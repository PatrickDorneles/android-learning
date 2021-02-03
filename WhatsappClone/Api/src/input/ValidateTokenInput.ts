import { IsNotEmpty, IsNumberString, IsString, Length } from "class-validator";

export class ValidateTokenInput {

    @IsNotEmpty()
    @IsString()
    @IsNumberString()
    token: string;

}
