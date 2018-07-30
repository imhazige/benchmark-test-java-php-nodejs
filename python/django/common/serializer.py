# see https://www.django-rest-framework.org/tutorial/1-serialization/

import os
import hashlib
import getpass
import base64


from rest_framework import serializers
from .models import TUSers
from . import log
import binascii
from django.core.signing import Signer
import django.contrib.auth.hashers as hasher
import hashlib


class TUSersSerializer(serializers.ModelSerializer):

    # def save(self):
    #     """
    #     refer to https://www.django-rest-framework.org/api-guide/serializers/#saving-instances
    #     """

    #     # TODO:salt and enctrypt
    #     password = self.validated_data['password']
    #     salt = os.urandom(16)
    #     sha = hashlib.sha512()
    #     sha.update(password)
    #     sha.update(salt)
    #     ssha512 = base64.b64encode('{}{}'.format(sha.digest(), salt))

    #     return super().save()

    def encrypt1(self, password, salt):
        """
        # see https://www.tunnelsup.com/using-salted-sha-hashes-with-dovecot-authentication/
        this way is not good, and encrypted password is too long
        """
        sha = hashlib.sha512()
        sha.update(password)
        sha.update(salt)
        ssha512 = sha.hexdigest()
        salt = binascii.hexlify(salt).decode('utf-8')

        return (ssha512, salt)

    def encrypt2(self, password, salt):
        """
        refer to https://docs.djangoproject.com/en/2.0/topics/signing/
        this is not good too, the encrypted password have the raw password, you can substring
        """
        salt = binascii.hexlify(salt).decode('utf-8')
        signer = Signer(salt=salt)
        password = signer.sign(password)

        return (password, salt)

    def encrypt3(self, password, salt):
        """
        refer to https://docs.djangoproject.com/en/2.0/topics/auth/passwords/
        this is not good too, the encrypted password have the raw password, you can substring
        """
        hashermethod = 'pbkdf2_sha256'
        iteration = '100000'
        salt = binascii.hexlify(salt).decode('utf-8')
        passwordenced = hasher.make_password(
            password, salt, hasher=hashermethod).split('$')[-1]
        # check
        # dec = hasher.check_password(password, '%s$%s$%s$%s' %
        # (hashermethod, iteration, salt, passwordenced))

        # :use md5 to make its length be 32
        m = hashlib.md5()

        m.update(passwordenced.encode('utf-8'))
        passwordenced = m.hexdigest()

        return (passwordenced, salt)

    def to_internal_value(self, data):
        """
        see https://www.django-rest-framework.org/api-guide/serializers/#overriding-serialization-and-deserialization-behavior

        """

        # :salt and enctrypt
        password = data['password']
        password = password.encode('utf-8')
        salt = os.urandom(16)
        (passpordstr, saltstr) = self.encrypt3(password, salt)
        data['password'] = passpordstr
        data['salt'] = saltstr

        ret = super().to_internal_value(data)
        return ret

    def to_representation(self, instance):
        ret = super().to_representation(instance)
        # remove the security data when represent it to the api
        ret.pop("salt", None)
        ret.pop("password", None)
        return ret

    class Meta:
        model = TUSers
        # fields = ('id', 'title', 'code', 'linenos', 'language', 'style')
        fields = '__all__'
