const express = require('express');
const uuid = require('uuid');
const log = require('../common/log');
const db = require('../common/db');
const crypto = require('crypto');

//date to unix timestamp for mysql
require('../common/date_extend');

const router = express.Router();

/* GET users listing. */
router.get('/users', function (req, res, next) {
    var limit = req.query.limit;
    limit = parseInt(limit) || 100;
    // log.debug('%s --- ',JSON.stringify(req.query));
    db.query('select * from t_users limit ?', [limit], function (error, results, fields) {
        
        if (error) { throw error; }

        res.json(results);
    });

});

/* add user. */
router.post('/users', function (req, res, next) {
    log.debug('user ....');
    var user = req.body;
    user.id = uuid.v4();
    //hash and salt password
    const pwdlen = 16;
    var salt = crypto.randomBytes(pwdlen);
    salt = salt.toString('hex');
    var iterations = 10000;
    hash = crypto.pbkdf2Sync(user.password, salt, iterations, pwdlen, 'sha512').toString('hex');

    var ecodedpwd = null;
    var now = new Date();
    db.query('insert into t_users values(?,?,?,?,FROM_UNIXTIME(?),FROM_UNIXTIME(?))', [user.id,user.name,hash,salt.toString('hex'),now.getUnixTime(),null], function (error, results, fields) {
        if (error) { throw error; }

        res.json({
            id : user.id
        });
    });

});

module.exports = router;