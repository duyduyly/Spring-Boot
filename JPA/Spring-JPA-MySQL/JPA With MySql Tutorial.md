# JPA Tutorial

- [**Connecting MySql**](#connecting-mysql)
- [**Relationship**](src/main/java/com/alan/entity_mapping/mapping/Relationship.md)
- [**Entity Listener**](src/main/java/com/alan/entity_listener/EntityListener.md)
- [**Data Type Mapping**](src/main/java/com/alan/entity_mapping/data_type/Data%20Type%20Mapping.md)
- [**@Transaction**](#transaction-annotation)
  - [*What is @Transaction?*](#what-is-transactional-)
  - [*Note*](#note)
  - [*Where To Use?*](#where-to-use)
  - [*Rollback Behavior*](#-rollback-behavior)
  - [*Transaction Propagation Example*](#transaction-propagation-example)

#
## Connecting MySql
### MySql Dependency Needed

```pom
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		
		<dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.3.0</version> <!-- or latest -->
        </dependency>
```


### Properties Config
#### yaml
```properties
spring:
  datasource:
    username: root
    url: jdbc:mysql://localhost:3306/db_test
    password: *****
    driverClassName: com.mysql.cj.jdbc.Driver
  jpa:
    properties:
      hibernate:
        globally_quoted_identifiers: true
        format_sql: true
    show-sql: false
    hibernate:
      ddl-auto: update
```

#### .properties
```properties
# Datasource Configuration
spring.datasource.username=root
spring.datasource.url=jdbc:mysql://localhost:3306/db_test
spring.datasource.password=*****
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA and Hibernate Configuration
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.globally_quoted_identifiers=true
spring.jpa.properties.hibernate.format_sql=true
```

------------------------
<br/>

## Transaction Annotation
### What is `@Transactional`? 
- The `@Transactional` annotation is used to manage database transactions. It ensures that a group of operations either complete successfully as one unit or fail entirely and roll back.
- If something fails → rollback.
- If all goes well → commit.

#
### Note
- `@Transactional` only works on public methods
- It must be called from outside the class, or proxy won't apply
- Does not apply to `private/internal` method calls
- Works only when Spring manages the bean (e.g., `@Service`, not new)

#
### Where to Use
| Place         | Meaning                                                      |
|---------------|--------------------------------------------------------------|
| On a `method` | Only that method is transactional                            |
| On a `class`  | All public methods in the class are transactional by default |

```java
@Service
@Transactional
public class UserService {
    public void registerUser(User user) {
        userRepository.save(user); // within transaction
    }
}
```
```java
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    @Transactional
    public void placeOrder(Order order) {
        orderRepository.save(order);     // 1. save order
        paymentService.charge(order);    // 2. charge payment

        // If charge fails → everything rolls back
    }
}
```

#
### Example 
- in example, We have two classes `TransactionModel1` and `TransactionModel2`
- it has Relationship `1-1`, so when you want to create Transaction, You `must create Both TransactionModel`
- If Just only one Model created, it can make error when you query 

[TransactionModel1.java](src/main/java/com/alan/transaction_annotation/entity/TransactionModel1.java)
```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="TRANSACTION_MODEL_1")
public class TransactionModel1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public TransactionModel1(String name) {
        this.name = name;
    }
}
```

[TransactionModel2.java](src/main/java/com/alan/transaction_annotation/entity/TransactionModel2.java)
```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="TRANSACTION_MODEL_2")
public class TransactionModel2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;

    // one to one mapping means,
    // one employee stays at one address only
    @OneToOne
    private TransactionModel1 transactionModel1;

    @Version
    private Long version;
}
```
- [TransactionModel1Repository.java](src/main/java/com/alan/transaction_annotation/repository/TransactionModel1Repository.java)
- [TransactionModel2Repository.java](src/main/java/com/alan/transaction_annotation/repository/TransactionModel2Repository.java)
```java
@Repository
public interface TransactionModel1Repository extends JpaRepository<TransactionModel1,Integer> {
}

@Repository
public interface TransactionModel2Repository extends JpaRepository<TransactionModel2,Integer> {
}
```

#
### Transaction Method Test
- Create Two method,Both Method also Set save(TransactionModel1)
- And Then Set TransactionModel1 in TransactionModel2(And TransactionModel2 Is Null)
- Difference 
  - Method `addTransactionTransactionAnnotation` use `@Transactional`
  - Method `addTransactionButNotTransactionAnnotation` not use

- And when Test you will see, method 1 Rollback `TransactionModel1`(not save in DB)
- And method two, save `TransactionModel1` into DB(it's wrong blueprint, can make error when select Data)

```java
    //don't save TransactionModel1 because Transaction was rollback TransactionModel1
    @Transactional(rollbackFor = Exception.class)
    public TransactionModel2 addTransactionTransactionAnnotation() {
        TransactionModel1 model1 = new TransactionModel1("Transaction 1");
        transactionModel1Repository.save(model1);

        TransactionModel2 model2 = null;
        model2.setTransactionModel1(model1);
        model2.setAddress("Address 1");

        return transactionModel2Repository.save(model2);
    }

    //save TransactionModel1, although TransactionModel2 Throw NullPointerException
    public TransactionModel2 addTransactionButNotTransactionAnnotation() {
        TransactionModel1 model1 = new TransactionModel1("Transaction 1");
        transactionModel1Repository.save(model1);

        TransactionModel2 model2 = null;
        model2.setTransactionModel1(model1);
        model2.setAddress("Address 1");

        return transactionModel2Repository.save(model2);
    }
```
- Call Method Use :
- Not Save `TransactionMode1` because it's rollback when `TransactionModel2` Throw Exception
```text
http://localhost:9999/api/transaction-annotation/create-transaction
```

- Not use `@Transaction`, It saved `TransactionModel1`, Although `TransactionModel2` throw Error
```text
http://localhost:9999/api/transaction-annotation/create-not-transaction
```

#
### ⚠️ Rollback Behavior
By default, Spring rolls back for unchecked exceptions (RuntimeException, Error).
```java
@Transactional(rollbackFor = RuntimeException.class)
```
 __🔄 Transaction Propagation:__

| Type                 | Meaning                                                               |
|----------------------|-----------------------------------------------------------------------|
| `REQUIRED` (default) | Use current transaction, or create new if none exists                 |
| `REQUIRES_NEW`       | Always create a new transaction (suspend current one if any)          |
| `MANDATORY`          | Must run within a transaction — throw exception if not                |
| `NEVER`              | Must run **outside** a transaction — throw exception if one exists    |
| `SUPPORTS`           | Use transaction if one exists, otherwise run non-transactional        |
| `NOT_SUPPORTED`      | Always run **non-transactional**, suspend if transaction exists       |
| `NESTED`             | Create a nested transaction (only works with JDBC and some platforms) |

#
### Transaction Propagation Example
- Parent Method Use `@Transaction` and Sub class use`@Transaction('Propagation')` to clearly designate open transactions for which situations.
- Mark Propagation in sub method to open transaction specific most
- For example: Save two tables `person` and `address` and must save `log` any times after `save person and address` , so you need to set specific Propagation for transaction (if save `person` error, but `log also save into db`)

```java
@Transactional(REQUIRES_NEW) //clear designation Propagation for sub method
public void saveLog(Log log) {
    //save log
}
```
```java
@Transactional
public void processOrder() {
    //save person
    //save address // but Error
  
    this.saveLog();//always save, If address or person save fails, log still saved
}
```

#
#### ✅ Propagation.REQUIRED (default, most common)
- You just want the method to run in a transaction, join an existing one if there is any.
```java
@Transactional // REQUIRED by default
public void placeOrder() {
    saveOrder(); // joins transaction
    reduceStock(); // joins same transaction
}
```

#
#### ✅ Propagation.REQUIRES_NEW
- You want to start a new transaction, even if one exists, and commit or rollback independently.
  - Logging/auditing even if main transaction fails
  - Sending email/SMS notifications
  - Retryable operations

```java
@Transactional(REQUIRES_NEW)
public void saveLog(Log log) {
    logRepository.save(log);
}
```
```java
@Transactional
public void processOrder() {
    orderRepository.save(...);         // part of main transaction
    logService.saveLog(...);           // separate transaction
    // If processOrder fails, log still saved
}
```

#
#### ✅ Propagation.NESTED
- You want a sub-transaction that can rollback separately, but doesn’t affect the main one unless you let it.
  - `Only works with JDBC and some platforms (like PostgreSQL).`
```java
@Transactional(NESTED)
public void updateInventory() {
    // If fails, can rollback only this part
}
```

#
#### ⚠️ Propagation.NOT_SUPPORTED
- You don’t want to use a transaction at all, even if one exists.
  - Running raw SELECT COUNT(*) or other non-critical reads
  - Performance-sensitive logging
```java
@Transactional(NOT_SUPPORTED)
public long getUserCount() {
    return userRepository.count();
}
```

#
#### ⚠️ Propagation.MANDATORY
- You want to force this method to run inside a transaction, or throw an error if no transaction exists.
  - Enforce correct transaction flow
  - Methods that should never be run standalone

```java
@Transactional(MANDATORY)
public void validateBeforeCommit() {
    // Throws exception if called without transaction
}
```

#
#### ❌ Propagation.NEVER
- Only allow this to run outside a transaction, throw exception if any exists.
- Rare — mostly used to prevent misuse in sensitive contexts.

#
#### ✅ Summary Table
| Propagation     | Common Use Case                          |
|-----------------|------------------------------------------|
| `REQUIRED`      | Default, join or create transaction      |
| `REQUIRES_NEW`  | Audit, log, notification, retry          |
| `NESTED`        | Rollback only part (if supported by DB)  |
| `NOT_SUPPORTED` | Run outside transaction (e.g. reporting) |
| `MANDATORY`     | Enforce run only within transaction      |
| `NEVER`         | Enforce no transaction (rare)            |
