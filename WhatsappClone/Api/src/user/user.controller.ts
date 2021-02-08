import { AuthUserHeaderModel } from '@/auth/model/auth-user-header.model';
import {
  Body,
  Controller,
  Post,
  Headers,
  UseGuards,
  Get,
  Param,
} from '@nestjs/common';
import { UserRegisterByPhoneInput } from './inputs/user-register.input';
import { UserSignUpByEmailInput } from './inputs/user-sign-up-email.input';
import { UserService } from './user.service';
import { JwtAuthGuard } from '@/auth/jwt-auth.guard';
import { UserResponseModel } from './models/user-response.model';
import { ValidatorInput } from './inputs/validator.input';

@Controller('user')
export class UserController {
  constructor(private userService: UserService) {}

  @Post('/sign-up/phone')
  protected async registerUserByPhone(
    @Body() registerInput: UserRegisterByPhoneInput,
  ) {
    const user = await this.userService.registerByPhoneNumber(registerInput);
    return UserResponseModel.fromUser(user);
  }

  @Post('/sign-up/validate/:id')
  protected async validateUser(
    @Param('id') id: string,
    @Body() input: ValidatorInput,
  ) {
    return await this.userService.validateUserPhoneNumber(id, input);
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
    @Body() emailOrPhoneNumber: string,
    @Headers() headers: AuthUserHeaderModel,
  ) {
    return await this.userService.addToContact(
      emailOrPhoneNumber,
      headers.authorization,
    );
  }

  @UseGuards(JwtAuthGuard)
  @Get('contacts')
  protected async getContacts(@Headers() headers: AuthUserHeaderModel) {
    return await this.userService.getUserContacts(headers.authorization);
  }
}
