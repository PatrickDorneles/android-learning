import {Entity, PrimaryGeneratedColumn, Column, PrimaryColumn} from "typeorm";
import { uuid } from "uuidv4";

@Entity()
export class User {

    @PrimaryColumn()
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
