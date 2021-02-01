import express, { Express } from 'express';
import { createExpressServer, useContainer as useContainerRoutingControllers } from 'routing-controllers';
import { Connection, useContainer as useContainerTypeorm } from 'typeorm';
import { Container } from 'typedi';

import { restControllers } from './controller';
import { createSocketServer } from 'socket-controllers';

export class Application {

    private readonly BASE_ROUTE_PREFIX = '/api';

    private app: Express;

    constructor(private port: number, private connection?: Connection) {
        this.app = createExpressServer({
            routePrefix: this.BASE_ROUTE_PREFIX,
            controllers: restControllers
        });

        createSocketServer(port + 1, {

        })
    }

    public run(): Promise<void> {
        return new Promise((resolve) => {
            this.app.listen(this.port, () => {
                resolve();
            });
        });
    }

}

