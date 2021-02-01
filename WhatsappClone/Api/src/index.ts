import 'reflect-metadata';

import { useContainer as useContainerValidator } from "class-validator";
import { useContainer as useContainerRoutingControllers } from "routing-controllers";
import {createConnection, useContainer as useContainerTypeorm} from "typeorm";
import { Application } from "./Application";
import { Container } from "typedi";
import { env } from 'process';


useContainerTypeorm(Container);
useContainerRoutingControllers(Container);
useContainerValidator(Container);

createConnection().then(async connection => {

    const PORT = parseInt(env.PORT) || 3000;

    const app = new Application(PORT, connection);

    await app.run();
    console.log(`Application running on http://localhost:${PORT}`);
        

}).catch(error => console.log(error));
