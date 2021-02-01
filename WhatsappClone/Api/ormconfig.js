export default {
    type: "postgres",
    host: "localhost",
    port: 5432,
    username: "postgres",
    password: "pass",
    database: "whatsapp_clone",
    synchronize: true,
    entities: [
        "src/entity/*.ts"
    ],
    subscribers: [
        "src/subscriber/*.ts"
    ],
    entitySchemas: [
        "src/schema/*.json"
    ],
    migrations: [
        "src/migration/*.ts"
    ],
    cli: {
        entitiesDir: "src/entities",
        migrationsDir: "src/migrations",
        subscribersDir: "src/subscribers"
    }
}