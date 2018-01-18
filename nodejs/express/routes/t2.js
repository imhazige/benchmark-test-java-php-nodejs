const express = require('express');
const us = require('./user-service');
const log = require('../common/log');


//date to unix timestamp for mysql
require('../common/date-extend');

const router = express.Router();

/* users listing. */
router.get('/users', function (req, res, next) {
    var limit = req.query.limit;
    limit = parseInt(limit) || 100;
    // log.debug('%s --- ',JSON.stringify(req.query));
    us.listing({
        limit: limit
    }, function (error, results, fields) {

        if (error) {
            throw error;
        }

        res.json(results);
    });

});

/* login. */
router.post('/login', function (req, res, next) {
    var name = req.body.name;
    var pwd = req.body.password;

    us.getByName({
        name: name
    }, (error, results, fields) => {
        if (!results){
            res.end();

            return;
        }

        
        var u = results[0];

        var hash = us.md5Password({
            password:pwd,
            salt:u.salt
        });
        
        if (hash == u.password){
            //create token

            res.status(200).send(token);
        }else{
            res.status(401);
        }
    });

});

/* add user. */
router.post('/users', function (req, res, next) {
    log.debug('user ....');
    var user = req.body;

    us.add({
        user: user
    }, function (error, results, fields) {
        if (error) {
            throw error;
        }

        res.json({
            id: user.id
        });
    });

});


/* get user by id. */
router.get('/users/:userId', function (req, res, next) {
    us.get({
        userId: req.params.userId
    }, function (error, results, fields) {
        if (error) {
            throw error;
        }

        res.json(results);
    });

});


module.exports = router;