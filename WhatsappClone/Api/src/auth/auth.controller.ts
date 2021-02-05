import { UserResponseModel } from '@/user/models/user-response.model';
import {
  Body,
  Controller,
  Get,
  Post,
  UseGuards,
  Headers,
} from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { AuthService } from './auth.service';
import { AuthWithEmailInput } from './inputs/auth-with-email.input';
import { JwtAuthGuard } from './jwt-auth.guard';
import { AuthUserHeaderModel } from './model/auth-user-header.model';

@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}

  @Post('/email')
  protected async authWithEmail(@Body() auth: AuthWithEmailInput) {
    return await this.authService.authUserWithEmail(auth);
  }

  @Get()
  @UseGuards(JwtAuthGuard)
  async getAthenticatedUser(
    @Headers() headers: AuthUserHeaderModel,
  ): Promise<UserResponseModel> {
    const userFound = await this.authService.getUserByToken(
      headers.authorization,
    );
    return UserResponseModel.fromUser(userFound);
  }
}
