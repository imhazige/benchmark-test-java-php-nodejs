import assert from 'assert';

/**
 * it is not good to test rest api at here, this code did not working for rest api
 */

//init server app
require('../../server/main.js');

describe('my', function() {
  it('package.json has correct name', async function() {
    const { name } = await import('../../package.json');
    assert.strictEqual(name, 'my');
  });

  if (Meteor.isClient) {
    it('client is not server', function() {
      assert.strictEqual(Meteor.isServer, false);
    });
  }

  if (Meteor.isServer) {
    it('server is not client', async function() {
      assert.strictEqual(Meteor.isClient, false);
      await import('./t1-tests.js');
    });
  }
});
