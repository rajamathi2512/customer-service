# customer-service
The Customer  microservice is the primary service to retrieve a "Customer" resource from the XXX bank.

With the help of this service you can retrieve a list of all customer for a specific user or retrieve the full
details of a specific customer.

A Customer service holds (among other things) the following properties:
- Id
- FirstName
- LastName
- Age
- Address

For a full list of properties and endpoints that are available please consult the swagger documentation:   
http://localhost:8085/swagger-ui.html when running locally
# API first design
This api is build using api first design practices

#Running a local workspace
__default__  
Makes sure logging gets sent to console