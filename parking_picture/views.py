from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.conf import settings
from django.template import loader

import datetime
import json
import os, io, ast
import hashlib, base64, hmac

from .models import ParkingLot, ParkingPicture, ParkingLotSpots
from .forms import ParkingPictureForm
from django.core.management import call_command

def view_car_pics(request):
    template = loader.get_template('car_pics.html')
    context = {'images': {}}
    image_dir = sorted(os.listdir('media/copies'))

    for file in image_dir:
        context['images'][os.path.splitext(file)[0]] = file
    return HttpResponse(template.render(context, request))

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
                return HttpResponse(401)

        except KeyError:
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
    out = io.StringIO()
    call_command('car_detection', stdout=out)
    values = ast.literal_eval(out.getvalue())

    space_status = {}
    spaces = ['space1', 'space4', 'space6', 'space3', 'space2', 'space5']
    for i, space in enumerate(spaces):
        space_status[space] = values[i]

    return JsonResponse(space_status)


def handle_file_upload(lot, file):
    now = datetime.datetime.now().strftime("%m-%d-%y_%H-%M-%S")
    file_name = str(file)
    file_name = lot + "_" + now + file_name[file_name.find('.'):]
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
