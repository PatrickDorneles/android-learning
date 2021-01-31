import {Entity, PrimaryGeneratedColumn, Column} from "typeorm";
import { uuid } from "uuidv4";

@Entity()
export class User {

    @Column()
    id: string;

    @Column()
    name: string;

    @Column()
    phoneNumber: string;


    constructor(user: Omit<User, 'id'>, id?: string) {
        Object.assign(this, user);

        if(!id) {
            this.id = uuid();
        }
    }

}
