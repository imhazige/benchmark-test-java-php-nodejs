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