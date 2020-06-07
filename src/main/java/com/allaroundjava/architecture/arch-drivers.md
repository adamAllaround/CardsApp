#Architectural drivers
This document lists important architectural drivers of credit card system

##Functional Requirements
* Operations data needs to be available to customers when application is running
* Operations data does not need to be available for long time or across application restarts.
* Statement information needs to capture all card operations for given period
* The same operation cannot be included in more than one statement
* Statement needs to be sent in a text form to user email address
* User can order a plastic card
* Users can modify plastic card data before its ordered
* Once ordered, users cannot modify card data

##Quality Attributes
* System should be observable
* The system needs to be configurable
* No message loss allowed in Card Operations
* Availability is not a concern
* Security is not a concern
* Eventual consistency in statement creation is ok
* Scalability is not a concern now

##Project constraints
* Time estimated for completion is 2 months
* App needs to be written in a known programming language
* No paid solutions should be used

##Conventions
* Single logging framework should be used - log4j
* Documentation is version controlled

##Project Goals
* Solution should enable learning
* The application should be a prototype - not an enterprise class solution

 

