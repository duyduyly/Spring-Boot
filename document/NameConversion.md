# Name Conventions in Spring Framework

```text
acme-user-service
├── build.gradle
├── settings.gradle
├── Dockerfile
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── acme
│   │   │           └── user
│   │   │               └── userservice
│   │   │                   ├── UserServiceApplication.java
│   │   │                   │
│   │   │                   ├── user
│   │   │                   │   ├── controller
│   │   │                   │   │   └── UserController.java
│   │   │                   │   │
│   │   │                   │   ├── service
│   │   │                   │   │   ├── UserService.java
│   │   │                   │   │   └── UserServiceImpl.java
│   │   │                   │   │
│   │   │                   │   ├── repository
│   │   │                   │   │   └── UserRepository.java
│   │   │                   │   │
│   │   │                   │   ├── model
│   │   │                   │   │   └── User.java
│   │   │                   │   │
│   │   │                   │   ├── dto
│   │   │                   │   │   ├── CreateUserRequest.java
│   │   │                   │   │   ├── UpdateUserRequest.java
│   │   │                   │   │   └── UserResponse.java
│   │   │                   │   │
│   │   │                   │   └── mapper
│   │   │                   │       └── UserMapper.java
│   │   │                   │
│   │   │                   ├── auth
│   │   │                   │   ├── controller
│   │   │                   │   │   └── AuthController.java
│   │   │                   │   ├── service
│   │   │                   │   │   └── AuthService.java
│   │   │                   │   └── model
│   │   │                   │       └── Token.java
│   │   │                   │
│   │   │                   ├── config
│   │   │                   │   ├── SecurityConfig.java
│   │   │                   │   ├── SwaggerConfig.java
│   │   │                   │   └── JwtProperties.java
│   │   │                   │
│   │   │                   ├── exception
│   │   │                   │   ├── GlobalExceptionHandler.java
│   │   │                   │   └── UserNotFoundException.java
│   │   │                   │
│   │   │                   └── common
│   │   │                       ├── constants
│   │   │                       │   └── ErrorCodes.java
│   │   │                       └── util
│   │   │                           └── DateUtils.java
│   │   │
│   │   └── resources
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── db
│   │           └── migration
│   │               └── V1__create_user_table.sql
│   │
│   └── test
│       └── java
│           └── com.acme.user.userservice
│               ├── user
│               │   ├── service
│               │   │   └── UserServiceTest.java
│               │   └── controller
│               │       └── UserControllerTest.java
│               └── auth
│                   └── AuthServiceTest.java

```



## Project name
- Rule:
  - lowercase letters
  - words separated by hyphens (`-`)
  - kebab-case format `my-spring-project`

```text
<company>-<domain>-<service>

Example: 
    acme-inventory-order-service
    acme-notification-service
    acme-auth-service
```

## Package names
### Root Package
```text
com.<company>.<domain>.<service>

Example:
    com.acme.inventory.orderservice
    com.acme.inventory.orderservice
    com.acme.inventory.authservice
```

#
### Controller, Service, Repository, Component class names
- Rule:
  - PascalCase format
  - Suffix with the type of class (e.g., Controller, Service, Repository, Component)
```java 
<Domain>Controller

Example:        
    UserController
    UserService
    UserRepository
    UserComponent
    UserConfig
    UserMapper
```

#
## Model Entity Dto(REQUEST / RESPONSE) enums class names

```text
<Domain><Type>

Example:
    UserEntity
    UserDto
    UserRequest
    UserResponse
    UserStatusEnums (enum)
```
#
## Yaml and Properties files
### Yaml ơr Properties
```text
application.yml
application-dev.yml
application-prod.yml

application.properties
application-dev.properties
application-prod.properties
```

#
### Properties keys
- Rule:
  - lowercase letters
  - words separated by hyphens (`-`)
  - kebab-case format
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.jpa.hibernate.ddl-auto=update
logging.level.org.springframework=INFO  
```

#
## Bean names
- Rule:
  - camelCase format

```java
@Bean("myCustomBean")
```

#
## Constants
- Rule:
  - UPPER_SNAKE_CASE format

```java
public static final String DEFAULT_USER_ROLE = "USER";
public static final int MAX_LOGIN_ATTEMPTS = 5;
```

#
## Test class names
- Rule:
  - PascalCase format

```java
<className>Test

Example:
    UserServiceTest
    UserControllerTest
```

#
## Migration files
- Rule:

```text
V<version>__<description>.sql

Example:
    V1__create_user_table.sql
    V2__add_email_column_to_user_table.sql
```

#
## Json
- Json names use `snake_case` convention.
```json
{
  "user_name": "Alan",
  "created_at": "2026-01-01"
}
```


