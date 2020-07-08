#Architectural drivers
This document lists important architectural drivers of credit card system

##Functional Requirements
* Card Operations data needs to be available to customers when application is running
* Card Operations data does not need to be available for long time or across application restarts.
* Statement information needs to capture all card operations for given period
* The same operation cannot be included in more than one statement
* Statement needs to be sent in a text form to user email address
* User can order a plastic card
* Users can modify plastic card data before its ordered
* Once ordered, users cannot modify card data

##Quality Attributes
#### System should be observable
This is expressed with following metrics:
* 100% of business operations which result in write or update is logged.
* 100% of data exchanges with external systems is logged - queue or REST call
* 100% of exceptions are logged
* Application memory usage is tracked
* Application queue sizes is tracked

####System should be testable
This is expressed with following metrics: 
* 90% of End to End paths should be testable without UI. Threshold mi 80%. Ideal 100%
* Class coverage is XX% 

####Build time is short
This is expresed with: 
* Compile time including unit tests is no longer than 2 min on dev machine
* Full test suite runs no longer than 5 mins on dev machine. 

#### No message loss allowed in Card Operations
#### Availability is not a concern
#### Security is not a concern
#### Eventual consistency in statement creation is ok
#### Scalability is not a concern now

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

 

