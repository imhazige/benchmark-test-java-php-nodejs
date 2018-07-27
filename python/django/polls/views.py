from django.shortcuts import render

from django.http import HttpResponse

from common import log


def index(request):
    log.debug('ddddd {} {name}', 'sss', name='haha')
    log.exception('hhohohoho---')
    return HttpResponse("Hello, world. You're at the polls index.")
