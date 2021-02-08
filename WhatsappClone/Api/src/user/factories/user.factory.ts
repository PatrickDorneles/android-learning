import { Inject, Injectable } from '@nestjs/common';
import { v4 as uuidv4 } from 'uuid';
import {
  CreateUserModelByEmail,
  CreateUserModelByPhone,
} from '../models/create-user.model';
import { User } from '../user.entity';

@Injectable()
export class UserFactory {
  public createUserByPhone(value: CreateUserModelByPhone): User {
    const id = uuidv4();
    const user = new User();

    user.id = id;
    user.name = value.name;
    user.phoneNumber = value.phoneNumber;
    user.valid = false;

    return user;
  }

  public createUserByEmail(value: CreateUserModelByEmail): User {
    const id = uuidv4();
    const user = new User();

    user.id = id;
    user.name = value.name;
    user.email = value.email;
    user.password = value.password;
    user.valid = true;

    return user;
  }
}
