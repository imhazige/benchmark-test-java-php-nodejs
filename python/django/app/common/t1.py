from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets

from rest_framework.decorators import action
from rest_framework.response import Response

from common.models import TUSers
from common.serializer import TUSersSerializer
from . import log


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

    def update(self, request, pk=None):
        '''
        override
        see UpdateModelMixin
        https://www.django-rest-framework.org/api-guide/viewsets/#viewset-actions
        '''
        log.debug('request {0}', request.data)

        serializer = TUSersSerializer(data=request.data)
        # When a serializer is passed a `data` keyword argument you must call `.is_valid()` before attempting to access the serialized `.data` representation
        serializer.is_valid(raise_exception=True)
        serializer.save()
        # or
        # u = TUSers(serializer.data)
        # TUSers.objects.save(u)
        return Response(serializer.data)
