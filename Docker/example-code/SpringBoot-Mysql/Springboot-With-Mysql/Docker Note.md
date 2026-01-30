# Note

The first: Check Mysql username root can access from all host
```sql
select host, user from mysql.user;
```
- if username = root
- and host = %
- it's ok

#
- if not setup it can use this statement to configure it:
  - 1: create user root
  - 2: grant all privileges (set all roles)
  - 3: flush privileges (reload all privileges)
```sql
create user 'root'@'%' identified by '123456';
grant all privileges on *.* to 'root'@'%';
flush privileges;
```

#
- open cmd use ipconfig to check your ip address:
- Take IPv4 Address
```bash
ipconfig
````

#
- Config application.properties
```properties
# MySQL Connection
#Exmaple: spring.datasource.url=jdbc:mysql://192.168.1.1:3336/db_test?useSSL=false&serverTimezone=UTC
spring.datasource.url=jdbc:mysql://<your-address>:<port>/<db_name>?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456
```

#
Maven clean and package to get jar file
<br/>
And docker build
```bash
docker build --tag=springtboot_with_mysql:0.0.1 .
```

#
docker run:
```bash
docker run -p 9999:8081 springtboot_with_mysql:0.0.1
```

#
Api test:

- Dummy Data
```bash
http://localhost:9999/student/dummyData
```

- Get All Students
```bash
http://localhost:9999/student/all
```

- Get student by id
```bash
http://localhost:9999/student/1
```
