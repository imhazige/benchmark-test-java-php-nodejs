const uuid = require('uuid');
const log = require('../common/log');
const db = require('../common/db');
const crypto = require('crypto');

/* users listing. */
const listing = (ops, callback) => {
    // log.debug('%s --- ',JSON.stringify(req.query));
    db.query('select * from t_users limit ?', [ops.limit], callback);
};


/* add user. */
const add = (ops, callback) => {
    const user = ops.user;
    db.query('select count(id) as c from t_users where name = ?', user.name, (error, results, fields) => {
        log.debug(JSON.stringify( results));
        if (results[0].c>0){
            callback(new Error('name already exists.'));
        }
    });
    //check user name unique
    user.id = uuid.v4();
    //hash and salt password
    const pwdlen = 16;
    var salt = crypto.randomBytes(pwdlen);
    salt = salt.toString('hex');
    var iterations = 10000;
    var hash = crypto.pbkdf2Sync(user.password, salt, iterations, pwdlen, 'sha512').toString('hex');

    var ecodedpwd = null;
    var now = new Date();
    db.query('insert into t_users values(?,?,?,?,FROM_UNIXTIME(?),FROM_UNIXTIME(?))', [user.id, user.name, hash, salt.toString('hex'), now.getUnixTime(), null], callback);
};


/* get user by id. */
const get = (ops, callback) => {
    db.query('select * from t_users where id = ?', ops.userId, callback);
};

module.exports = {
    listing: listing,
    add: add,
    get: get
};