import { Inject, Injectable } from "@nestjs/common";
import { v4 as uuidv4 } from 'uuid';
import { CreateUserModel } from "../models/create-user.model";
import { User } from "../user.entity";

@Injectable()
export class UserFactory {

    public createUser(value: CreateUserModel): User {
        const id = uuidv4();
        const user = new User();

        user.id = id;
        user.name = value.name;
        user.phoneNumber = value.phoneNumber;
        
        return user;
    }

}