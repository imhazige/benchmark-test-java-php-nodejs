# Target

This project is going to do the following function to provide a test code to see the performance of Java, PHP, Nodejs.

The function will include simple but common thing that a RESTful API will do:

- Convert request to a language data type and query a database(insert,get). -- IO and database.
- Use a library to create a token and  validate the token. -- CPU-intensive.

Each solution will use the popular library, framework adopted in the specified language, in this way, it will be more closer to the real situation.


## API
### Test 1 without autentication
#### POST /t1/users -- create a User
#### GET /t1/users -- get list of users
#### GET /t1/users/<id> -- get a user

### Test 2 with token autentication
#### POST /t2/login -- create token
#### POST /t2/users -- create a User
#### GET /t2/users -- get list of users
#### GET /t2/users/<id> -- get a user

## Database

Database is using Mysql.

### Schema

## Java
 - [springboot](http://projects.spring.io/spring-boot/)
 - [play-scala](https://playframework.com/) 
 - [kotlin](https://kotlinlang.org/)

## PHP
- [laravel](https://laravel.com/)
- [symphony](https://symphony.com/)

## Node.js
- [express](http://expressjs.com/)
- [koa](http://koajs.com/)








