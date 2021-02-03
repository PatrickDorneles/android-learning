import { Service } from "typedi";
import { EntityRepository, Repository } from "typeorm";
import { User } from "../entity/User";

@Service()
@EntityRepository(User)
export class UserRepository extends Repository<User> {
    public async findOneByPhone(phoneNumber: string) {
        return await this.findOne(undefined, { where: { phoneNumber }});
    }

    public async findOneById(id: string) {
        return await this.findOne(id);
    }
}
