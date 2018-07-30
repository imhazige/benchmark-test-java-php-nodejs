import jwt
from rest_framework import permissions
from django.utils.translation import ugettext as _
from rest_framework.authentication import (
    BaseAuthentication, get_authorization_header
)


from rest_framework import exceptions
from .jwt_auth_view import T2TokenViewSet


class MyJSONWebTokenAuthentication(BaseAuthentication):
    """
    see https://www.django-rest-framework.org/api-guide/authentication/#basicauthentication
    """

    def authenticate(self, request):
        """
        Authenticate the request and return a two-tuple of (user, token).
        """
        jwt_value = get_authorization_header(request)
        if jwt_value is None:
            return None

        try:
            user = jwt.decode(jwt_value, T2TokenViewSet.SECRET,
                              algorithms=[T2TokenViewSet.ALGORITHM])
            return (user, jwt_value)
        except jwt.ExpiredSignature:
            msg = _('Signature has expired.')
            raise exceptions.AuthenticationFailed(msg)
        except jwt.DecodeError:
            msg = _('Error decoding signature.')
            raise exceptions.AuthenticationFailed(msg)
        except jwt.InvalidTokenError:
            raise exceptions.AuthenticationFailed()

    def authenticate_header(self, request):
        """
        Return a string to be used as the value of the `WWW-Authenticate`
        header in a `401 Unauthenticated` response, or `None` if the
        authentication scheme should return `403 Permission Denied` responses.
        """
        # the header just be Authentication:<token>
        return r''
        # return r'JWT realm="api"'


class CustomerAccessPermission(permissions.IsAuthenticated):
    """
    see https://www.django-rest-framework.org/api-guide/permissions/#custom-permissions
    """

    def has_permission(self, request, view):
        """
        this class is as same as IsAuthenticated
        """
        return request.user
