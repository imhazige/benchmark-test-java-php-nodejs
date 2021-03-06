from django.test import TestCase

# Create your tests here.
# See https://docs.djangoproject.com/en/2.0/intro/tutorial05/

# See http://www.django-rest-framework.org/api-guide/testing/
from django.urls import reverse
from rest_framework import status
from rest_framework.test import APITestCase
from .models import TUSers
import uuid
from . import log
from datetime import datetime
from django.utils import timezone


class T2Tests(APITestCase):
    base_name = 't2'

    def __init__(self, *args, **kvargs):
        super(T2Tests, self).__init__(*args, **kvargs)
        self.loginUser = None
        self.token = None

    def setone(self):
        id = str(uuid.uuid1())
        now = datetime.now(tz=timezone.utc)
        # :fit the test case
        return {'id': id, 'name': id, 'password': '123456', 'create_time': now, 'update_time': now}

    def setUp(self):
        super().setUp()
        # this is not required, it will create a temp database when testing
        TUSers.objects.all().delete()

        # set up test user via t1 api
        self.loginUser = self.create_one()
        url = reverse('t2_token-list')

        response = self.client.post(
            url, {'name': self.loginUser.name, 'password': self.loginUser.password}, format='json')
        self.token = response.data
        print('>>>>>>>>> token >>>>', url, self.token)
        # see https://www.django-rest-framework.org/api-guide/testing/#authenticating
        self.client.credentials(HTTP_AUTHORIZATION=self.token)

    def create_one(self, **kwargs):
        id = kwargs.get('id')
        if id is None:
            id = str(uuid.uuid1())
            kwargs['id'] = id
        if kwargs.get('name') is None:
            kwargs['name'] = id

        now = datetime.now(tz=timezone.utc)
        kwargs['create_time'] = now
        kwargs['update_time'] = now

        u = TUSers.objects.create(**kwargs)
        return u

    def test_list(self):
        """
        list
        """
        # get the url
        url = reverse(T2Tests.base_name + '-list')
        data = {}
        response = self.client.get(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        # self.assertEqual(TUsers.objects.get().name, 'DabApps')

    def test_retrieve(self):
        """
        retrieve
        """
        # get the url
        u = self.create_one()
        log.debug('created {}', u)
        url = reverse(T2Tests.base_name + '-detail', args=(u.id,))
        log.debug('url is {}', url)
        data = {}
        response = self.client.get(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        # self.assertEqual(TUsers.objects.get().name, 'DabApps')

    def test_createAndUpdate(self):
        """
        retrieve
        """
        # get the url
        u = self.create_one()
        log.debug('created {}', u)
        url = reverse(T2Tests.base_name + '-list')
        log.debug('url is {}', url)
        data = self.setone()
        response = self.client.post(url, data, format='json')
        # :get response body
        log.debug('response:{}', response.data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
