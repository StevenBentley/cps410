from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.conf import settings
from django.template import loader
from django.core.management import call_command

import os, io, ast
from .models import ParkingLot, ParkingPicture, ParkingLotSpots
from .forms import ParkingPictureForm
from .helpers import handle_file_upload, store_picture, encryption_keys_match

def view_car_pics(request):
    #Renders the template using parking lot picture data
    template = loader.get_template('car_pics.html')
    context = {'images': {}}
    image_dir = sorted(os.listdir('media/copies'))

    for file in image_dir:
        context['images'][os.path.splitext(file)[0]] = file
    return HttpResponse(template.render(context, request))


#CSRF exempt because PI couldn't access the server otherwise, only temporary
@csrf_exempt
def parking_picture(request):
    #View where the parking pictures requests are sent
    if request.method == 'GET':
        return HttpResponse(403)

    if request.method == 'POST':
        if encryption_keys_match(request):
            form = ParkingPictureForm(request.POST, request.FILES)
            if form.is_valid():
                handle_file_upload(request.POST['parking_lot'], request.FILES['picture'])
                return HttpResponse(200)
        else:
            return HttpResponse(403)

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
