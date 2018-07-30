
import jwt
from rest_framework.views import APIView
from rest_framework import status
from rest_framework.response import Response
from datetime import datetime

from .models import TUSers

ALGORITHM = 'HS256'
SECRET = 'w9of98u 0-32o c2 $DSDFSD=ddsfl'


class T2TokenViewSet(APIView):
    """
    inspired from https://github.com/GetBlimp/django-rest-framework-jwt/blob/master/docs/index.md
    """

    # see https://www.django-rest-framework.org/api-guide/viewsets/
    def create(self, request, *args, **kwargs):
        # TODO:check user
        name = request.data.name
        password = request.data.password
        # refer to https://docs.djangoproject.com/en/2.0/topics/db/queries/#limiting-querysets
        user = TUSers.objects.filter(name=name)[:1]
        if user is None:
            # just response null
            return Response('')
        encoded = jwt.encode({'userId': user.id}, SECRET, ALGORITHM)
        # TODO: create token
        return Response(encoded)
