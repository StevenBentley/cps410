from django.db import models

class ParkingLot(models.Model):
    lot_name = models.CharField(max_length=100)

    def __str__(self):
        return self.lot_name

class ParkingPicture(models.Model):
    parking_lot = models.ForeignKey(ParkingLot, on_delete=models.CASCADE)
    picture = models.ImageField(upload_to='media/', height_field=None,
        width_field=None, max_length=100)
    date = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.parking_lot.lot_name + "parking pictures"

class ParkingLotSpots(models.Model):
    parking_lot = models.ForeignKey(ParkingLot, on_delete=models.CASCADE)
    data = models.TextField(max_length=1000) # this will be JSON format but SQL doesn't support json

    def __str__(self):
        return self.parking_lot.name + "parking spots"
