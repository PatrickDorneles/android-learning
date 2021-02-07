import { Module } from '@nestjs/common';
import { ChatService } from './chat.service';
import { ChatController } from './chat.controller';
import { PassportModule } from '@nestjs/passport';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ChatRepository } from './chat.repository';
import { AuthModule } from '@/auth/auth.module';
import { UserModule } from '@/user/user.module';
import { ChatFactory } from './factories/chat.factory';
import { JwtModule } from '@nestjs/jwt';

@Module({
  imports: [
    PassportModule.register({ defaultStrategy: 'jwt' }),
    TypeOrmModule.forFeature([ChatRepository]),
    AuthModule,
    UserModule,
  ],
  controllers: [ChatController],
  providers: [ChatService, ChatFactory],
  exports: [ChatService],
})
export class ChatModule {}
