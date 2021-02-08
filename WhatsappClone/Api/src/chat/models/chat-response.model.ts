import { MessageResponseModel } from '@/message/models/message-response.model';
import { UserResponseModel } from '@/user/models/user-response.model';
import { Chat } from '../chat.entity';

export class ChatResponseModel {
  constructor(
    readonly id: string,
    readonly contact: UserResponseModel,
    readonly messages: MessageResponseModel[],
  ) {}

  public static fromChat(chat: Chat, contactId: string) {
    const messageResponses = chat.messages.map((m) =>
      MessageResponseModel.fromMessage(m),
    );
    const contact = chat.users.find((u) => {
      return u.id === contactId;
    });

    const contactResponse = UserResponseModel.fromUser(contact);

    return new ChatResponseModel(chat.id, contactResponse, messageResponses);
  }

  public static fromChatAuth(chat: Chat, userId: string) {
    const messageResponses = chat.messages.map((m) =>
      MessageResponseModel.fromMessage(m),
    );
    const contact = chat.users.find((u) => {
      return u.id !== userId;
    });

    const contactResponse = UserResponseModel.fromUser(contact);

    return new ChatResponseModel(chat.id, contactResponse, messageResponses);
  }
}
