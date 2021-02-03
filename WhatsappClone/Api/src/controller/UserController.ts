import { Body, Controller, JsonController, Param, Post } from "routing-controllers";
import { Inject } from "typedi";
import { UserRegisterInput } from "../input/UserRegisterInput";
import { ValidateTokenInput } from "../input/ValidateTokenInput";
import { UserRepository } from "../repository/UserRepository";
import { RegisterUserService } from "../service/User/RegisterUserService";

@JsonController("/user")
export class UserController {

    @Inject()
    private registerUserService: RegisterUserService;

    @Post()
    protected registerUser(@Body({ validate: true }) registerInput: UserRegisterInput) {
        return this.registerUserService.register(registerInput);
    }

    @Post('/validate/:id')
    private validateUser(
        @Body({ validate: true }) validateInput: ValidateTokenInput, 
        @Param('id') id: string) {
        return 
    }


}