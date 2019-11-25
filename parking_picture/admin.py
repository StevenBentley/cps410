from django.contrib import admin
from .models import ParkingLot, ParkingPicture

# Register your models here.
class ParkingLotAdmin(admin.ModelAdmin):
    fields = ('lot_name',)

class ParkingPictureAdmin(admin.ModelAdmin):
    fields = ('picture', 'date')

admin.site.register(ParkingLot, ParkingLotAdmin)
admin.site.register(ParkingPicture, ParkingPictureAdmin)
