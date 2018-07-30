"""app URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
# from django.urls import include, path
from django.conf.urls import url, include
from rest_framework import routers, serializers, viewsets
from common.t1 import T1ViewSet
from common.t2 import T2ViewSet
from common.jwt_auth_view import T2TokenViewSet

router = routers.DefaultRouter()
# t1
router.register(r't1/users', T1ViewSet, 't1')
router.register(r't2/users', T2ViewSet, 't2')
router.register(r't2/token', T2TokenViewSet, 't2_token')

urlpatterns = [
    url(r'^', include(router.urls)),
    # path('polls/', include('polls.urls')),
    # path('admin/', admin.site.urls),
]
