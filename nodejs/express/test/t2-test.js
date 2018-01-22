const log = require('../common/log');
const t = require('./base-test');
const uuid = require('uuid');

const prefix = '/t2';

var data = {
    name: uuid.v1(),
    password: '123456'
};

var tk = null;

t.test_api('use t1 api add user', function (req, done) {
    req.post('/t1/users')
        .send(data)
        .expect(res => {
            log.debug('response>>' + res.text);
            data.id = res.body.id
        })
        .expect(200, done);
});

t.test_api('test login', function (req, done) {
    req.post(prefix + '/login')
        .send(data)
        .expect(res => {
            log.debug('response>>' + res.text);
            tk = res.text;
        })
        .expect(200, done);
});

t.test_api('test add user', function (req, done) {
    req.post(prefix + '/users')
        .send(data)
        .expect(res => {
            log.debug('response>>' + res.text);
            data.id = res.body.id
        })
        .expect(200, done);
});

t.test_api('test get users', function (req, done) {
    req.get(prefix + '/users')
        .expect(res => {
            log.debug('response>>' + res.text)
        })
        .expect(200, done);
});


t.test_api('test get user by id', function (req, done) {
    //add a user
    req.get(prefix + '/users/' + data.id)
        .expect(res => {
            log.debug('response>>' + res.text)
        })
        .expect(200, done);
});