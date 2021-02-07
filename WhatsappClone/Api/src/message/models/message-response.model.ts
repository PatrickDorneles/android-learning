import { UserResponseModel } from '@/user/models/user-response.model';

export class MessageResponseModel {
  constructor(
    readonly text: string,
    readonly user: UserResponseModel,
    readonly chatId: string,
    readonly dateTime: Date,
  ) {}
}
