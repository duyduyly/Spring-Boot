# Entity Listener

- [List Lifecycle Hooks](#-list-of-lifecycle-hooks-you-can-use)
- [Setup](#setup)
- [Example 1 Default](#example-1-default)
- [Example 2 Custom Auditing](#example-2-custom-auditing-class)
- [More Example](#bonus)

## 🔁 List of Lifecycle Hooks You Can Use
| Annotation     | Triggered When?                |
|----------------|--------------------------------|
| `@PrePersist`  | Before saving **new** entity   |
| `@PostPersist` | After saving **new** entity    |
| `@PreUpdate`   | Before updating an entity      |
| `@PostUpdate`  | After updating an entity       |
| `@PreRemove`   | Before deleting an entity      |
| `@PostRemove`  | After deleting an entity       |
| `@PostLoad`    | After loading from DB (SELECT) |


## Setup
1. The First: Enable `@EnableJpaAuditing` in `main.claas` or `configure class` 
```java
@SpringBootApplication
@EnableJpaAuditing
public class JpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

}
```

2. Add Annotation `@EntityListeners(AuditingEntityListener.class)` or `@EntityListeners(AuditingEntityListenerCustom.class)` in Entity model
```java
@EntityListeners(AuditingEntityListener.class)

//or

@EntityListeners(AuditingEntityListenerCustom.class) // if you want to use custom Auditing class of you

```

3. Add Annotation LifeCycle hook (you can see here: [Life Cycle](#-list-of-lifecycle-hooks-you-can-use)) 
```java

    // for @EntityListeners(AuditingEntityListener.class) //default class framework supported 
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    
    //OR

    //for @EntityListeners(AuditingEntityListenerCustom.class) which this is your Auditing custom class  
    //Check Email After Store into DB
    @PrePersist
    public void prePersist(Model2 e) {
        if (!e.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email is required!");
        }
        e.setCreatedAt(LocalDateTime.now());
    }
```
---------------------
<br/>


## Example 1 Default
```java
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

@Entity
@Table(name = "ENTITY_LISTENER")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

- you can test Here
- createAt and updatedAt updated when `save`
```text
http://localhost:9999/api/auditing-entity/default?id=0
```
```text
Model(id=1, name=null, createdAt=2025-07-31T09:35:20.330782800, updatedAt=2025-07-31T09:35:20.330782800)
```

- Update: `name` updated and `updatedAt` also updated
```text
http://localhost:9999/api/auditing-entity/default?id=1
```
```text
Model(id=1, name=Updated, createdAt=2025-07-31T09:35:20.330783, updatedAt=2025-07-31T09:35:55.584673800)
```

---------------------
<br/>


## Example 2 Custom Auditing class
```java
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "ENTITY_LISTENER2")
@EntityListeners(AuditingEntityListenerCustom.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Model2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Builder.Default
    private boolean isDeleted = false; // default is false

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Model2{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", isDeleted=" + isDeleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
```
```java
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class AuditingEntityListenerCustom {


    //Check Email After Store into DB
    @PrePersist
    public void prePersist(Model2 e) {
        if (!e.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email is required!");
        }
        e.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Model2 e) {
        e.setUpdatedAt(LocalDateTime.now());
    }


    //Send mail after create (Example User)
    @PostPersist
    public void notifyNewUser(Model2 e) {

        //send mail if you have notification Service
        System.out.println("New user created: " + e.getEmail());
    }


    @PreRemove
    public void preRemove(Model2 e) {
        log.info("Remove user: " + e.getEmail());
    }
}
```
### Test Custom Auditing Class
- Save Success with `Email`
```text
http://localhost:9999/api/auditing-entity/custom?id=0&email=alan@gmail.com
```
```text
Model2{id=1, email='alan@gmail.com', isDeleted=false, createdAt=2025-07-31T09:41:44.460175200, updatedAt=null}
```

- Save with email not contains @
- Error Because in Auditing class catches if email not contains @, it will throw error
```java
    //Check Email After Store into DB
    @PrePersist
    public void prePersist(Model2 e) {
        if (!e.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email is required!");
        }
        e.setCreatedAt(LocalDateTime.now());
    }
```
```text
http://localhost:9999/api/auditing-entity/custom?id=0&email=alan
```
```text
java.lang.IllegalArgumentException: Email is required!
```

- Update: UpdatedAt Updated Date
```java
 @PreUpdate
    public void preUpdate(Model2 e) {
        e.setUpdatedAt(LocalDateTime.now());
    }
```
```text
http://localhost:9999/api/auditing-entity/custom?id=1&email=alanUpdated@Gmail.com
```
```text
Model2{id=1, email='alanUpdated@Gmail.com', isDeleted=false, createdAt=2025-07-31T09:41:44.460175, updatedAt=2025-07-31T09:49:03.666789900}
```

---------------------
<br/>


## Bonus
- Can mark multiple Annotation to use for situations needed
```java
public class AuditTrailListener {
    private static Log log = LogFactory.getLog(AuditTrailListener.class);
    
    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(User user) {
        if (user.getId() == 0) {
            log.info("[USER AUDIT] About to add a user");
        } else {
            log.info("[USER AUDIT] About to update/delete user: " + user.getId());
        }
    }
    
    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(User user) {
        log.info("[USER AUDIT] add/update/delete complete for user: " + user.getId());
    }
    
    @PostLoad
    private void afterLoad(User user) {
        log.info("[USER AUDIT] user loaded from database: " + user.getId());
    }
}
```

- Can See more Here: https://www.baeldung.com/jpa-entity-lifecycle-events