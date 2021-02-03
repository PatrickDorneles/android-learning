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
    protected registerUserByPhone(@Body() registerInput: UserRegisterByPhoneInput) {
        return this.userService.registerByPhoneNumber(registerInput);
    }

    @Post("/sign-up/email")
    protected signUpUserByEmail(@Body() signUpInput: UserSignUpByEmailInput) {
        
    }


}
