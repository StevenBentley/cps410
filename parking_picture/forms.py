from django import forms

class ParkingPictureForm(forms.Form):
    parking_lot = forms.CharField(max_length=50)
    picture = forms.ImageField()
