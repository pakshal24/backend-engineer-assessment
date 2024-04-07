# Getting Started

**IMPORTANT: Do not send pull requests to this repository. This is a template repository and is not used for grading. Any pull requests will be closed and ignored.**

## Introduction

If you are reading this, you are probably have received this project as a coding challenge. Please read the instructions
carefully and follow the steps below to get started.

## Setup

### Pre-requisities

To run the application you would require:

- [Java](https://www.azul.com/downloads/#zulu)
- [Temporal](https://docs.temporal.io/cli#install)
- [Docker](https://docs.docker.com/get-docker/)
- [Stripe API Keys](https://stripe.com/docs/keys)

### On macOS:

First, you need to install Java 21 or later. You can download it from [Azul](https://www.azul.com/downloads/#zulu) or
use [SDKMAN](https://sdkman.io/).

```sh
brew install --cask zulu21
```

You can install Temporal using Homebrew

```sh
brew install temporal
```

or visit [Temporal Installation](https://docs.temporal.io/cli#install) for more information.

You can install Docker using Homebrew

```sh
brew install docker
```

or visit [Docker Installation](https://docs.docker.com/get-docker/) for more information.

### Other platforms

Please check the official documentation for the installation of Java, Temporal, and Docker for your platform.

### Stripe API Keys

Sign up for a Stripe account and get your API keys from the [Stripe Dashboard](https://dashboard.stripe.com/apikeys).
Then in `application.properties` file add the following line with your secret key.

```properties
stripe.api-key=sk_test_51J3j
```

## Run

You are required to first start the temporal server using the following command

```sh
temporal server start-dev
```

and then run the application using the following command or using your IDE.

```sh
./gradlew bootRun
```

### Other commands

#### Lint
To run lint checks, use the following command

```sh
./gradlew sonarlintMain
```

#### Code Formatting
To format the code, use the following command

```sh
./gradlew spotlessApply
```

## Guides

The following guides illustrate how to use some features concretely:

- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Temporal Quick Start](https://docs.temporal.io/docs/quick-start)
- [Temporal Java SDK Quick Guide](https://docs.temporal.io/dev-guide/java)
- [Stripe Quick Start](https://stripe.com/docs/quickstart)
- [Stripe Java SDK](https://stripe.com/docs/api/java)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

- postgres: [`postgres:latest`](https://hub.docker.com/_/postgres)

Please review the tags of the used images and set them to the same as you're running in production.


### Implementaion Approach

According to the task file, The Motive of this application is to integrate a third party payment service into our system and that third
party payment service is Stripe.

There are 3 Api endpoints in the project
1. Signup : Here User create account with us and simultaneously our application creates user's stripe account and stripe's account id is stored in out database as well
2. Update : Here User can patch update the details email, firstname, lastname. The update is also reflected in the stripe's database as well.
3. GetAccounts: Here we fetch all the users data from our database

All the implememntaion is done using temporal workflow as asked.
Created createUserWorkflow and UpdateUserWorkflow.

### Project Setup and Requirements

On System
1. Java
2. Temporal Cli
3. Docker Desktop
4. PostgreSQL Database
5. Stripe Account


Steps to Run the Project
1. Git Clone https://github.com/pakshal24/backend-engineer-assessment.git
2. create Database in postgres
3. Make changes in compose.yml file edit line no. 6,7,8 and 21,22,23 as per your credentials
4. Add 'stripe.api-key' genrated from your stripe account in src/main/resources.application.properties file.
5. start the docker
6. start temporal server using this command

```sh
temporal server start-dev
```
6. Start The Application with this command

```sh
./gradlew bootRun
```
7. Hit the Apis using postman
    1. UserSignup - localhost:8080/createaccount
        Body->raw->json
            {
            "firstName":"Abhishek",
            "lastName": "mahagan",
            "email":"abhi.p@gmail.com"
                }

    2. Userupdate - localhost:8080/accounts/id
        Body->raw->json
            {
            "firstName":"Abhishek",
            "lastName": "mahagan",
            "email":"abhi.p@gmail.com"
                }
        Patch Update you can update any of these fields or all the fields

    3. Get All User - localhost:8080/accounts/all
        Shows all the registerd users.


### Video Implementation
    https://drive.google.com/file/d/1_UaJS2AThOQdgsM3FxC0aQ2yK9FjpIwq/view?usp=sharing