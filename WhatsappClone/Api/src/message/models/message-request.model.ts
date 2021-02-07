export class MessageRequestModel {
  constructor(
    readonly text: string,
    readonly chatId: string,
    readonly userId: string,
  ) {}
}
