const express = require('express')
const app = express()

var t1 = require('./routes/t1');

app.use('/t1', t1);

app.get('/', (req, res) => res.send('Hello World!'))

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});








//error
// var bodyParser = require('body-parser')
// var methodOverride = require('method-override')
// 
// app.use(bodyParser.urlencoded({
//   extended: true
// }))
// app.use(bodyParser.json())
// app.use(methodOverride())
// app.use(logErrors)
// app.use(clientErrorHandler)
// app.use(errorHandler)


function logErrors (err, req, res, next) {
  console.error(err.stack)
  next(err)
}





var server = app.listen(9090, () => console.log('Example app listening on port 9090!'))

//for supertest 
module.exports = server;




