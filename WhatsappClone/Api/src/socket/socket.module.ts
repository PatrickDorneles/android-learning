import { AuthModule } from '@/auth/auth.module';
import { ChatModule } from '@/chat/chat.module';
import { MessageModule } from '@/message/message.module';
import { UserModule } from '@/user/user.module';
import { Module } from '@nestjs/common';
import { SocketGateway } from './socket.gateway';

@Module({
  imports: [UserModule, ChatModule, MessageModule, AuthModule],
  providers: [SocketGateway],
})
export class SocketModule {}
