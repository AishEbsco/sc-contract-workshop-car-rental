# Spring Cloud Contract - Workshop (Car Rental & Fraud services)

## 2 services used to demonstrate Spring Cloud Contract workflows and capabilities in this workshop are:
### fraud-service (provider)
### car-rental (consumer)

Use the diff links to see changes between branches.

| Branch                                                                                                                  | Feature                                      |
| ----------------------------------------------------------------------------------------------------------------------- | -------------------------------------------- |
| implementation - [diff](https://github.com/AishEbsco/sc-contract-workshop-car-rental/compare/base...implementation)                                                                        | Base implementation of the services
| add-contracts - [diff](https://github.com/AishEbsco/sc-contract-workshop-car-rental/compare/implementation...add-contracts)                                                                        | Adding consumer-contract (hand-written) and spring-cloud dependencies on both services
| stubrunner - [diff](https://github.com/AishEbsco/sc-contract-workshop-car-rental/compare/add-contracts...stubrunner)                                                                        | StubRunner implementation on consumer
| rest-docs - [diff](https://github.com/AishEbsco/sc-contract-workshop-car-rental/compare/stubrunner...rest-docs)                                                                        | RestDocs implementation on consumer to automatically generate groovy contract


### Based on YouTube talk by Marcin Grzejszczak and Josh Long
https://www.youtube.com/watch?v=MDydAqL4mYE 