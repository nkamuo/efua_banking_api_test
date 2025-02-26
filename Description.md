Installation Instruction.
Use the commandline to run the applidation and access it from the web once runnming.

```cmd
mvn spring-boot:run
```
By default the application is exposed on port ``8080`` and available at 
```http
http://localhost:8080
```

There are also several tests defined as per the Test instructions

```cmd
mvn test
```

***This project provides 4 data models***
 
 - Employee
 - Customer
 - Account
 - Transfer

**Employee** - represents a bank worker that can manipulate Customer accounts. They basically function as Users.

**Customer** - These are people that have accounts with this bank

**Account** - Represent Each of the many possible accounts belonging to the Customers

**Transfer** - This represents the Transfer of funds from one account to another.


RESTful Endpoint
-

**OpenAPI**

[Swagger UI](http://localhost:8080/swagger-ui/index.html) |
[API Docs](http://localhost:8080/v3/api-docs)
-------------------------------------------------

**Employee** - requires `ROLE_SUPERUSER`
```http
http://localhost:8080/api/employees
```
*Create new Employee*
```http
[POST] http://localhost:8080/api/employees
```
---------

**Customer** - requires `ROLE_ADMIN` - all employees can access
```http
http://localhost:8080/api/customers
```
--------

**Account** - requires `ROLE_ADMIN` - all employees can access
```http
http://localhost:8080/api/accounts
```
----------
**Transfer** - requires `ROLE_ADMIN` - all employees can access
```http
http://localhost:8080/api/transfers
```


