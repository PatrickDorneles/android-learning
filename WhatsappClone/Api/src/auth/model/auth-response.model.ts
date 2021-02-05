import { UserResponseModel } from "@/user/models/user-response.model";

export class AuthResponseModel {

    constructor(
        public authToken: string,
        public user: UserResponseModel 
    ) {}

}