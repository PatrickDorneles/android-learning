import { BadRequestError } from "routing-controllers";
import { Inject, Service } from "typedi";
import { InvalidValidationTokenError } from "../../error/InvalidValidationTokenError";
import { UserDoesNotExistsError } from "../../error/UserDoesNotExistsError";
import { UserRepository } from "../../repository/UserRepository";

@Service()
export class ValidateUserTokenService {

    @Inject()
    private readonly userRepository: UserRepository;

    private async validate(token: string, id: string) {
        const user = await this.userRepository.findOneById(id);

        if(!user) {
            throw new UserDoesNotExistsError();
        }

        const isTokenValid = user.validationToken === token;

        if(!isTokenValid) {
            throw new InvalidValidationTokenError();
        }

    }

}
