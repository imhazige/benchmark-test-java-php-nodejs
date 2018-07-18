const log4js = require('log4js');
log4js.configure({
    appenders: { cheese: { type: 'file', filename: 'express-benmark.log' }, 'out': { type: 'stdout' } },
    categories: { default: { appenders: ['cheese','out'], level: 'error' } }
});

//use default
const log = log4js.getLogger();
// const log = log4js.getLogger('cheese');

module.exports = log;