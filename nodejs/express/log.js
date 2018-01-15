const log4js = require('log4js');
log4js.configure({
  appenders: { cheese: { type: 'file', filename: 'express-benmark.log' } },
  categories: { default: { appenders: ['cheese'], level: 'debug' } }
});

const log = log4js.getLogger('cheese');

module.exports = log;