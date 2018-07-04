### Libray used:
- restapi - [django-rest-framework](https://github.com/encode/django-rest-framework)
- logging - django
- database - django
- uuid - 
- JWT - 
- encryption - 

- unit test - 
- unit test - 


## Install
>**Note:**  

>For windows, the `sudo` command is installed via [scoop](https://scoop.sh/)

> To let VSCode recognize the pipenv context, see [here](https://blog.kazge.com/python/2018/07/03/the-python-dependency-tool/)

> Do not use migrate, instead, install the database from the sql  

### Install python3

## Install mysql connector
Download [mysql connctor](https://dev.mysql.com/downloads/connector/c/)  

See [The Python Dependency Tool](https://blog.kazge.com/python/2018/07/03/the-python-dependency-tool/)

## Run
go to app folder, run command
`python manage.py runserver`


## Install [pipenv](https://github.com/pypa/pipenv)
`sudo pip install pipenv`

## Setup project
- initialize dependencies `pipenv install`  
- load venv `pipenv shell`   
- ~~django migrate `python manage.py makemigrations` `python manage.py migrate`~~
- start dev server `python manage.py runserver`

## Test
 `python manage.py test common`

