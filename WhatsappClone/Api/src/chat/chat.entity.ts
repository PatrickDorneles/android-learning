import { Message } from '@/message/message.entity';
import { User } from '@/user/user.entity';
import {
  Entity,
  JoinTable,
  ManyToMany,
  OneToMany,
  PrimaryColumn,
} from 'typeorm';

@Entity()
export class Chat {
  @PrimaryColumn()
  id: string;

  @ManyToMany((type) => User, (user) => user.chats)
  @JoinTable()
  users: User[];

  @OneToMany((type) => Message, (message) => message.chat, {
    onDelete: 'CASCADE',
  })
  messages: Message[];

  setUsers(users: User[]) {
    this.users = users;
  }
}
