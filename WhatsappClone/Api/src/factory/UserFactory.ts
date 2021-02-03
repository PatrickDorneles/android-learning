import { Service } from "typedi";
import { uuid } from "uuidv4"
import { User } from "../entity/User"
import { CreateUserModel } from "../model/CreateUserModel";

@Service()
export class UserFactory {

    public createUser(value: CreateUserModel): User {
        const id = uuid();
        const user = new User();

        user.id = id;
        user.name = value.name;
        user.phoneNumber = value.phoneNumber;
        
        return user;
    }

}