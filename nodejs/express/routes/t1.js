var express = require('express');
var router = express.Router();
var log = require.main.require('./log');;

/* GET users listing. */
router.get('/users', function (req, res, next) {
    log.debug('mkkkkk') ; 
    res.send('sssss');
})

module.exports = router;