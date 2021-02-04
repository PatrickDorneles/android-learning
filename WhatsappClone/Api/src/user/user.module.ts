import { forwardRef, Module } from '@nestjs/common';
import { PassportModule } from '@nestjs/passport';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UserFactory } from './factories/user.factory';
import { ValidationTokenFactory } from './factories/validation-token.factory';
import { UserController } from './user.controller';
import { User } from './user.entity';
import { UserRepository } from './user.repository';
import { UserService } from './user.service';
import { AuthModule } from '@/auth/auth.module'

@Module({
  imports: [
    PassportModule.register({ defaultStrategy: 'jwt' }),
    TypeOrmModule.forFeature([UserRepository]),
    AuthModule
  ],
  controllers: [UserController],
  providers: [
    UserService, 
    UserFactory,
    ValidationTokenFactory
  ],
  exports: [
    UserService
  ]
})
export class UserModule {}
