from django.db import models

# Create your models here.


class TUSers(models.Model):
    # objects = models.Manager()
    # https://code.djangoproject.com/ticket/9349, but can not found the FixedCharField, will not use migrate
    id = models.CharField(primary_key=True, max_length=36)
    name = models.CharField(max_length=100, db_column='name')
    password = models.CharField(max_length=32)
    salt = models.CharField(max_length=32)
    password = models.CharField(max_length=32)
    create_time = models.DateTimeField()
    update_time = models.DateTimeField()

    class Meta:
        # override the default table name
        db_table = 't_users'
        ordering = ('create_time',)
