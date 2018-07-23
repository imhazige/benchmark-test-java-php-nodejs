import { Meteor } from 'meteor/meteor';

//load t1 api
import api from '../imports/server/api.js';

Meteor.startup(() => {
  // code to run on server at startup
  api.setupApi();
});
