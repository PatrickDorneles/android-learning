# Whatsapp Clone
This application was built by me based on a Udemy's course on learning android, to learn and practice Android with Java, using some tweaks like Sockets and consuming REST APIs.

## The Application
The app is at simple as it sounds, a whatsapp basics clone, with messaging, signing in using sms (but also with email and password) and adding contacts.

## Topics
* [Tech](#tech)
* [Features](#features)
* [Installing And Running](#installing-and-running)
* [Screens](#screens)
* [Structure](#structure)
* [What Could Be Better](#what-could-be-better)

## Tech
The project is made in two applications: A Android Application and a Rest API with WebSockets.

The Android app was made in Java, with the following dependencies:
- **Retrofit 2** for consuming Rest APIs
- **Socket.IO Java Client** for Calls to WebSockets
- **Lombok** for Java Utilities Annotations
- **Android Annotations** for Android Utilities Annotations
- **Gson** as JSON converter
- **Material Components** for general application styling.

The Rest API was made in Typescript (NodeJS) using the framework NestJS, with the main dependencies:
- **NestJS** libs for general use in Dependency Injection, Routing, Middleware handling.
- **TypeORM** for database connection with ORM and query builder utilities;
- **JSON Web Tokens and NestJS JWT Utils** for authentication handling
- **Socket.IO Server V3 with NestJS Socket Utils** for socket server handling (NestJS doesn't currently has a Socket.IO v3 compatibility but there is an Adapter for it in the project)

Besides the application, the API uses TypeORM for connecting to a SQL database. As in the ormconfig.js file I was using a Postgres database but it can be changed withouth great code refactoring.

## Features
The Udemy's course suggests a simple concept: a messaging client that let its users have a chat, creating a email/password account.

In this project that concept is used together with other explored but not implemented ideas througout the course, as SMS messages for validating accounts.

- **Make an account:** you can sign up to the applicaiton by email or phone number, the SMS API I used, as far as I know, is available only in Brazil and I don't share any key for it in this Repo.

- **Sign in:** you as the user can sign in the application by email and password, or using your phone number. 

- **Add a contact:** as soon as you enter the app the first only available action is to add a contact by its email or phone number, so after this you can start a chat.

- **Send and receive messages in real time:** the app has real time messaging private only chats.

## Installing And Running
- **Prerequisites:**
    - NodeJS LTS installed
    - Android Studio and Java 8 installed
    - Android API 30 installed 
- **API**
    - ``cd Api``
    - ``npm i`` or ``yarn``
    - ``npm run build`` or ``yarn build``
    - for development run: ``npm run start:dev`` or ``yarn start:dev``
- **Android App**
    - Go into Android Studio
    - Open the **Android** folder project
    - Click ``File``>``Sync Project with Gradle Files``
    - Click ``Build``>``Make Project``
    - Run with ``Run``>``Run 'app'``
    - Or create a Bundle/APK in ``Build``>``Generate Signed Bundle / APK``

## Screens
// TODO

## Structure

### Android Application
The Android Application is a self made structure, so there are some concepts that can be erroneous or misleading.

    - Java Main or (com.androidlearning.whatsappclone)
        - activities: app general activities, could have subfolders if needed
        - adapters: list adapters
        - config: configuration java files, now used to stocking app constants
        - factories: factories for creating some only one configuration objects
        - general: not used in the project
        - helpers: util classes, for both handling common use tweaks like Gson or Socket
            - preferences: shared preferences handling classes
        - inputs: classes templates for both socket and rest emitting events
        - models: template classes for internal concepts or received objects
        - utils: generic utilitary classes
    - Res
        - drawable: folder for icons, images and view customization in general
        - layout: screens and base layouts for each part of an app such like activities, fragments and list items
        - menu: menu configuration layouts
        - mipmap: as far as I know, laucher icons
        - values: general values for everything, like colors, strings, dimens and styles

### NestJS API
NestJS has a Angular like modulated structure, with some main concepts, modules should be used for almost everything in the app, but I like to use a self made pattern using @ as prefix for every global folder.

    - ..everything ouside src: configuration files, build files and e2e testing
    - src: main folder where all modules of a standard nest app stand
        - @config: config files and services, now only a constants file folder
        - @error: all application made exceptions
        - auth: auth module - handles authentication, provide guards and authed user getter function
        - chat: chat module - has chat entity and handle all interactions with it
        - message: message module - has message entity and handle all interactions with it
        - socket: socket module - only has a socket gateway to handle socket calls
        - user: user module - handles user sign up, and general user related operations
        - app.module.ts: main application module

## What Could Be Better
- [ ] **SQL Lite Inner DB:** make an implementation of the inner database
- [ ] **Email Api Integration:** integrate an email api to do validation and password changing with safety.
- [ ] **User and Message Search:** there is a search icon but there is no search feature in the application now. What could it be is a search for contacts and chats feature and if we want to go a little further a message search.
- [ ] **Search for Non-Contact Users:** the way you can add a user as contact now is pretty rough.

