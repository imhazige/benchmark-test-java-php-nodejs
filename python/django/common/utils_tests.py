import unittest
from . import django_utils
from . import log
from .models import TUSers


class DjangoUtilsTests(unittest.TestCase):
    def test_json(self):
        u = TUSers()
        jstr = django_utils.obj2json(u)
        djs = django_utils.json2obj(jstr)
        log.debug('{} -- {}', jstr, djs)
