import { IsEmail, IsNotEmpty, IsPhoneNumber, IsString } from 'class-validator';

export class UserRegisterByPhoneInput {
  @IsNotEmpty()
  @IsString()
  name: string;

  @IsNotEmpty()
  @IsPhoneNumber()
  phoneNumber: string;
}
