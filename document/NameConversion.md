# Spring Boot Naming Convention (Production Ready)

## 1. Project Name

**Format**

```
<company>-<domain>-<service>
```

**Rules**

* lowercase
* kebab-case
* business-oriented (not technical)

**Examples**

* `acme-user-service`
* `acme-auth-service`
* `acme-notification-service`

---

## 2. Root Package

**Format**

```
com.<company>.<domain>.<service>
```

**Example**

```
com.acme.user.userservice
```

---

## 3. Recommended Project Structure (Domain-based)

```
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
│   │   │   └── com.acme.user.userservice
│   │   │       ├── UserServiceApplication.java
│   │   │
│   │   │       ├── user
│   │   │       │   ├── controller
│   │   │       │   │   └── UserController.java
│   │   │       │   ├── service
│   │   │       │   │   ├── UserService.java
│   │   │       │   │   └── UserServiceImpl.java
│   │   │       │   ├── repository
│   │   │       │   │   └── UserRepository.java
│   │   │       │   ├── model
│   │   │       │   │   └── User.java
│   │   │       │   ├── dto
│   │   │       │   │   ├── request
│   │   │       │   │   │   ├── CreateUserRequest.java
│   │   │       │   │   │   └── UpdateUserRequest.java
│   │   │       │   │   └── response
│   │   │       │   │       └── UserResponse.java
│   │   │       │   ├── mapper
│   │   │       │   │   └── UserMapper.java
│   │   │       │   └── validation
│   │   │       │       └── UserValidator.java
│   │   │
│   │   │       ├── auth
│   │   │       │   ├── controller
│   │   │       │   ├── service
│   │   │       │   └── model
│   │   │
│   │   │       ├── security
│   │   │       │   ├── filter
│   │   │       │   ├── handler
│   │   │       │   └── JwtTokenProvider.java
│   │   │
│   │   │       ├── event
│   │   │       │   └── UserCreatedEvent.java
│   │   │
│   │   │       ├── config
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   ├── SwaggerConfig.java
│   │   │       │   └── JwtProperties.java
│   │   │
│   │   │       ├── exception
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── UserNotFoundException.java
│   │   │
│   │   │       └── common
│   │   │           ├── constants
│   │   │           │   └── ErrorCodes.java
│   │   │           ├── response
│   │   │           │   └── ApiResponse.java
│   │   │           └── util
│   │   │               └── DateUtils.java
│   │   │
│   │   └── resources
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── db
│   │           └── migration
│   │               └── V1__create_user_table.sql
│   │               ├── V1__init_schema.sql
│   │               ├── V2__create_user_table.sql
│   │               └── R__create_user_view.sql
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
│       └── java/com.acme.user.userservice
│           └── user
│               └── service
│                   └── UserServiceTest.java

```

---

## 4. Class Naming Conventions

## Project name
- Rule:
  - lowercase letters
  - words separated by hyphens (`-`)
  - kebab-case format `my-spring-project`

```text
<company>-<domain>-<service>
### Controller

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
```

Example: `UserController`

### Service

```
<Domain>Service
<Domain>ServiceImpl
```

### Repository

```
<Domain>Repository
```

### Entity (JPA)

Example:        
    UserController
    UserService
    UserRepository
    UserComponent
    UserConfig
    UserMapper
```
<Domain>
```

Example: `User`

---

## 5. DTO Naming

#
## Model Entity Dto(REQUEST / RESPONSE) enums class names
### Request DTO

```text
<Domain><Type>
```
<Create|Update|Search><Domain>Request

Example:: `CreateUserRequest`
```

### Response DTO

```
<Domain>Response

Example:: `UserResponse`
```

❌ Avoid generic `UserDto`

---

## 6. Mapper

```
<Domain>Mapper

Example: `UserMapper`
```

---

## 7. Configuration Classes

```
<Feature>Config
<Feature>Properties
```

Examples:

* `SecurityConfig`
* `JwtProperties`

---

## 8. Exception Handling

### Custom Exception

```
<Domain><Reason>Exception
```

Example: `UserNotFoundException`

### Global Handler

```
GlobalExceptionHandler
```

---

## 9. Constants

```
<Domain>Constants
```

Fields:

```
UPPER_SNAKE_CASE

public static final String DEFAULT_ROLE = "USER";
public static final int MAX_LOGIN_ATTEMPTS = 5;
```

---

## 10. application.yml / properties

### File Naming

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

### Property Keys
#
## Bean names
- Rule:
  - camelCase format

```java
@Bean("myCustomBean")
```
lowercase + dot + kebab-case

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

```yml
spring.datasource.url: jdbc:mysql://...
jwt.token-expiration: 3600
    UserServiceTest
    UserControllerTest
```

---
#
## Migration files
- Rule:

## 11. Bean Naming

* camelCase
* Prefer method name
* Avoid explicit bean name unless required

```java
@Bean("passwordEncoder")
// or
@Bean
public PasswordEncoder passwordEncoder() {}
```

---

## 12. Migration Files (Flyway)

### Versioned Migration

```
```text
V<version>__<description>.sql
```

Examples:

```
V1__init_schema.sql
V2__create_user_table.sql
V3__add_email_to_user.sql
```

### Repeatable Migration

```
R__<description>.sql
```

Example:
    V1__create_user_table.sql
    V2__add_email_column_to_user_table.sql

```
R__create_user_view.sql
```

---

## 13. JSON Naming

* snake_case

#
## Json
- Json names use `snake_case` convention.
```json
{
  "user_name": "Alan",
  "user_id": 1,
  "created_at": "2026-01-01"
}
```

---

## 14. Test Naming

```
<ClassName>Test
```

Examples:

* `UserServiceTest`
* `UserControllerTest`

---

## 15. Key Principles

* Consistency over cleverness
* Domain-first, not layer-first
* Naming should explain business intent
* Easy to scale, easy to onboard
