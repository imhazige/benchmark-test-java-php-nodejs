PATH=D:/work/git/benchmark-test-java-php-nodejs/python/django
DOCKER="C:/Program Files/Docker/Docker/Resources/bin/docker.exe"
PIPENV="C:/Program Files (x86)/Microsoft Visual Studio/Shared/Python36_64/Scripts/pipenv.exe"
REQURIEFILES=app/requirements.txt

rm -rf $REQURIEFILES
# "$PIPENV" shell
"$PIPENV" lock -r > $REQURIEFILES
"$DOCKER" build -t djangoapp .
"$DOCKER" run -it --rm -p 8080:80 -v $PATH/logs:/app/logs --name djangoapp-run djangoapp