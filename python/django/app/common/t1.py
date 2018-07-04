from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets

from rest_framework.decorators import action
from rest_framework.response import Response

from common.models import TUSers
from common.serializer import TUSersSerializer


class T1ViewSet(viewsets.ModelViewSet):
    """
    This viewset automatically provides `list` and `detail` actions.
    """
    queryset = TUSers.objects.all()
    serializer_class = TUSersSerializer

    # see https://www.django-rest-framework.org/api-guide/viewsets/
    # @action(detail=True, method=['get'])
    # def get_one(self, request, *args, **kwargs):
    #     snippet = self.get_object()
    #     return Response(snippet.highlighted)

    # def retrieve(self, request, id=None):
    # TUSers.objects
