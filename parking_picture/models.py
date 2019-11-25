from django.db import models
from django.contrib.postgres.fields import JSONField

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
        return self.parking_lot.name + "parking pictures"

class ParkingLotSpots(models.Model):
    parking_lot = models.ForeignKey(ParkingLot, on_delete=models.CASCADE)
    data = JSONField()

    def __str__(self):
        return self.parking_lot.name + "parking spots"
