const express = require('express');
const us = require('./user-service');
const log = require('../common/log');
const token = require('../common/token');
const passport = require('passport')
const Strategy = require('passport-http-bearer').Strategy;



//date to unix timestamp for mysql
require('../common/date-extend');

const router = express.Router();

const needAuthChain = (req, res, next) => {
    const rt = passport.authenticate('bearer', {
        session: false
    });
    rt(req,res,next);
};


/* users listing. */
router.get('/users', needAuthChain,
    function (req, res, next) {
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

        if (hash == u.password) {
            //create token
            var tk = token.createToken({
                userId: u.userId
            }, 60 * 60 * 24);
            tk = `Bearer ${tk}`;
            res.status(200).send(tk);
        } else {
            res.status(401);
        }
    });

});

/* add user. */
router.post('/users', needAuthChain, function (req, res, next) {
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
router.get('/users/:userId', needAuthChain, function (req, res, next) {
    us.get({
        userId: req.params.userId
    }, function (error, results, fields) {
        if (error) {
            throw error;
        }

        res.json(results);
    });

});

const initialize = (app) => {
    app.use(passport.initialize());

    passport.use(new Strategy(
        function (tk, done) {

            var tkdata = token.verifyToken(tk);

            if (!tkdata) {
                return done(null, false);
            }

            return done(null, tkdata);
        }
    ));

    return router;
};

module.exports = {
    initialize: initialize
};