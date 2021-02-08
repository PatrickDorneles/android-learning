import { HttpService, Injectable, UnauthorizedException } from '@nestjs/common';
import { UserFactory } from './factories/user.factory';
import { ValidationTokenFactory } from './factories/validation-token.factory';
import { UserRegisterByPhoneInput } from './inputs/user-register.input';
import { UserSignUpByEmailInput } from './inputs/user-sign-up-email.input';
import { UserRepository } from './user.repository';
import { EmailAlreadyInUseError } from '@/@errors/email-already-in-use.error';

import { hash } from 'bcrypt';
import { AuthService } from '@/auth/auth.service';
import { User } from './user.entity';
import { ContactWasNotFoundError } from '@/@errors/contact-was-not-found.error';
import { UserResponseModel } from './models/user-response.model';
import { AlreadyAContactError } from '@/@errors/already-a-contact.error';
import { ValidatorInput } from './inputs/validator.input';
import { isEmail } from 'class-validator';
import { AppConstants } from '@/@config/constants';

const TOKEN_SIZE = 6;

@Injectable()
export class UserService {
  constructor(
    private readonly userRepository: UserRepository,
    private readonly userFactory: UserFactory,
    private readonly tokenFactory: ValidationTokenFactory,
    private readonly authService: AuthService,
    private readonly httpService: HttpService,
  ) {}

  public async registerByPhoneNumber(registerInput: UserRegisterByPhoneInput) {
    const user = await this.findUserByPhoneOrCreate(registerInput);
    const token = this.tokenFactory.createToken(TOKEN_SIZE);

    user.name = registerInput.name;
    user.validationToken = token;
    user.valid = false;

    await this.userRepository.save(user);

    const message = `Whatsapp Clone: seu codigo de verificação é ${token}`;

    console.log(message);

    // this.httpService.post(
    //   `${AppConstants.SMS_API}/send?key=${
    //     AppConstants.SMS_API_KEY
    //   }&type=9&number=${user.phoneNumber.replace(
    //     '55',
    //     '',
    //   )}&msg=${encodeURIComponent(message)}`,
    // );

    return user;
  }

  public async validateUserPhoneNumber(
    userId: string,
    validator: ValidatorInput,
  ) {
    const user = await this.findUserById(userId);

    if (validator.validationToken !== user.validationToken) {
      throw new UnauthorizedException('Invalid validation token');
    }

    user.valid = true;
    user.validationToken = '';

    await this.userRepository.save(user);

    return await this.authService.authUserWithPhoneNumber(userId);
  }

  public async signUpByEmail(signUpInput: UserSignUpByEmailInput) {
    const existentUser = await this.userRepository.findOneByEmail(
      signUpInput.email,
    );
    if (existentUser) {
      throw new EmailAlreadyInUseError();
    }

    const user = this.userFactory.createUserByEmail(signUpInput);

    user.password = await hash(user.password, 10);

    const savedUser = await this.userRepository.save(user);

    return savedUser;
  }

  public async findUserById(id: string) {
    return await this.userRepository.findOneById(id);
  }

  public async findUserByEmail(email: string) {
    return await this.userRepository.findOneByEmail(email);
  }

  public async addToContact(emailOrPhoneNumber: string, token: string) {
    const user = await this.authService.findUserByToken(token);
    let contact: User;

    if (isEmail(emailOrPhoneNumber)) {
      contact = await this.findUserByEmail(emailOrPhoneNumber);
    } else {
      contact = await this.userRepository.findOneByPhone(emailOrPhoneNumber);
    }

    if (!contact) {
      throw new ContactWasNotFoundError();
    }

    if (user.contacts.find((c) => contact.id === c.id)) {
      throw new AlreadyAContactError();
    }

    user.contacts.push(contact);

    await this.userRepository.save(user);

    return UserResponseModel.fromUser(contact);
  }

  public async getUserContacts(token: string) {
    const user = await this.authService.findUserByToken(token);
    return user.contacts.map((contact) => UserResponseModel.fromUser(contact));
  }

  private async findUserByPhoneOrCreate(
    registerInput: UserRegisterByPhoneInput,
  ) {
    const user = await this.userRepository.findOneByPhone(
      registerInput.phoneNumber,
    );

    if (!user) {
      const userToRegister = this.userFactory.createUserByPhone(registerInput);
      return await this.userRepository.save(userToRegister);
    }

    return user;
  }
}
