import request from 'supertest';

const urlPrefix = 'http://localhost:3000';

describe('T1 API', function() {
  it('get all users', function(done) {
    request(
      {
        method: 'GET',
        url: urlPrefix + '/t1/users'
      },
      function(err, res) {
        console.log('callback url response: ', res);
        done();
      }
    );
  });
});
