import { Module } from '@nestjs/common';
import { UserModule } from './user/user.module';
import { ChatModule } from './chat/chat.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AuthModule } from './auth/auth.module';
import { MessageModule } from './message/message.module';
import { SocketModule } from './socket/socket.module';

@Module({
  imports: [
    TypeOrmModule.forRoot(),
    UserModule,
    ChatModule,
    AuthModule,
    MessageModule,
    SocketModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
