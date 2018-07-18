// sign with default (HMAC SHA256)
var jwt = require('jsonwebtoken');
const key = 'w9of98u 0-32o c2 $DSDFSD=ddsfl';

const createToken = (data, seconds) => {
    var token = jwt.sign({
        data: data
    }, key, {
        expiresIn: seconds
    });

    return token;
};

const verifyToken = (tk) => {
    try {
        return jwt.verify(tk, key);
    } catch (ex) {
        return null;
    }
};

module.exports = {
    createToken: createToken,
    verifyToken: verifyToken
};