from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.
def index(request):
    return HttpResponse("we outchea")

def parking_picture(request):
    #request.get_that_picture_securely_and_save_it
    print (request)
    return HttpResponse("hell yea")
