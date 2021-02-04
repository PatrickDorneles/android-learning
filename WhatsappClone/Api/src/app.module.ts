import { Module } from '@nestjs/common';
import { UserModule } from './user/user.module';
import { ChatModule } from './chat/chat.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AuthModule } from './auth/auth.module';

@Module({
  imports: [TypeOrmModule.forRoot(), UserModule, ChatModule, AuthModule],
  controllers: [],
  providers: [],
})
export class AppModule {}
