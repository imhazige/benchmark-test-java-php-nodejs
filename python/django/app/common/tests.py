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


class T1Tests(APITestCase):
    base_name = 't1'

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

    def set_up(self):
        # this is not required, it will create a temp database when testing
        TUSers.objects.all().delete()

    def test_list(self):
        """
        list
        """
        # get the url
        url = reverse(T1Tests.base_name + '-list')
        data = {}
        response = self.client.get(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(TUSers.objects.count(), 0)
        # self.assertEqual(TUsers.objects.get().name, 'DabApps')

    def test_retrieve(self):
        """
        retrieve
        """
        # get the url
        u = self.create_one()
        log.debug('created {}', u)
        url = reverse(T1Tests.base_name + '-detail', args=(u.id,))
        log.debug('url is {}', url)
        data = {}
        response = self.client.get(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(TUSers.objects.count(), 1)
        # self.assertEqual(TUsers.objects.get().name, 'DabApps')

    def test_createAndUpdate(self):
        """
        retrieve
        """
        # get the url
        u = self.create_one()
        log.debug('created {}', u)
        url = reverse(T1Tests.base_name + '-create', args=(u.id,))
        log.debug('url is {}', url)
        data = {}
        response = self.client.get(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(TUSers.objects.count(), 1)
        # self.assertEqual(TUsers.objects.get().name, 'DabApps')
