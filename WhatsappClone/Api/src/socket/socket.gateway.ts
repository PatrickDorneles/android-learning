import { AuthService } from '@/auth/auth.service';
import { Chat } from '@/chat/chat.entity';
import { ChatService } from '@/chat/chat.service';
import { Message } from '@/message/message.entity';
import { MessageService } from '@/message/message.service';
import { MessageRequestModel } from '@/message/models/message-request.model';
import { MessageResponseModel } from '@/message/models/message-response.model';
import { UserResponseModel } from '@/user/models/user-response.model';
import { User } from '@/user/user.entity';
import { UserService } from '@/user/user.service';
import {
  OnGatewayConnection,
  OnGatewayDisconnect,
  SubscribeMessage,
  WebSocketGateway,
  WebSocketServer,
} from '@nestjs/websockets';
import { Socket } from 'socket.io';
import { MessageRequestInput } from './inputs/message-request.input';
import { ChatResponseModel } from './models/chat-response.model';
import { ConnectedUserModel } from './models/connected-user.model';

export enum Events {
  CONNECT_USER = 'CONNECT_USER',
  MESSAGE = 'MESSAGE',
}

export enum Emits {
  CONNECTION_ERROR = 'CONNECTION_ERROR',
  NEW_CHAT = 'NEW_CHAT',
  NEW_MESSAGE = 'NEW_MESSAGE',
}

function getRoomName(id: string) {
  const CHAT_ROOM_PREFIX = 'chat';
  return `${CHAT_ROOM_PREFIX}: ${id}`;
}

@WebSocketGateway(80, {
  transports: ['websocket', 'polling'],
  allowUpgrades: false,
})
export class SocketGateway
  implements OnGatewayDisconnect<Socket>, OnGatewayConnection<Socket> {
  private connectedUsers: ConnectedUserModel[] = [];

  constructor(
    private readonly userService: UserService,
    private readonly authService: AuthService,
    private readonly messageService: MessageService,
    private readonly chatService: ChatService,
  ) {}

  @WebSocketServer() server: SocketIO.Server;

  async handleConnection(socket: Socket) {
    const token = socket.request.headers.authorization;

    try {
      const user: User = await this.authService.getUserByToken(token);
      const connectedUser = new ConnectedUserModel(user.id, socket);
      this.connectedUsers.push(connectedUser);

      const chatsIds = await this.chatService.findChatsIdsByUserToken(token);

      chatsIds.forEach((pcid) => {
        socket.join(getRoomName(pcid));
      });
    } catch (error) {
      socket.disconnect();
      socket.emit(Emits.CONNECTION_ERROR, 'Something went wrong');
    }
  }

  handleDisconnect(client: Socket) {
    const userToDisconnet = this.connectedUsers.find(
      (c) => c.socket.id === client.id,
    );
    this.connectedUsers = this.connectedUsers.filter(
      (c) => c !== userToDisconnet,
    );
  }

  @SubscribeMessage(Events.MESSAGE)
  public async privateMessage(
    client: Socket,
    messageRequest: MessageRequestInput,
  ) {
    const user: User = await this.authService.getUserByToken(
      messageRequest.token,
    );

    const contactUser: User = await this.userService.findUserById(
      messageRequest.receiverId,
    );

    const chatWithContact:
      | Chat
      | undefined = await this.chatService.findChatByUsers([user, contactUser]);

    let chatId: string;

    if (!chatWithContact) {
      const newChat = await this.chatService.createChat([user, contactUser]);
      chatId = newChat.id;
    } else {
      chatId = chatWithContact.id;
    }

    const messageReq = new MessageRequestModel(
      messageRequest.text,
      chatId,
      user.id,
    );

    const message: Message = await this.messageService.sendMessage(messageReq);

    const messageResponse = new MessageResponseModel(
      message.text,
      UserResponseModel.fromUser(user),
      chatId,
      message.dateTime,
    );

    const contactUserConnected:
      | ConnectedUserModel
      | undefined = this.connectedUsers.find((u) => u.id === contactUser.id);

    if (!chatWithContact) {
      const chatResponseToUser = new ChatResponseModel(
        chatId,
        UserResponseModel.fromUser(contactUser),
        [messageResponse],
      );

      client.join(getRoomName(chatId));

      client.emit(Emits.NEW_CHAT, chatResponseToUser);

      if (contactUserConnected) {
        const chatResponseToContact = new ChatResponseModel(
          chatId,
          UserResponseModel.fromUser(user),
          [messageResponse],
        );

        contactUserConnected.socket.emit(Emits.NEW_CHAT, chatResponseToContact);
        contactUserConnected.socket.join(getRoomName(chatId));
      }
    } else {
      this.server
        .to(getRoomName(chatId))
        .emit(Emits.NEW_MESSAGE, messageResponse);
    }
  }
}
