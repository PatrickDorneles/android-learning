import { BadRequestError, UnauthorizedError } from "routing-controllers";
import { Inject, Service } from "typedi";
import { InjectRepository } from "typeorm-typedi-extensions";
import { UserRegisterInput } from "../../input/UserRegisterInput";
import { UserFactory } from "../../factory/UserFactory";
import { User } from "../../entity/User";
import { UserRepository } from "../../repository/UserRepository";
import { ValidationTokenFactory } from "../../factory/ValidationTokenFactory";

const TOKEN_SIZE = 6;

@Service()
export class RegisterUserService {

    @InjectRepository()
    private readonly userRepository: UserRepository;

    @Inject()
    private readonly userFactory: UserFactory;

    @Inject()
    private readonly tokenFactory: ValidationTokenFactory;

    public async register(registerInput: UserRegisterInput)  {
        const user = await this.findUserByPhoneOrCreate(registerInput)
        const token = this.tokenFactory.createToken(TOKEN_SIZE);

        user.validationToken = token;
        user.valid = false;

        await this.userRepository.update(user.id, user);
    }


    private async findUserByPhoneOrCreate(registerInput: UserRegisterInput) {
        const user = await this.userRepository.findOneByPhone(registerInput.phoneNumber);

        if(!user) {
            const userToRegister = this.userFactory.createUser(registerInput);
            return this.userRepository.create(userToRegister);
        }

        return user;
    }

}
