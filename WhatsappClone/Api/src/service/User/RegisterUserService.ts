import { BadRequestError, UnauthorizedError } from "routing-controllers";
import { Inject, Service } from "typedi";
import { InjectRepository } from "typeorm-typedi-extensions";
import { UserRegisterRequestDTO } from "../../dto/UserRegisterRequestDTO";
import { User } from "../../entity/User";
import { UserRepository } from "../../repository/UserRepository";

@Service()
export class RegisterUserService {

    @InjectRepository()
    private readonly userRepository: UserRepository;

    public async registerUser(registerDTO: UserRegisterRequestDTO)  {
        let user = await this.userRepository.findOneByPhone(registerDTO.phoneNumber);

        if(!user) {
            const userToRegister = new User({
                name: registerDTO.name,
                phoneNumber: registerDTO.phoneNumber,
            });

            user = this.userRepository.create(userToRegister);   
        }

        

    }

}
