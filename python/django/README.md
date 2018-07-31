### Libray used:
- restapi - [django-rest-framework](https://github.com/encode/django-rest-framework)
- logging - django
- database - django
- uuid - 
- JWT - [pyjwt](https://github.com/jpadilla/pyjwt)
- encryption - pbkdf2_sha256

- unit test - django
- unit test - django


## Install
>**Note:**  

>For windows, the `sudo` command is installed via [scoop](https://scoop.sh/)

> To let VSCode recognize the pipenv context, see [here](https://blog.kazge.com/python/2018/07/03/the-python-dependency-tool/)

> Do not use migrate, instead, install the database from the sql  

### Install python3

## Install mysql connector
Download [mysql connctor](https://dev.mysql.com/downloads/connector/c/)  

See [The Python Dependency Tool](https://blog.kazge.com/python/2018/07/03/the-python-dependency-tool/)

## Run development
go to app folder, run command  
`python manage.py runserver 8080`  
or using wsgi-express(if installed):  
`python manage.py runmodwsgi --port=8080 --reload-on-changes`


## Run production
refer to https://docs.djangoproject.com/en/2.0/howto/deployment/wsgi/modwsgi/  
refer to https://github.com/GrahamDumpleton/mod_wsgi#using-mod_wsgi-express-with-django  

### Via Docker:
go to the project folder
`docker build -t djangoapp .`  
`docker run -it --rm -p 8080:80 -v <your project path>:/app --name djangoapp-run djangoapp`

### Via Windows(tricky):
refer to [Running mod_wsgi on Windows](https://github.com/GrahamDumpleton/mod_wsgi/blob/develop/win32/README.rst)  
`scoop install php apache`  [install apache via scoop](https://github.com/lukesampson/scoop/wiki/Apache-with-PHP)    
`scoop which httpd` #show the httpd folder  
`bash`  
`pipenv shell`   
`MOD_WSGI_APACHE_ROOTDIR=~/scoop/apps/apache/current pipenv install mod_wsgi`  
`pipenv shell`  
`MOD_WSGI_APACHE_ROOTDIR=C:/Apache24 pipenv install mod_wsgi`  
`python manage.py collectstatic`  
`python manage.py runmodwsgi`  

## Install [pipenv](https://github.com/pypa/pipenv)  
`sudo pip install pipenv`  

## Setup project
- initialize dependencies `pipenv install`   
- load venv `pipenv shell`   
- ~~django migrate `python manage.py makemigrations` `python manage.py migrate`~~
- start dev server `python manage.py runserver 8080`

## Test
 `python manage.py test common` or `python -m unittest common`
 it will create a temp database based on the database setting for test the app common

 `python manage.py test common.utils_tests` test one file only

