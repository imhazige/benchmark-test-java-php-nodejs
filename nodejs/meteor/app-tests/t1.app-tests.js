import request from 'supertest';

import { Meteor } from 'meteor/meteor';
import { Tracker } from 'meteor/tracker';
import { DDP } from 'meteor/ddp-client';
import { Promise } from 'meteor/promise';

const urlPrefix = 'http://localhost:3000';

const denodeify = f => (...args) =>
  new Promise((resolve, reject) => {
    f(...args, (err, val) => {
      if (err) {
        reject(err);
      } else {
        resolve(val);
      }
    });
  });

// Utility -- returns a promise which resolves when all subscriptions are done
const waitForSubscriptions = () =>
  new Promise(resolve => {
    const poll = Meteor.setInterval(() => {
      if (DDP._allSubscriptionsReady()) {
        Meteor.clearInterval(poll);
        resolve();
      }
    }, 200);
  });

// Tracker.afterFlush runs code when all consequent of a tracker based change
//   (such as a route change) have occured. This makes it a promise.
const afterFlushPromise = denodeify(Tracker.afterFlush);

if (Meteor.isClient) {
  describe('T1 API', function() {
    it('get all users', function(done) {
      afterFlushPromise()
        .then(waitForSubscriptions)
        .then(() => {
          const result = Meteor.wrapAsync(callback => {
            console.log(' -----', a);
            request(
              {
                method: 'GET',
                url: urlPrefix + '/t1/users'
              },
              function(err, res) {
                console.log('callback url response: ', res);
                callback(err, res);
              }
            );
          })();
          console.log('result is: ', result);
          done();
        });
    });
  });
}
