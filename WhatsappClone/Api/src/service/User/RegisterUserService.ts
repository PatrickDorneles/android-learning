import { BadRequestError, UnauthorizedError } from "routing-controllers";
import { Inject, Service } from "typedi";
import { UserRegisterRequestDTO } from "../../dto/UserRegisterRequestDTO";
import { User } from "../../entity/User";
import { FindUserByPhoneNumberService } from "./FindUserByPhoneNumberService";

@Service()
export class RegisterUserService {

    @Inject()
    

    @Inject()
    private readonly findUserByPhoneNumberService: FindUserByPhoneNumberService;

    public async registerUser(registerDTO: UserRegisterRequestDTO)  {
        let user = await this.findUserByPhoneNumberService.findUserByPhone(registerDTO.phoneNumber);

        if(!user) {
            const userToRegister = new User({
                name: registerDTO.name,
                phoneNumber: registerDTO.phoneNumber,
            });


        }

    }

}
