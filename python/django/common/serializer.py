# see https://www.django-rest-framework.org/tutorial/1-serialization/


from rest_framework import serializers
from .models import TUSers


class TUSersSerializer(serializers.ModelSerializer):
    class Meta:
        model = TUSers
        # fields = ('id', 'title', 'code', 'linenos', 'language', 'style')
        fields = '__all__'
