import { Body, Controller, JsonController, Post } from "routing-controllers";
import { Inject } from "typedi";
import { UserRegisterRequestDTO } from "../dto/UserRegisterRequestDTO";
import { UserRepository } from "../repository/UserRepository";
import { RegisterUserService } from "../service/User/RegisterUserService";

@JsonController("/user")
export class UserController {

    @Inject()
    private registerUserService: RegisterUserService;

    @Post()
    protected registerUser(@Body({ validate: true }) registerDTO: UserRegisterRequestDTO) {
        return this.registerUserService.registerUser(registerDTO);
    }


}