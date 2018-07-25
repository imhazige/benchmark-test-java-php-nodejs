REST API is not the recommended using style of meteor which power by [DDP](https://github.com/meteor/meteor/blob/master/packages/ddp/DDP.md). 

Here I use official suggested library [nimble:restivus](https://atmospherejs.com/nimble/restivus)

Refer to <https://guide.meteor.com/routing.html#server-side>

### Libray used:
- logging - 
- database - 
- uuid - 
- JWT - 
- encryption - nodejs native

- unit test - 
- unit test - 



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




