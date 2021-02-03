import { Service } from "typedi";

const MIN_TOKEN_NUMBER = 1;
const TOKEN_CEILING_BASE = 10;
const CEILING_TO_MAX_DIFFERENCE = 1;

@Service()
export class ValidationTokenFactory {    

    public createToken(tokenSize: number) {
        const tokenNumberCeiling = TOKEN_CEILING_BASE ** tokenSize;
        const maxTokenNumber = (tokenNumberCeiling - CEILING_TO_MAX_DIFFERENCE);

        const randomNumber = Math.random();
        
        const highFlooredNumber = Math.floor(randomNumber * tokenNumberCeiling);
        const minComparedNumber = Math.min(maxTokenNumber, highFlooredNumber);
        const maxComparedNumber = Math.max(MIN_TOKEN_NUMBER, minComparedNumber);

        const token = this.zeroPad(maxComparedNumber, tokenSize);
        
        return token;
    }

    private zeroPad = (num: number, places: number) => String(num).padStart(places, '0');

}

