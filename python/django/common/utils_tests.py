import unittest
import uuid
from datetime import datetime
from django.utils import timezone
from . import django_utils
from . import log
from .models import TUSers

from .serializer import TUSersSerializer


class DjangoUtilsTests(unittest.TestCase):
    # def test_json(self):
    #     u = TUSers()
    #     jstr = django_utils.obj2json(u)
    #     djs = django_utils.json2obj(jstr)
    #     log.debug('{} -- {}', jstr, djs)

    def test_serialize(self):
        id = str(uuid.uuid1())
        now = datetime.now(tz=timezone.utc)
        # :fit the test case
        data = {'id': id, 'name': id, 'password': '123456',
                'create_time': now, 'update_time': now}
        serializer = TUSersSerializer(data=data)
        valid = serializer.is_valid(raise_exception=True)
        serializer.save()
