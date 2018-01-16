const t = require('./base_test');

const prefix = '/t1';

t.test_api('test get users', function (req, done) {
    
    req.get(prefix + '/users').expect(200, done);
});


t.test_api('test add user', function (req, done) {
    var data = {
        name:'Good Name',
        password:'123456'    
    };

    req.post(prefix + '/users')
    .send(data)
    .expect(200, done);
});