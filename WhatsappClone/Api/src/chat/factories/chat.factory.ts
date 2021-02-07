import { User } from '@/user/user.entity';
import { Inject, Injectable } from '@nestjs/common';
import { v4 as uuidv4 } from 'uuid';
import { Chat } from '../chat.entity';

@Injectable()
export class ChatFactory {
  public createChat(users: User[]) {
    const id = uuidv4();
    const chat = new Chat();

    chat.id = id;
    chat.setUsers(users);

    return chat;
  }
}
