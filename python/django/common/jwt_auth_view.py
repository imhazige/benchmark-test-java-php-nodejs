
import jwt
from rest_framework.views import APIView
from rest_framework import status
from rest_framework.response import Response
from datetime import datetime
from rest_framework import viewsets

from .models import TUSers
from . import log


class T2TokenViewSet(viewsets.ViewSet):
    """
    inspired from https://github.com/GetBlimp/django-rest-framework-jwt/blob/master/docs/index.md
    """

    # well, I know this is a bad idea to place them at here
    ALGORITHM = 'HS256'
    SECRET = 'w9of98u 0-32o c2 $DSDFSD=ddsfl'

# see https://www.django-rest-framework.org/api-guide/viewsets/
    def create(self, request, *args, **kwargs):
        # TODO:check user
        name = request.data['name']
        password = request.data['password']
        log.debug('----- {}', name)
        # refer to https://docs.djangoproject.com/en/2.0/topics/db/queries/#limiting-querysets
        user = TUSers.objects.filter(name=name)[:1]
        if user is None or user.count() < 1:
            # just response null
            log.debug('no such user {}', name)
            return Response('')
        user = user[0]
        log.debug('>>>> {}', user)
        encoded = jwt.encode({'userId': user.id},
                             T2TokenViewSet.SECRET, T2TokenViewSet.ALGORITHM)
        # TODO: create token
        return Response(encoded)
