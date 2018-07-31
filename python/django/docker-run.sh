PATH=D:/work/git/benchmark-test-java-php-nodejs/python/django
# change to the correct path of docker
DOCKER="C:/Program Files/Docker/Docker/Resources/bin/docker.exe"
# change to the correct path of pipenv
PIPENV="C:/Program Files (x86)/Microsoft Visual Studio/Shared/Python36_64/Scripts/pipenv.exe"
REQURIEFILES=app/requirements.txt

# create the requirements.txt
rm -rf $REQURIEFILES
# "$PIPENV" shell
"$PIPENV" lock -r > $REQURIEFILES
# start docker
"$DOCKER" build -t djangoapp .
"$DOCKER" run -it --rm -p 8080:80 -v $PATH/logs:/app/logs --name djangoapp-run djangoapp