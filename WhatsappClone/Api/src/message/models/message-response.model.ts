import { UserResponseModel } from '@/user/models/user-response.model';
import { Message } from '../message.entity';

export class MessageResponseModel {
  constructor(
    readonly text: string,
    readonly user: UserResponseModel,
    readonly chatId: string,
    readonly dateTime: Date,
  ) {}

  public static fromMessage(message: Message) {
    const user = UserResponseModel.fromUser(message.user);
    const messageResponse = new MessageResponseModel(
      message.text,
      user,
      message.chat.id,
      message.dateTime,
    );

    return messageResponse;
  }
}
