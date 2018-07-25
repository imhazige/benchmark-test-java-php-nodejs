const express = require('express');
const app = express();

const bodyParser = require('body-parser');
app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded

const log = require('../imports/server/common/log');

var t1 = require('../imports/server/t1');
var t2 = require('../imports/server/t2');

app.use('/t1', t1);
app.use('/t2', t2);

app.get('/', (req, res) => res.send('Hello World!'));

const port = 8080;
var server = app.listen(port, () => {
  log.info('Example app listening on port %s!', port);
});

//for supertest
module.exports = server;
