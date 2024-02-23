# myShop
A Shopping App based on Event-Driven, Microservices &amp; Micro-Frontend architecture.

# Event-Driven Microservices with Spring Boot and AWS

## Overview
This project is an example of building an event-driven microservices architecture using Spring Boot and various AWS services. It demonstrates how to design, develop, and deploy serverless applications that leverage AWS Lambda, API Gateway, EventBridge, SQS, Step Functions, DynamoDB, and more.

## Features
- Implementation of AWS Lambda functions using Spring Cloud Function
- Integration with AWS services such as API Gateway, EventBridge, SQS, Step Functions, and DynamoDB
- Event-driven architecture for asynchronous communication between microservices
- Use of Terraform for infrastructure as code (IaC) deployment

## Technologies Used
- Spring Boot
- Spring Cloud Function
- AWS Lambda
- AWS API Gateway
- AWS EventBridge (formerly CloudWatch Events)
- AWS SQS (Simple Queue Service)
- AWS Step Functions
- AWS DynamoDB
- AWS S3
- Terraform
- IntelliJ IDEA (IDE)

## Getting Started
To get started with this project, follow these steps:
1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd event-driven-microservices`
3. Install dependencies: `mvn clean install`
4. Configure AWS credentials: `aws configure`
5. Deploy the infrastructure using Terraform: `terraform apply`
6. Build and deploy your Spring Boot application to AWS Lambda
7. Test the deployed application by invoking Lambda functions or triggering events

## Contributing
Contributions are welcome! If you'd like to contribute to this project, please fork the repository, make your changes, and submit a pull request. Before submitting a pull request, make sure to follow the contribution guidelines.

## License
This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.
