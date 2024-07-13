# Shopping Application using Spring, MongoDb and Rabbit MQ

## Overview ##

This project is an e-commerce platform built using a microservices architecture. It consists of three core microservices, each with distinct responsibilities:

- **[Order Service](ms-order-service)**: Handles the creation and management of customer orders.
- **[Product Service](ms-product-service)**: Manages product information and inventory stock levels.
- **[Payment Service](ms-payment-service)**: Manages payment processing, integrating with Stripe for secure transactions.

## Architecture ##

Saga Choreography Pattern: Ensures data consistency across the microservices through distributed transactions.

* Each microservice performs its operations and publishes events that trigger actions in other microservices.
* No centralized coordinator is used; instead, each service listens for events and reacts accordingly.

Asynchronous Communication:
* Uses RabbitMQ to handle message passing between services.
* Enhances scalability and fault tolerance by decoupling services.

![Alt text](_assets/shopping-application-architecture.png?raw=true "Shopping Application Architecture")

## Workflow ##

Order Placement:
* A customer places an order through the Order Service.
* The Order Service sends a message to the Product Service to check stock availability.

Stock Verification:
* The Product Service verifies stock and responds with availability status.
* If the product is available, the Product Service sends a payment request to the Payment Service.

Payment Processing:
* The Payment Service processes the payment via Stripe.
* Upon successful payment, the Payment Service sends a confirmation message back to the Order Service.

Order Completion:
* The Order Service finalizes the order and updates its status in MongoDB.

![Alt text](_assets/shopping-application-workflow.png?raw=true "Shopping Application Workflow")

### EndPoints ###


| Service  | EndPoint                               | Port  | Method | Description                                     |
|----------|----------------------------------------|:-----:|:------:|-------------------------------------------------|
| Orders   | /v1/customers/{customerId}/orders      | 7500  |  POST  | Create an order                                 |
| Orders   | /v1/customers/{customerId}/orders      | 7500  |  GET   | Return a list of orders for a specific customer |
| Orders   | /v1/customers/{customerId}/orders/{id} | 7500  |  POST  | Return detail of specified order                |
| Products | /v1/products/{id}                      | 7501  |  GET   | Return detail of specified product              |
| Products | /v1/products                           | 7501  |  GET   | Return a list of products                       |
| Products | /v1/products                           | 7501  |  POST  | Insert a new product                            |
| Products | /v1/products/{id}                      | 7501  |  PUT   | Update a specific product                       |
| Products | /v1/products/{id}                      | 7501  | DELETE | Delete a specific product                       |

### Documentation and examples ###

#### OpenApi

- **ms-order-service** : http://localhost:7500/swagger-ui.html
- **ms-product-service** : http://localhost:7501/swagger-ui.html

## Rabbit MQ:

You can open Rabbit MQ : http://localhost:5672/

username/password: guest

## Build & Run

### Build

In the root folder run the maven command to build all the microservices:

```
mvn clean package
```

### Build as a container

Access each microservice folder and execute the command to build the docker image:

```
docker build -f Dockerfile -t [SERVICE_NAME]:1.0.0 .
```

### Run as a container

After the docker image was created, execute the command to run the container:

```
docker run -d -p [PORT]:[PORT]  --env PROFILE=local -i -t [SERVICE_NAME]:1.0.0
```

### Run using docker compose

There is a project to run all microservices and the third party services using docker compose

First, in the root folder execute the command bellow to build the microservices and generate the jar files

```
mvn clean install
```

#### Build the images and run all the containers

```
docker-compose up
```

#### Stop the containers

```
docker-compose down
```

## VERSIONS
1.0.0

## Technologies Used
* Spring Boot: Framework for building the microservices.
* Java 21: Programming language for implementing the services.
* MongoDB: NoSQL database for data storage.
* RabbitMQ: Message broker for asynchronous communication between services.
* Stripe: Third-party payment processing service.