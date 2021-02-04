import { UserModule } from '@/user/user.module';
import { Module } from '@nestjs/common';
import { AuthService } from './auth.service';
import { AuthController } from './auth.controller';
import { PassportModule } from '@nestjs/passport';
import { JwtModule } from '@nestjs/jwt';
import { JwtStrategy } from './jwt.strategy';
import { JwtFactory } from './factories/jwt.factory';
import { jwtConstants } from './constants';

@Module({
  imports: [
    PassportModule,
    JwtModule.register({
      secret: jwtConstants.secret,
      signOptions: { expiresIn: '6d' },
    }),
  ],
  providers: [AuthService, JwtStrategy, JwtFactory],
  controllers: [AuthController],
  exports: [AuthService],
})
export class AuthModule {}
