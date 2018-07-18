const uuid = require('uuid');
const log = require('./common/log');
const db = require('./common/db');
const crypto = require('crypto');

const pwdlen = 16;

/* users listing. */
const listing = (ops, callback) => {
  // log.debug('%s --- ',JSON.stringify(req.query));
  db.query('select * from t_users limit ?', [ops.limit], callback);
};

const md5Password = ops => {
  var iterations = 10000;
  log.debug('ops', ops);
  var hash = crypto
    .pbkdf2Sync(ops.password, ops.salt, iterations, pwdlen, 'sha512')
    .toString('hex');

  return hash;
};

const hashPassword = password => {
  var salt = crypto.randomBytes(pwdlen);
  salt = salt.toString('hex');
  var ecodedpwd = md5Password({
    password: password,
    salt: salt
  });

  return {
    ecodedpwd: ecodedpwd,
    salt: salt
  };
};

/* add user. */
const add = (ops, callback) => {
  const user = ops.user;
  db.query(
    'select count(id) as c from t_users where name = ?',
    [user.name],
    (error, results, fields) => {
      log.debug(JSON.stringify(results));
      if (results[0].c > 0) {
        callback(new Error('name already exists.'));

        return;
      }

      //check user name unique
      user.id = uuid.v4();
      //hash and salt password

      var enpwd = hashPassword(user.password);

      var now = new Date();
      log.debug('got to here');
      db.query(
        'insert into t_users values(?,?,?,?,FROM_UNIXTIME(?),FROM_UNIXTIME(?))',
        [
          user.id,
          user.name,
          enpwd.ecodedpwd,
          enpwd.salt,
          now.getUnixTime(),
          null
        ],
        callback
      );
    }
  );
};

/* get user by id. */
const get = (ops, callback) => {
  db.query('select * from t_users where id = ?', [ops.userId], callback);
};

const getByName = (ops, callback) => {
  db.query('select * from t_users where name = ?', [ops.name], callback);
};

const update = (ops, callback) => {
  let user = ops.user;
  db.query(
    'select count(id) as c from t_users where name = ? and id != ?',
    [user.name, user.id],
    (error, results, fields) => {
      log.debug(JSON.stringify(results));
      if (results[0].c > 0) {
        callback(new Error('name already exists.'));
        return;
      }

      var now = new Date();
      db.query(
        'update t_users set name = ?, password = ?, salt = ?, update_time = ?  where id = ?',
        [user.name, user.password, user.salt, now.getUnixTime(), user.userId],
        callback
      );
    }
  );
};

const remove = (ops, callback) => {
  db.query('delete from t_users where id = ?', [ops.userId], callback);
};

module.exports = {
  listing: listing,
  add: add,
  get: get,
  update: update,
  remove: remove,
  getByName: getByName,
  md5Password: md5Password
};
