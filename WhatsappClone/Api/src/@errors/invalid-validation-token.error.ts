import { BadRequestException } from "@nestjs/common";

export class InvalidValidationTokenError extends BadRequestException {

    constructor() {
        super("Your token is invalid.");
    }

}
