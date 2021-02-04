import {Entity, PrimaryGeneratedColumn, Column, PrimaryColumn} from "typeorm";

@Entity()
export class User {

    @PrimaryColumn()
    id: string;

    @Column()
    name: string;

    @Column({ nullable: true })
    email: string;

    @Column()
    password: string;

    @Column({ nullable: true })
    phoneNumber: string;

    @Column({ nullable: true })
    validationToken: string;

    @Column({ default: false })
    valid: boolean;

}
