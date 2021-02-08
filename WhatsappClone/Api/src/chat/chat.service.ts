import { AuthService } from '@/auth/auth.service';
import { User } from '@/user/user.entity';
import { UserService } from '@/user/user.service';
import {
  BadRequestException,
  Injectable,
  NotFoundException,
} from '@nestjs/common';
import { ChatRepository } from './chat.repository';
import { ChatFactory } from './factories/chat.factory';
import { ChatResponseModel } from './models/chat-response.model';

@Injectable()
export class ChatService {
  constructor(
    private readonly authService: AuthService,
    private readonly userService: UserService,
    private readonly chatRepository: ChatRepository,
    private readonly chatFactory: ChatFactory,
  ) {}

  public async findChatById(id: string) {
    return await this.chatRepository.findOne(id, {
      relations: ['messages', 'messages.chat', 'messages.user', 'users'],
    });
  }

  public async findResponseChatsByAuth(token: string) {
    const user = await this.authService.findUserByToken(token);
    const chats = user.chats;
    return chats.map((c) => ChatResponseModel.fromChatAuth(c, user.id));
  }

  public async findChatsIdsByUserToken(token: string) {
    const user = await this.authService.findUserByToken(token);
    return user.chats.map((c) => c.id);
  }

  public async findChatByUsers([user, contact]: [User, User]) {
    const findedUser = await this.userService.findUserById(user.id);
    const chat = findedUser.chats.find(
      (c) =>
        c.users.find((u) => {
          return u.id === contact.id;
        }) != undefined,
    );

    if (!chat) {
      return undefined;
    }

    return this.findChatById(chat.id);
  }

  public async createChat(users: User[]) {
    const chat = this.chatFactory.createChat(users);
    return await this.chatRepository.save(chat);
  }

  public async findChatByContact(contactId: string, token: string) {
    const user = await this.authService.findUserByToken(token);
    const contact = await this.userService.findUserById(contactId);

    if (!contact) {
      throw new BadRequestException();
    }

    const chat = await this.findChatByUsers([user, contact]);

    if (!chat) {
      throw new NotFoundException();
    }

    return chat;
  }
}
