const log = require('./log');
const mysql = require('mysql');

function query(sql, args, callback) {
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: 'root',
        database: 'test_db'
    });

    connection.connect();

    var q = connection.query(sql, args, function (error, results, fields) {
        callback(error,results,fields);        
    });
    log.debug(`SQL:${q.sql}`);

    connection.end();
}

module.exports = {
    query:query
};