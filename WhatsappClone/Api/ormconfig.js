module.exports = {
    type: "postgres",
    host: "localhost",
    port: 5432,
    username: "postgres",
    password: "root",
    database: "whatsapp_clone",
    synchronize: true,
    entities: [
        "dist/**/*.entity{.ts,.js}"
    ],
    subscribers: [
        "dist/**/*.subscriber{.ts,.js}"
    ],
    entitySchemas: [
        "dist/**/*.schema.json"
    ],
    migrations: [
        "dist/src/migrations/*{.ts,.js}"
    ],
    cli: {
        migrationsDir: "src/migrations"
    }
}
