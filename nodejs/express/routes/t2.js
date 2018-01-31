const express = require('express');
const us = require('./user-service');
const log = require('../common/log');
const token = require('../common/token');


//date to unix timestamp for mysql
require('../common/date-extend');

const router = express.Router();

const authFilter = (req, res, next) => {
    var tk = req.get('Authorization');

    if (!tk) {
        res.status(401).send();

        return;
    }
    var tkdata = token.verifyToken(tk);

    if (!tkdata) {
        res.status(401).send();

        return;
    }

    req.tkdata = tkdata;

    next();
};


/* users listing. */
router.get('/users', authFilter, function (req, res, next) {
    log.debug('auth data is ', req.tkdata);
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
        if (!results) {
            res.end();

            return;
        }


        var u = results[0];

        var hash = us.md5Password({
            password: pwd,
            salt: u.salt
        });

        var tokData = {
            userId: u.id
        };

        log.debug('tk data is ',tokData);

        if (hash == u.password) {
            //create token
            var tk = token.createToken(tokData, 60 * 60 * 24);
            res.status(200).send(tk);
        } else {
            res.status(401);
        }
    });

});

/* add user. */
router.post('/users', authFilter, function (req, res, next) {
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
router.get('/users/:userId', authFilter, function (req, res, next) {
    us.get({
        userId: req.params.userId
    }, function (error, results, fields) {
        if (error) {
            throw error;
        }

        res.json(results);
    });

});

/* update user. */
router.put('/users/:userId', function (req, res, next) {
    log.debug('user ....');
    var user = req.body;
    user.id = req.params.userId;

    us.update({
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

/* delete user by id. */
router.delete('/users/:userId', function (req, res, next) {
    us.remove({
        userId: req.params.userId
    }, function (error, results, fields) {
        if (error) {
            throw error;
        }

        res.json({
            id: req.params.userId
        });
    });

});


module.exports = router;