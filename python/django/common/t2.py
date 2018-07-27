from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from rest_framework import status

from rest_framework.decorators import action
from rest_framework.response import Response

from common.models import TUSers
from common.serializer import TUSersSerializer
from . import log
import json
import uuid


class T2TokenViewSet(viewsets.ModelViewSet):
    """
    inspired from https://github.com/GetBlimp/django-rest-framework-jwt/blob/master/docs/index.md
    """

    # see https://www.django-rest-framework.org/api-guide/viewsets/
    def create(self, request, *args, **kwargs):
        # TODO:check user

        # TODO: create token
        return Response(snippet.highlighted)


class T2ViewSet(viewsets.ModelViewSet):
    """
    This viewset automatically provides `list` and `detail` actions.
    """

    queryset = TUSers.objects.all()
    serializer_class = TUSersSerializer

    # https://www.django-rest-framework.org/api-guide/authentication/#how-authentication-is-determined
    authentication_classes = (MyJWTAuthentication)
    permission_classes = (MyIsAuthenticated)

    def create_(self, request):
        log.debug('-----------------create')
        # super(T1ViewSet, self).create(request)

        # TODO:set id, date
        serializer = TUSersSerializer(data=request.data)
        # When a serializer is passed a `data` keyword argument you must call `.is_valid()` before attempting to access the serialized `.data` representation
        valid = serializer.is_valid(raise_exception=False)
        if not valid:
            log.debug('valid {}', serializer.errors)
            return Response(json.dumps(serializer.errors), status=status.HTTP_400_BAD_REQUEST)
        # TODO:do not use serializer.save(), it have validation
        serializer.save()
        return Response(serializer.data, status=status.HTTP_200_OK)

    def create(self, request):
        log.debug('-----------------create')
        # super(T1ViewSet, self).create(request)

        # TODO:set salt
        u = request.data
        # u['salt'] = ?
        u = TUSers.objects.create(**u)
        serializer = TUSersSerializer(u)
        return Response(serializer.data, status=status.HTTP_200_OK)

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
