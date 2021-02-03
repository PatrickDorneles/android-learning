import { BadRequestException } from "@nestjs/common";

export class InvalidValidationTokenError extends BadRequestException {

    constructor() {
        super("Seu token de validação é invalido.");
    }

}
