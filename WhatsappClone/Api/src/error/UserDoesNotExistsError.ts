import { BadRequestError } from "routing-controllers";

export class UserDoesNotExistsError extends BadRequestError {
    constructor() {
        super("Usuario não existe.");
    }
}
