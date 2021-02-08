import { User } from '@/user/user.entity';
import { EntityRepository, Repository } from 'typeorm';
import { Chat } from './chat.entity';

@EntityRepository(Chat)
export class ChatRepository extends Repository<Chat> {
  public async findAll() {
    return await this.find({
      relations: ['messages', 'messages.user', 'messages.chat', 'users'],
    });
  }
}
