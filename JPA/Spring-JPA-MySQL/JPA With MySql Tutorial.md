# JPA Tutorial

- Connect Mysql
- [**Relationship**](src/main/java/com/alan/entity_mapping/mapping/Relationship.md)
- [**Entity Listener**](src/main/java/com/alan/entity_listener/EntityListener.md)

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


