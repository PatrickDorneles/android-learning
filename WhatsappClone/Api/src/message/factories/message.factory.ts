import { Chat } from '@/chat/chat.entity';
import { User } from '@/user/user.entity';
import { Injectable } from '@nestjs/common';
import { Message } from '../message.entity';
import { v4 as uuidv4 } from 'uuid';
import { MessageResponseModel } from '../models/message-response.model';
import { UserResponseModel } from '@/user/models/user-response.model';

@Injectable()
export class MessageFactory {
  public createMessage(user: User, chat: Chat, text: string) {
    const id = uuidv4();
    const message = new Message();

    message.id = id;
    message.user = user;
    message.chat = chat;
    message.text = text;

    return message;
  }
}
