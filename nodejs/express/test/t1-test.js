const log = require('../common/log');
const t = require('./base-test');
const uuid = require('uuid');

const prefix = '/t1';

var data = {
    name: uuid.v1(),
    password: '123456'
};

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

t.test_api('test update user', function (req, done) {
    //update a user
    req.put(prefix + '/users/' + data.id)
        .send(data)
        .expect(res => {
            log.debug('response>>' + res.text)
        })
        .expect(200, done);
});

t.test_api('test delete user', function (req, done) {
    //add a user
    req.delete(prefix + '/users/' + data.id)
        .expect(res => {
            log.debug('response>>' + res.text)
        })
        .expect(200, done);
});