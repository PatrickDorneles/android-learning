import { Chat } from '@/chat/chat.entity';
import { Message } from '@/message/message.entity';
import {
  Entity,
  Column,
  PrimaryColumn,
  JoinTable,
  OneToMany,
  ManyToMany,
  ManyToOne,
} from 'typeorm';

@Entity()
export class User {
  @PrimaryColumn()
  id: string;

  @Column()
  name: string;

  @Column({ nullable: true })
  email: string;

  @Column({ nullable: true })
  password: string;

  @Column({ nullable: true })
  phoneNumber: string;

  @Column({ nullable: true })
  validationToken: string;

  @Column({ default: false })
  valid: boolean;

  @JoinTable()
  @ManyToMany(() => User)
  contacts: User[];

  @JoinTable()
  @OneToMany((type) => Message, (message) => message.user)
  messages: Message[];

  @ManyToMany((type) => Chat, (chat) => chat.users)
  chats: Chat[];
}
