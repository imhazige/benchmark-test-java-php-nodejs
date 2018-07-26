export PORT=8080
export MONGO_URL=mongodb://localhost:27017/meteor-test
export ROOT_URL=http://localhost:8080
METEOR="/C/Users/Administrator/AppData/Local/.meteor/meteor.bat"
echo "npm install..."
npm install --production
echo "clean old build..."
rm -rf ./build
echo "build..."
$METEOR build ./build
echo "extract build..."
cd ./build
# extract the package
tar xzvf meteor.tar.gz
cd ./bundle
echo "install subfolder..."
(cd programs/server && npm install)
pwd
node main.js

