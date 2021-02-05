import { BadRequestException, NotFoundException } from "@nestjs/common";

export class ContactWasNotFoundError extends BadRequestException {
    constructor() {
        super("Contact was not found");
    }
}
