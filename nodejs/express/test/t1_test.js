const t = require('./base_test');

const prefix = '/t1';

t.test_api('get all users',function(req,done){
    req.get(prefix + '/users').expect(200,done);
});


t.test_api('add a user',function(req,done){
    var user = {
        name:'some user',
        password:'some pwssword'
    };
    req.post(prefix + '/users',user).expect(200,done);
});