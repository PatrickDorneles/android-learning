import { IsEmail, IsNotEmpty, IsPhoneNumber, IsString } from 'class-validator';

export class UserRegisterInput {
    
    @IsNotEmpty()
    @IsString()
    name: string;

    @IsNotEmpty()
    @IsPhoneNumber()
    phoneNumber: string;

}
