import { Inject, Service } from "typedi";
import { User } from "../../entity/User";
import { UserRepository } from "../../repository/UserRepository";

@Service()
export class FindUserByPhoneNumberService {

    @Inject()
    private readonly userRepository: UserRepository;
    
    public async findUserByPhone(phoneNumber: string) {
        return await this.userRepository.findOne(undefined, { where: { phoneNumber }});
    }

}
