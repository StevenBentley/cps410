from django.conf import settings
import hashlib, base64, hmac, datetime
from .models import ParkingLot, ParkingPicture

def handle_file_upload(lot, file):
    #Format file name and upload file to media location
    now = datetime.datetime.now().strftime("%m-%d-%y_%H-%M-%S")
    file_name = str(file)
    file_name = lot + "_" + now + file_name[file_name.find('.'):]
    with open("media/" + file_name, 'wb+') as destination:
        for chunk in file.chunks():
            destination.write(chunk)
    store_picture(lot, file_name)

def store_picture(lot, file):
    #Get or create a parking lot, then foreign key the picture 
    #to the parking lot database model
    try:
        parking_lot = ParkingLot.objects.get(lot_name=lot)
    except:
        parking_lot = ParkingLot.objects.create(lot_name=lot)
        parking_lot.save()
    parking_picture = ParkingPicture.objects.create(parking_lot=parking_lot, picture=file)
    parking_picture.save()

def encryption_keys_match(request):
    #Check if the encrypted key passed in the request is the same
    #as the key on the server when it is encrypted
    try:
        public_key = settings.PI_SECRET_KEY[request.META["HTTP_PUBLICKEY"]]
        signed_hmac = request.META["HTTP_HMAC"]
        digest = hmac.new(public_key, msg=request.body, digestmod=hashlib.sha256)
        msg = digest.hexdigest()
        if hmac.compare_digest(msg, signed_hmac):
            return True
        else:
            return False
    except KeyError:
        # Logging?
        return False
