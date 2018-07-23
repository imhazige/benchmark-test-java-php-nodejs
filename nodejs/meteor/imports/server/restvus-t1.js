import service from './user-service';
import log from './common/log';

/**
 * I did not found express is able to use with meteor directly, restvus is working, but not good to test
 */

const Api = new Restivus({
  apiPath: 'restvus/t1/users'
});

Api.addRoute(
  ':id',
  { authRequired: false },
  {
    get: function() {
      return service.get(this.urlParams.id);
    },
    delete: {
      roleRequired: ['author', 'admin'],
      action: function() {
        if (Articles.remove(this.urlParams.id)) {
          return { status: 'success', data: { message: 'Article removed' } };
        }
        return {
          statusCode: 404,
          body: { status: 'fail', message: 'Article not found' }
        };
      }
    }
  }
);

Api.addRoute(
  '',
  { authRequired: false },
  {
    get: function() {
      var limit = this.queryParams.limit;
      limit = parseInt(limit) || 100;
      try {
        var results = Meteor.wrapAsync(callback => {
          service.listing({ limit }, (error, results, fields) => {
            if (error) {
              callback(error);
              return;
            }

            callback(null, results);
          });
        })();
        return results;
      } catch (err) {
        throw err;
      }
    },
    delete: {
      roleRequired: ['author', 'admin'],
      action: function() {
        if (Articles.remove(this.urlParams.id)) {
          return { status: 'success', data: { message: 'Article removed' } };
        }
        return {
          statusCode: 404,
          body: { status: 'fail', message: 'Article not found' }
        };
      }
    }
  }
);
