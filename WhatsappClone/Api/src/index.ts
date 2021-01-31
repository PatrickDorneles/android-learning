import { useContainer as useContainerValidator } from "class-validator";
import "reflect-metadata";
import { useContainer as useContainerRoutingControllers } from "routing-controllers";
import Container from "typedi";
import {createConnection, useContainer as useContainerTypeorm} from "typeorm";
import {User} from "./entity/User";
import { ExpressApplication } from "./ExpressApplication";



useContainerTypeorm(Container);
useContainerRoutingControllers(Container);
useContainerValidator(Container);

createConnection().then(async connection => {

    const app = new ExpressApplication(3000, connection);

    await app.run();
    console.log(`Application running on http://localhost:${app.getPort}`);
        

}).catch(error => console.log(error));
