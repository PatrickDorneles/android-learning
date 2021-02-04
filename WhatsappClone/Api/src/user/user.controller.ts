import { Body, Controller, Post } from '@nestjs/common';
import { UserRegisterByPhoneInput } from './inputs/user-register.input';
import { UserSignUpByEmailInput } from './inputs/user-sign-up-email.input';
import { UserService } from './user.service';

@Controller('user')
export class UserController {

    
    constructor(
        private userService: UserService
    ) { }

    @Post("/sign-up/phone")
    protected async registerUserByPhone(@Body() registerInput: UserRegisterByPhoneInput) {
        return await this.userService.registerByPhoneNumber(registerInput);
    }

    @Post("/sign-up/email")
    protected async signUpUserByEmail(@Body() signUpInput: UserSignUpByEmailInput) {
        return await this.userService.signUpByEmail(signUpInput);
    }


}
