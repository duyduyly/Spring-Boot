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
acme-user-service
├── build.gradle
├── Dockerfile
├── README.md
├── src
│   ├── main
│   │   ├── java
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
│   │               ├── V1__init_schema.sql
│   │               ├── V2__create_user_table.sql
│   │               └── R__create_user_view.sql
│   │
│   └── test
│       └── java/com.acme.user.userservice
│           └── user
│               └── service
│                   └── UserServiceTest.java

```

---

## 4. Class Naming Conventions

### Controller

```
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

```
<Domain>
```

Example: `User`

---

## 5. DTO Naming

### Request DTO

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

```
application.yml
application-dev.yml
application-prod.yml
```

### Property Keys

```
lowercase + dot + kebab-case
```

Example:

```yml
spring.datasource.url: jdbc:mysql://...
jwt.token-expiration: 3600
```

---

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

```
R__create_user_view.sql
```

---

## 13. JSON Naming

* snake_case

```json
{
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
