from django.core import serializers
import json


def obj2json(obj):
    '''
    this method only able to encode the django model to json
    format like this 
    {
        "model": "common.tusers", "pk": "63e0ed3a-7ffd-11e8-8ccf-3ca9f45339fc", 
        "fields": {"name": "63e0ed3a-7ffd-11e8-8ccf-3ca9f45339fc", "salt": "", "password": "", "create_time": "2018-07-05T02:44:51.393Z", "update_time": "2018-07-05T02:44:51.393Z"}
    }
    '''
    mystr = serializers.serialize('json', [obj])
    if len(mystr) < 2:
        return ''
    return mystr[1:-1]


def json2obj(str):
    return json.loads(str)
