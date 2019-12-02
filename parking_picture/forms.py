from django import forms

#Form for checking if requests have the correct data for ParkingPictures
class ParkingPictureForm(forms.Form):
    parking_lot = forms.CharField(max_length=50)
    picture = forms.ImageField()
