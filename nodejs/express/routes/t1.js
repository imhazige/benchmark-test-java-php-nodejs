const express = require('express');
const uuid = require('uuid');
const log = require('../common/log');
const db = require('../common/db');

const router = express.Router();

/* GET users listing. */
router.get('/users', function (req, res, next) {
    var limit = req.query.limit;
    limit = parseInt(limit) || 100;
    // log.debug('%s --- ',JSON.stringify(req.query));
    db.query('select * from t_users limit ?', [limit], function (error, results, fields) {
        
        if (error) { res.send('ERROR:' + JSON.stringify(error)); return; }

        res.json(results);
        // res.json({
        //     results: results,
        //     fields: fields
        // });
    });

});

/* add user. */
router.post('/users', function (req, res, next) {
    log.debug('user ....');
    var user = req.json();
    user.id = uuid.v4();
    //TODO encrypt password
    // https://github.com/dcodeIO/bcrypt.js
    // or 

    // var ecodedpwd = ;
    var now = new Date();
    db.query('insert into t_users values(?,?,?,?)', [user.id,user.name,ecodedpwd,now.getTime()], function (error, results, fields) {
        
        if (error) { res.send('ERROR:' + JSON.stringify(error)); return; }

        res.json(results);
        // res.json({
        //     results: results,
        //     fields: fields
        // });
    });

});

module.exports = router;