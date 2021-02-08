import { EntityRepository, Repository } from 'typeorm';
import { User } from './user.entity';

@EntityRepository(User)
export class UserRepository extends Repository<User> {
  public async findOneByPhone(phoneNumber: string) {
    return await this.findOne(undefined, {
      where: { phoneNumber },
      relations: [
        'contacts',
        'chats',
        'chats.messages',
        'chats.messages.user',
        'chats.messages.chat',
        'chats.users',
        'messages',
      ],
    });
  }

  public async findOneById(id: string) {
    return await this.findOne(id, {
      relations: [
        'contacts',
        'chats',
        'chats.messages',
        'chats.messages.user',
        'chats.messages.chat',
        'chats.users',
        'messages',
      ],
    });
  }

  public async findOneByEmail(email: string) {
    return await this.findOne(undefined, {
      where: { email },
      relations: [
        'contacts',
        'chats',
        'chats.messages',
        'chats.messages.user',
        'chats.messages.chat',
        'chats.users',
        'messages',
      ],
    });
  }
}
