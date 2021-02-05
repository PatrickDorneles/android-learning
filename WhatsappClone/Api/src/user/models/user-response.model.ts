import { User } from "../user.entity";

export class UserResponseModel {
  id: string;
  name: string;
  email: string | undefined;
  phoneNumber: string | undefined;
  valid: boolean;

  static fromUser(user: User) {
    const model = new UserResponseModel();
    model.id = user.id;
    model.name = user.name;
    model.email = user.email;
    model.phoneNumber = user.phoneNumber;
    model.valid = user.valid;
    return model;
  }
}
