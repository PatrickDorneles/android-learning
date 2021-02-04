import { User } from '@/user/user.entity';
import { Injectable } from '@nestjs/common';
import { JwtService, JwtSignOptions } from '@nestjs/jwt';
import { AuthTokenPayload } from '../inputs/token-payload.input';

@Injectable()
export class JwtFactory {
  constructor(private readonly jwtService: JwtService) {}

  public async generateJwt(user: User) {
    const tokenPayload: AuthTokenPayload = {
      id: user.id,
      phoneNumber: user.phoneNumber,
      email: user.email,
    };

    const tokenOptions: JwtSignOptions = {
      expiresIn: '3d',
    };

    return this.jwtService.sign(tokenPayload, tokenOptions);
  }
}
