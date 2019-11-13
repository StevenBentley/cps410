from django.contrib import admin
from .models import ParkingPicture

# Register your models here.
class ParkingPictureAdmin(admin.ModelAdmin):
    fields = ('picture', 'date')

admin.site.register(ParkingPicture, ParkingPictureAdmin)
