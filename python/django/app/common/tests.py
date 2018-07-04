from django.test import TestCase

# Create your tests here.
# See https://docs.djangoproject.com/en/2.0/intro/tutorial05/

# See http://www.django-rest-framework.org/api-guide/testing/
from django.urls import reverse
from rest_framework import status
from rest_framework.test import APITestCase
from .models import TUSers


class T1Tests(APITestCase):
    base_name = 't1'

    def set_up(self):
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
