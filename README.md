# AccountsAPI Application Readme

## Overview

The AccountsAPI is a Java-based web application built using the Spring Framework. It provides RESTful services for managing user accounts, transactions, and balances. The application uses an H2 in-memory database, JDBC, JPA (Java Persistence API), HAL (Hypertext Application Language), Lombok, JUnit, and follows the MVC (Model-View-Controller) architectural pattern. The application is developed using Java 17.

## Architecture

### Why the Given Architecture?

- **Spring Framework:** The Spring Framework was chosen for its comprehensive features, modular design, and support for building scalable and maintainable applications. It offers a wide range of modules, including Spring MVC for building RESTful web services, and Spring Data JPA for simplifying data access with JPA.

- **H2 Database:** H2 is an in-memory database that provides a lightweight and fast solution for development and testing purposes. It helps simplify the setup and teardown of databases during testing.

- **JDBC and JPA:** JDBC is used for low-level database access, while JPA provides a higher-level, object-oriented abstraction for interacting with relational databases. This combination allows for efficient and flexible data access.

- **HAL (Hypertext Application Language):** HAL is a simple format that enables a consistent and standardized way to express the relationships between resources in a hypermedia-driven RESTful API. It enhances discoverability and provides a uniform structure for responses.

- **Lombok:** Lombok is used to reduce boilerplate code in Java classes. It helps maintain clean and concise code by automatically generating common methods such as getters, setters, and builders.

- **JUnit:** JUnit is utilized for writing unit tests to ensure the reliability and correctness of the application's components.

- **MVC (Model-View-Controller):** The MVC architectural pattern is employed to separate concerns and organize code in a way that enhances maintainability. Controllers handle incoming requests, models manage the application's data, and views render the presentation.


## User Authentication and Authorization

### Ensuring Accounts are Accessed Only by Owners

The application could use Spring Security to handle user authentication and authorization.

1. **User Authentication:** Users are required to authenticate themselves by providing valid credentials (username and password). Spring Security can be configured to use JDBC-based authentication, where user details and roles are stored in the database.

2. **Token-Based Authorization:** Upon successful authentication, the application can issue a token (JWT or OAuth2 token) to the authenticated user. This token contains information about the user's roles and permissions.

3. **Authorization Checks:** Each protected endpoint in the application can be annotated with `@PreAuthorize` or `@Secured` annotations, specifying the required roles or permissions.

   ```java
    @PreAuthorize("hasRole('ROLE_USER')")
   @GetMapping("api/accounts/getAllAccountsOnUser/{accountId}")
   public List<EntityModel<Account>> getAllOnUser(@PathVariable Long accountId) {
   }
   ```

## Monitoring in Production

A brief section on monitoring the application in production can include the following:

1. **Logging:** Implement comprehensive logging using frameworks like SLF4J and Logback. Log important events, errors, and performance metrics. Proper logging helps in diagnosing issues and monitoring application behavior.

2. **Application Metrics:** Integrate with monitoring solutions such as Prometheus or Micrometer to collect application-specific metrics. Monitor metrics related to database queries, HTTP requests, and other critical components.

3. **Regression tests:** Having tests that run every night, would ensure that incompatibilities with new and old code would be caught before release

4. **Alerting:** Alerts can notify administrators or operations teams of potential issues, allowing for proactive responses.

5. **Daily image creation and deployment of long-lived branches:** Setting up a jenkins pipeline that builds images of the main branches and uploads them to nexus, would allow for further monitoring. This can be done by deploying different docker environments where the pods can be monitored.

## How to use the api

The database in being populated from the 'LoadDatabase' class. It currently has 4 users created. This means that the system can be tested from http://localhost:8080/swagger-ui/index.html by using CRU operations (Delete operations were not implemented).

Example 1 - Using the api:
1. Download the repository and open it as from intelliJ.
2. Run the AccountApiApplication
3. Access http://localhost:8080/swagger-ui/index.html 
4. Choose the GET operation /api/users/getAll
5. Press 'Try it out' - Execute
6. See all the registered users.

Example 2 - Testing creating accounts:
1. Follow steps 1-3
2. Choose the POST operation /api/accounts/newAccount
   2.1. Write "Poul" or any of the other users in the username field
   2.2. Write an account name that that follows the regex pattern: [a-zA-Z0-9]
   2.3. Execute
3. Go the GET operation /api/accounts/getAllAccounts or /api/accounts/getAllAccountsOnUser
4. See that the account(s) you created were saved

Example 3 - Testing adding funds to an account
1. If you are still in the same session as "Example 2", then you can simply go to /api/transactions/addFunds
2. Fill out the 'toAccountId' field with the id of an account created in Example 2.
3. Fill out the 'amountToTransfer' field with a numeric value, f.x. 100.
4. Alternatively to step 2, you can input an invalid accountId and see the custom error.