var request = require('supertest');

function test_api(name, testfn) {
  describe('loading express', function() {
    var server;
    beforeEach(function() {
      server = require('./app.js');
    });
    afterEach(function() {
      server.close();
    });
    it(name, function testPath(done) {
      var req = request(server);
      testfn(req, done);
    });
  });
}

module.exports = {
  test_api: test_api
};
