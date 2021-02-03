import { BadRequestError } from "routing-controllers";


export class InvalidValidationTokenError extends BadRequestError {

    constructor() {
        super("Seu token de validação é invalido.");
    }

}
