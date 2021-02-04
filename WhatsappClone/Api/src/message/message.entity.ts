import { Chat } from '@/chat/chat.entity';
import { User } from '@/user/user.entity';
import {
  Column,
  CreateDateColumn,
  Entity,
  ManyToOne,
  PrimaryColumn,
} from 'typeorm';

@Entity()
export class Message {
  @PrimaryColumn()
  id: string;

  @CreateDateColumn()
  dateTime: Date;

  @ManyToOne((type) => User)
  user: User;

  @ManyToOne((type) => Chat, { onDelete: 'CASCADE' })
  chat: Chat;

  @Column('text')
  text: string;
}
