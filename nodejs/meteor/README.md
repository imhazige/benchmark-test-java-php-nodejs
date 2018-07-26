REST API is not the recommended using style of meteor which power by [DDP](https://github.com/meteor/meteor/blob/master/packages/ddp/DDP.md). 

Here I use official suggested library [nimble:restivus](https://atmospherejs.com/nimble/restivus)

Refer to <https://guide.meteor.com/routing.html#server-side>

### Libray used:
- logging - [log4js](https://github.com/log4js-node/log4js-node)
- database - [mysql](https://github.com/mysqljs/mysql)
- uuid - [uuid](https://github.com/broofa/node-uuid)
- JWT - [jsonwebtoken](https://github.com/auth0/node-jsonwebtoken)
- encryption - nodejs native

- unit test - [mocha](https://mochajs.org/)
- unit test - [supertest](https://github.com/visionmedia/supertest)

### run development
install meteor
run command
`meteor npm start`

### build on local machine
`npm install --production`
`meteor build ./build`

### run build
first make sure you have mongodb installed, created a database like meteor_test
extract the built package to a folder
`cd <path-to-build-extracted-folder>/bundle/program/server`
`npm install`
`cd <path-to-build-extracted-folder>/bundle` -- goback to the bundle root folder
`bash`
`PORT=8080 MONGO_URL=mongodb://localhost:27017/meteor-test ROOT_URL=http://localhost:8080 node main.js`

(cd programs/server && npm install)
MONGO_URL=mongodb://localhost:27017/myapp ROOT_URL=http://my-app.com node main.js



### Run
- development 
``` shell
meteor npm start
```

- unit test
```
npm run test
```

- integration test
```
npm run test-app
```

## problem
when run on windows
`meteor npm run test` or `meteor npm run test-app` did not work. error show ''mocha' is not recognized as an internal or external command'

I can not make the --full-app test working, the api is not present when running with `npm run test-app`. so I have to use a normal npm way to run mocha test. the express api is not running in the meteor in this way.




