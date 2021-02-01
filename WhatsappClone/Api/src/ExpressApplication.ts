import express, { Express } from 'express';
import { createExpressServer, useContainer as useContainerRoutingControllers } from 'routing-controllers';
import { Connection, useContainer as useContainerTypeorm } from 'typeorm';
import { Container } from 'typedi';

import { controllers } from './controller';

export class ExpressApplication {

    private readonly BASE_ROUTE_PREFIX = '/api';

    private app: Express;

    constructor(private port: number, private connection?: Connection) {
        this.app = createExpressServer({
            routePrefix: this.BASE_ROUTE_PREFIX,
            controllers
        });
    }

    public run(): Promise<void> {
        return new Promise((resolve) => {
            this.app.listen(this.port, () => {
                resolve();
            });
        });
    }

}

