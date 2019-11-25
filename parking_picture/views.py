from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect
from django.views.decorators.csrf import csrf_exempt
from django.conf import settings

import datetime
import json
import os
import hashlib, base64, hmac
# from .test_ml import get_data
from .models import ParkingLot, ParkingPicture, ParkingLotSpots
from .forms import ParkingPictureForm

def index(request):
    # return frontend app..
    return HttpResponse("we outchea")

@csrf_exempt
def parking_picture(request):
    if request.method == 'GET':
        return HttpResponse(403)

    if request.method == 'POST':
        # for some reason the return response statements are messed up
        # for example, when the keys dont match, "we here boooi" prints, but
        # a 200 response is still returned?
        try:
            public_key = settings.PI_SECRET_KEY[request.META["HTTP_PUBLICKEY"]]
            signed_hmac = request.META["HTTP_HMAC"]
            digest = hmac.new(public_key,
                msg=request.body, digestmod=hashlib.sha256)
            msg = digest.hexdigest()
            if not hmac.compare_digest(msg, signed_hmac):
                print("we here boooi")
                return HttpResponse(401)

        except KeyError:
            print("hello")
            return HttpResponse(400)

        form = ParkingPictureForm(request.POST, request.FILES)
        if form.is_valid():
            handle_file_upload(request.POST['parking_lot'], request.FILES['picture'])
            HttpResponseRedirect('/')
        else:
            return HttpResponse(422)

    return HttpResponse(200)
        # Delete old file
        # os.remove(old_file_location)

def parking_data(request, lot):
    # ml get_data will call a function to also update the lot data
    lot_data = get_data(lot)
    ParkingLotSpots.objects.update(data=lot_data)
    return JsonResponse(lot_data)


def handle_file_upload(lot, file):
    print("111")
    print(file)
    print("222")
    now = datetime.datetime.now().strftime("%m-%d-%y_%H-%M-%S")
    print(file)
    file_name = str(file)
    file_name = lot + "_" + now + file_name[file_name.find('.'):]
    print(file_name)
    with open("media/" + file_name, 'wb+') as destination:
        for chunk in file.chunks():
            destination.write(chunk)
    store_picture(lot, file_name)

def store_picture(lot, file):
    try:
        parking_lot = ParkingLot.objects.get(lot_name=lot)
    except:
        parking_lot = ParkingLot.objects.create(lot_name=lot)
        parking_lot.save()
    parking_picture = ParkingPicture.objects.create(parking_lot=parking_lot, picture=file)
    parking_picture.save()
