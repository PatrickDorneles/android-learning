import { Socket } from 'socket.io';

export class ConnectedUserModel {
  constructor(public id: string, public socket: Socket) {}
}
