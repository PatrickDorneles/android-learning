import { BadRequestException } from "@nestjs/common";

export class EmailAlreadyInUseError extends BadRequestException {

    constructor() {
        super("Your email is already in use.");
    }

}