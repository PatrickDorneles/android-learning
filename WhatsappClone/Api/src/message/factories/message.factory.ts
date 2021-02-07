import { Chat } from '@/chat/chat.entity';
import { User } from '@/user/user.entity';
import { Injectable } from '@nestjs/common';
import { Message } from '../message.entity';

@Injectable()
export class MessageFactory {
  public createMessage(user: User, chat: Chat, text: string) {
    const message = new Message();

    message.user = user;
    message.chat = chat;
    message.text = text;

    return message;
  }
}
