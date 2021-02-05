import { BadRequestException } from "@nestjs/common";

export class AlreadyAContactError extends BadRequestException {
    constructor() {
        super("This user is already one of your contacts")
    }
}