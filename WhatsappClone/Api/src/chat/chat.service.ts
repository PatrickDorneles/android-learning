import { AuthService } from '@/auth/auth.service';
import { User } from '@/user/user.entity';
import { Injectable } from '@nestjs/common';
import { ChatRepository } from './chat.repository';
import { ChatFactory } from './factories/chat.factory';

@Injectable()
export class ChatService {
  constructor(
    private readonly authService: AuthService,
    private readonly chatRepository: ChatRepository,
    private readonly chatFactory: ChatFactory,
  ) {}

  public async findChatById(id: string) {
    return await this.chatRepository.findOne(id, {
      relations: ['messages', 'messages.user', 'users'],
    });
  }

  public async findChatsIdsByUserToken(token: string) {
    const user = await this.authService.getUserByToken(token);
    return user.chats.map((c) => c.id);
  }

  public async findChatByUsers(users: [User, User]) {
    return await this.chatRepository.findByUsers(users);
  }

  public async createChat(users: User[]) {
    const chat = this.chatFactory.createChat(users);
    return await this.chatRepository.save(chat);
  }
}
