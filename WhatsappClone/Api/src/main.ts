import { ValidationPipe } from '@nestjs/common';
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { SocketIoAdapter } from './socket/socket-v3.adapter';

async function bootstrap() {
  const app = await NestFactory.create(AppModule, { cors: { origin: '*' } });
  app.useGlobalPipes(new ValidationPipe());

  app.useWebSocketAdapter(new SocketIoAdapter(app, ['*']));
  await app.listen(3000);
}
bootstrap();
