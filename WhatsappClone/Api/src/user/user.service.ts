import { Inject, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { UserFactory } from './factories/user.factory';
import { ValidationTokenFactory } from './factories/validation-token.factory';
import { UserRegisterByPhoneInput } from './inputs/user-register.input';
import { UserSignUpByEmailInput } from './inputs/user-sign-up-email.input';
import { UserRepository } from './user.repository';

const TOKEN_SIZE = 6;

@Injectable()
export class UserService {

    constructor(
        private readonly userRepository: UserRepository,
        private readonly userFactory: UserFactory,
        private readonly tokenFactory: ValidationTokenFactory,
    ) { }

    public async registerByPhoneNumber(registerInput: UserRegisterByPhoneInput)  {
        const user = await this.findUserByPhoneOrCreate(registerInput)
        const token = this.tokenFactory.createToken(TOKEN_SIZE);

        user.validationToken = token;
        user.valid = false;
        
        await this.userRepository.update(user.id, user);
    }

    public async signUpByEmail(signUpInput: UserSignUpByEmailInput) {
        const user = await this.userRepository.findOneByEmail(signUpInput.email);



    }

    private async findUserByPhoneOrCreate(registerInput: UserRegisterByPhoneInput) {
        const user = await this.userRepository.findOneByPhone(registerInput.phoneNumber);

        if(!user) {
            const userToRegister = this.userFactory.createUser(registerInput);
            return this.userRepository.create(userToRegister);
        }

        return user;
    }


}
