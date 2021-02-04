import {
  BadRequestException,
  ForbiddenException,
  UnauthorizedException,
} from '@nestjs/common';

export class EmailAlreadyInUseError extends ForbiddenException {
  constructor() {
    super('Your email is already in use.');
  }
}
