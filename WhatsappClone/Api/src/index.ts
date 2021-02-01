import 'reflect-metadata';

import { useContainer as useContainerValidator } from "class-validator";
import { useContainer as useContainerRoutingControllers } from "routing-controllers";
import {createConnection, useContainer as useContainerTypeorm} from "typeorm";
import { ExpressApplication } from "./ExpressApplication";
import { Container } from "typedi";
import { env } from 'process';


useContainerTypeorm(Container);
useContainerRoutingControllers(Container);
useContainerValidator(Container);

createConnection().then(async connection => {

    const PORT = parseInt(env.PORT) || 3000;

    const app = new ExpressApplication(PORT, connection);

    await app.run();
    console.log(`Application running on http://localhost:${PORT}`);
        

}).catch(error => console.log(error));
