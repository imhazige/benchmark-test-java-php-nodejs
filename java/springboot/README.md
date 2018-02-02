### Libray used:
- logging - [springboot default - logback](https://logback.qos.ch/)
- database - [mysql](https://mvnrepository.com/artifact/mysql/mysql-connector-java)
- uuid - Java-native
- JWT - [jsonwebtoken](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt)
- unit test - [spring test](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)
- unit test - [junit](http://junit.org/junit4/)
- encryption - Java native



### Run
- development 
``` shell
mvn package
java -jar springboot-example-0.1.0.jar
```

- unit test
  make sure database installed.
``` shell
 mvn test
```