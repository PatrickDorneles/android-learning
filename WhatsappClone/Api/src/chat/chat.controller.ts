import { JwtAuthGuard } from '@/auth/jwt-auth.guard';
import { AuthUserHeaderModel } from '@/auth/model/auth-user-header.model';
import { Controller, Get, Param, UseGuards, Headers } from '@nestjs/common';
import { ChatService } from './chat.service';
import { ChatResponseModel } from './models/chat-response.model';

@Controller('chat')
export class ChatController {
  constructor(private readonly chatService: ChatService) {}

  @UseGuards(JwtAuthGuard)
  @Get('with-user/:id')
  public async getChatWithUser(
    @Param('id') contactId: string,
    @Headers() headers: AuthUserHeaderModel,
  ) {
    const chat = await this.chatService.findChatByContact(
      contactId,
      headers.authorization,
    );

    return ChatResponseModel.fromChat(chat, contactId);
  }

  @UseGuards(JwtAuthGuard)
  @Get()
  public async getAuthedUserChats(@Headers() headers: AuthUserHeaderModel) {
    return await this.chatService.findResponseChatsByAuth(
      headers.authorization,
    );
  }
}
