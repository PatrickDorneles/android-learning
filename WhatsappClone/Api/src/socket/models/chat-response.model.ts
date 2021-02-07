import { MessageResponseModel } from '@/message/models/message-response.model';
import { UserResponseModel } from '@/user/models/user-response.model';

export class ChatResponseModel {
  constructor(
    readonly id: string,
    readonly contact: UserResponseModel,
    readonly messages: MessageResponseModel[],
  ) {}
}
