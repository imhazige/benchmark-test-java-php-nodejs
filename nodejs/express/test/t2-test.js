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

t.test_api('test2 login', function (req, done) {
    req.post(prefix + '/login')
        .send(data)
        .expect(res => {
            log.debug('response>>' + res.text);
            tk = res.text;
        })
        .expect(200, done);
});

t.test_api('test2 add user', function (req, done) {
    data.name = uuid.v1();
    req.post(prefix + '/users')
        .set('Authorization', tk)
        .send(data)
        .expect(res => {
            log.debug('response>>' + res.text);
            data.id = res.body.id
        })
        .expect(200, done);
});

t.test_api('test2 get users', function (req, done) {
    req.get(prefix + '/users')
        .set('Authorization', tk)
        .expect(res => {
            log.debug('response>>' + res.text)
        })
        .expect(200, done);
});


t.test_api('test2 get user by id', function (req, done) {
    //add a user
    req.get(prefix + '/users/' + data.id)
        .set('Authorization', tk)
        .expect(res => {
            log.debug('response>>' + res.text)
        })
        .expect(200, done);
});

t.test_api('test2 update user', function (req, done) {
    //update a user
    req.put(prefix + '/users/' + data.id)
        .send(data)
        .expect(res => {
            log.debug('response>>' + res.text)
        })
        .expect(200, done);
});

t.test_api('test2 delete user', function (req, done) {
    //add a user
    req.delete(prefix + '/users/' + data.id)
        .expect(res => {
            log.debug('response>>' + res.text)
        })
        .expect(200, done);
});