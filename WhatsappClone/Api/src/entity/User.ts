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

    @Column({ nullable: true })
    validationToken: string;

    @Column({ default: false })
    valid: boolean;

}
