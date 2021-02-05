import { AuthUserHeaderModel } from '@/auth/model/auth-user-header.model';
import { Body, Controller, Post, Headers, UseGuards, Get } from '@nestjs/common';
import { NewContactInput } from './inputs/new-contact.input';
import { UserRegisterByPhoneInput } from './inputs/user-register.input';
import { UserSignUpByEmailInput } from './inputs/user-sign-up-email.input';
import { UserService } from './user.service';
import { JwtAuthGuard } from '@/auth/jwt-auth.guard'

@Controller('user')
export class UserController {
  constructor(private userService: UserService) {}

  @Post('/sign-up/phone')
  protected async registerUserByPhone(
    @Body() registerInput: UserRegisterByPhoneInput,
  ) {
    return await this.userService.registerByPhoneNumber(registerInput);
  }

  @Post('/sign-up/email')
  protected async signUpUserByEmail(
    @Body() signUpInput: UserSignUpByEmailInput,
  ) {
    return await this.userService.signUpByEmail(signUpInput);
  }

  @UseGuards(JwtAuthGuard)
  @Post('add/contact')
  protected async addContact(
    @Body() newContactInput: NewContactInput,
    @Headers() headers: AuthUserHeaderModel,
  ) {
    return await this.userService.addToContact(newContactInput, headers.authorization);
  }

  @UseGuards(JwtAuthGuard)
  @Get('contacts')
  protected async getContacts(@Headers() headers: AuthUserHeaderModel) {
    return await this.userService.getUserContacts(headers.authorization);
  }
}
