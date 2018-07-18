import service from './user-service';
import { Restivus } from 'nimble:restivus';

const Api = new Restivus({
  apiPath: 't1/users'
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
      return service.listing();
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
