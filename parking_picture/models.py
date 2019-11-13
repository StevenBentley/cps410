from django.db import models

# Create your models here.
class ParkingPicture(models.Model):
    picture = models.ImageField(upload_to='media/', height_field=None, width_field=None,
        max_length=100)
    date = models.DateTimeField()
