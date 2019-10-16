from django.db import models

# Create your models here.
class ParkingPicture(models.Model):
    picture = ImageField(upload_to=None, height_field=None, width_field=None,
        max_length=100, **options)
    captured_date = DateTimeField(auto_now=True, auto_now_add=True **options)
