const log = require('./log');
const mysql = require('mysql');

var pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'test_db',
    connectionLimit: 10
});

function query(sql, args, callback) {
    pool.getConnection(function (err, connection) {
        if (err) {
            callback(error, null, null);

            return;
        }
        var q = connection.query(sql, args, function (error, results, fields) {
            connection.release();
            callback(error, results, fields);
        });
        log.debug(`SQL:${q.sql}`);
    });
}

module.exports = {
    query: query
};