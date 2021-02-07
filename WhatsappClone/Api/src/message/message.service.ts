import { ChatService } from '@/chat/chat.service';
import { UserService } from '@/user/user.service';
import { Injectable } from '@nestjs/common';
import { MessageFactory } from './factories/message.factory';
import { MessageRepository } from './message.repository';
import { MessageRequestModel } from './models/message-request.model';

@Injectable()
export class MessageService {
  constructor(
    private readonly userService: UserService,
    private readonly chatService: ChatService,
    private readonly messageFactory: MessageFactory,
    private readonly messageRepository: MessageRepository,
  ) {}

  public async sendMessage(messageRequest: MessageRequestModel) {
    const user = await this.userService.findUserById(messageRequest.userId);
    const chat = await this.chatService.findChatById(messageRequest.chatId);

    const newMessage = this.messageFactory.createMessage(
      user,
      chat,
      messageRequest.text,
    );

    return await this.messageRepository.save(newMessage);
  }
}
