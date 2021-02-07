import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { MessageService } from './message.service';
import { MessageRepository } from './message.repository';
import { AuthModule } from '@/auth/auth.module';
import { ChatModule } from '@/chat/chat.module';
import { UserModule } from '@/user/user.module';
import { MessageFactory } from './factories/message.factory';

@Module({
  imports: [
    TypeOrmModule.forFeature([MessageRepository]),
    AuthModule,
    ChatModule,
    UserModule,
  ],
  providers: [MessageService, MessageFactory],
  exports: [MessageService],
})
export class MessageModule {}
