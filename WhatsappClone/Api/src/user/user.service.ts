import { Injectable } from '@nestjs/common';
import { UserFactory } from './factories/user.factory';
import { ValidationTokenFactory } from './factories/validation-token.factory';
import { UserRegisterByPhoneInput } from './inputs/user-register.input';
import { UserSignUpByEmailInput } from './inputs/user-sign-up-email.input';
import { UserRepository } from './user.repository';
import { EmailAlreadyInUseError } from '@/@errors/email-already-in-use.error';

import { hash } from 'bcrypt';
import { NewContactInput } from './inputs/new-contact.input';
import { AuthService } from '@/auth/auth.service';
import { User } from './user.entity';
import { ContactWasNotFoundError } from '@/@errors/contact-was-not-found.error';
import { UserResponseModel } from './models/user-response.model';
import { AlreadyAContactError } from '@/@errors/already-a-contact.error';

const TOKEN_SIZE = 6;

@Injectable()
export class UserService {
  constructor(
    private readonly userRepository: UserRepository,
    private readonly userFactory: UserFactory,
    private readonly tokenFactory: ValidationTokenFactory,
    private readonly authService: AuthService
  ) {}

  public async registerByPhoneNumber(registerInput: UserRegisterByPhoneInput) {
    const user = await this.findUserByPhoneOrCreate(registerInput);
    const token = this.tokenFactory.createToken(TOKEN_SIZE);

    user.validationToken = token;
    user.valid = false;

    await this.userRepository.update(user.id, user);
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

  public async findOneUserById(id: string) {
    return await this.userRepository.findOneById(id);
  }

  public async findOneUserByEmail(email: string) {
    return await this.userRepository.findOneByEmail(email);
  }

  public async addToContact(newContactInput: NewContactInput, token: string) {
    const user = await this.authService.getUserByToken(token);
    let contact: User;

    console.log(newContactInput);

    if(newContactInput.email) {
      contact = await this.findOneUserByEmail(newContactInput.email);
    } else if(newContactInput.phoneNumber) {
      contact = await this.userRepository.findOneByEmail(newContactInput.phoneNumber);
    } else {
      throw new ContactWasNotFoundError();
    }

    if(!contact || !contact.valid) {
      throw new ContactWasNotFoundError();
    }

    if(
      user.contacts.find(c => contact.id === c.id) &&
      contact.contacts.find(u => user.id === u.id)
    ) {
      throw new AlreadyAContactError();
    }

    user.contacts.push(contact);
    contact.contacts.push(user);

    await this.userRepository.save(user);
    await this.userRepository.save(contact);

    return UserResponseModel.fromUser(user);
  }

  public async getUserContacts(token: string) {
    const user = await this.authService.getUserByToken(token);
    return user.contacts.map(contact => UserResponseModel.fromUser(contact));
  }

  private async findUserByPhoneOrCreate(
    registerInput: UserRegisterByPhoneInput,
  ) {
    const user = await this.userRepository.findOneByPhone(
      registerInput.phoneNumber,
    );

    if (!user) {
      const userToRegister = this.userFactory.createUserByPhone(registerInput);
      return this.userRepository.create(userToRegister);
    }

    return user;
  }

}
