import { InvalidEmailOrPasswordError } from '@/@errors/invalid-email-or-password.error';
import { UserResponseModel } from '@/user/models/user-response.model';
import { User } from '@/user/user.entity';
import { UserService } from '@/user/user.service';
import {
  Injectable,
  OnModuleInit,
  UnauthorizedException,
} from '@nestjs/common';
import { ModuleRef } from '@nestjs/core';
import { JwtService } from '@nestjs/jwt';
import { compare } from 'bcrypt';
import { JwtFactory } from './factories/jwt.factory';
import { AuthWithEmailInput } from './inputs/auth-with-email.input';
import { AuthTokenPayload } from './inputs/token-payload.input';
import { AuthResponseModel } from './model/auth-response.model';

@Injectable()
export class AuthService implements OnModuleInit {
  private userService: UserService;

  constructor(
    private readonly jwtFactory: JwtFactory,
    private readonly jwtService: JwtService,
    private readonly userModuleRef: ModuleRef,
  ) {}

  onModuleInit() {
    this.userService = this.userModuleRef.get(UserService, { strict: false });
  }

  async authUserWithEmail(authReq: AuthWithEmailInput) {
    const user: User | undefined = await this.userService.findUserByEmail(
      authReq.email,
    );

    if (!user) {
      throw new InvalidEmailOrPasswordError();
    }

    const passwordDoMatch: boolean = await compare(
      authReq.password,
      user.password,
    );

    if (!passwordDoMatch) {
      throw new InvalidEmailOrPasswordError();
    }

    const authToken = await this.jwtFactory.generateJwt(user);

    return new AuthResponseModel(authToken, UserResponseModel.fromUser(user));
  }

  public async authUserWithPhoneNumber(userId: string) {
    const user: User = await this.userService.findUserById(userId);

    const authToken = await this.jwtFactory.generateJwt(user);

    return new AuthResponseModel(authToken, UserResponseModel.fromUser(user));
  }

  public async findUserByToken(token: string): Promise<User | undefined> {
    const jwtToken: string = token.replace('Bearer ', '');
    try {
      const userTokenPayload: AuthTokenPayload = this.jwtService.verify<AuthTokenPayload>(
        jwtToken,
      );
      return await this.findUserByPayload(userTokenPayload);
    } catch (error) {
      throw new UnauthorizedException('Token Invalido');
    }
  }

  public async findUserByPayload(
    tokenPayload: AuthTokenPayload,
  ): Promise<User | undefined> {
    const user = await this.userService.findUserById(tokenPayload.id);

    return user;
  }
}
