from django.contrib import admin
from .models import ParkingLot, ParkingPicture, ParkingLotSpots

# Register your models here.
class ParkingLotAdmin(admin.ModelAdmin):
    fields = ('lot_name',)
    
class ParkingPictureAdmin(admin.ModelAdmin):
    fields = ('parking_lot', 'picture', )
    readonly_fields = ('parking_lot', )

class ParkingLotSpotsAdmin(admin.ModelAdmin):
    fields = ('parking_lot', 'parking_spots', 'spot_status')
    readonly_fields = ('parking_lot', 'parking_spots' )

admin.site.register(ParkingLot, ParkingLotAdmin)
admin.site.register(ParkingPicture, ParkingPictureAdmin)
admin.site.register(ParkingLotSpots, ParkingLotSpotsAdmin)
