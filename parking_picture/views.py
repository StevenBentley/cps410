from django.shortcuts import render
from django.http import HttpResponse
import os

from .test_ml import get_data
from .models import ParkingPicture, ParkingLotSpots

def index(request):
    # return frontend app..
    return HttpResponse("we outchea")

def parking_picture(request):
    if request.method == 'GET':
        return HttpResponse(403)

    if request.POST == 'POST':
        # check if its valid...
        form = ParkingPictureForm(request.post, request.FILES)
        if form.is_valid():
            # file is saved
            form.save()
            HttpResponseRedirect('/')

        # Delete old file
        # os.remove(old_file_location)

def parking_data(request, lot):
    # ml get_data will call a function to also update the lot data
    lot_data = get_data(lot)
    ParkingLotSpots.objects.update(data=lot_data)
    return JsonResponse(lot_data)
