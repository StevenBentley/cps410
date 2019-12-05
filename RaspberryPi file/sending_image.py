import requests, hmac, hashlib, datetime, time
from picamera import PiCamera

# TO DO : implement public-private key exchange for accessing url
# Allow cross origin request to url for this to work
camera = PiCamera()
filename = 'img.jpg'


while True:
    camera.capture(filename)

    url = 'http://parking-tracker.azurewebsites.net/parking_picture/api/parking_picture'
    files = { 'picture': open(filename, 'rb')}
    # data = { 'parking_lot': 'lot 1'}
    # requests.post(url, files=files, data=data)


    payload = {'parking_lot': 'lot 1'}
    s = requests.Session()

    headers = {
        'publicKey': 'PIPUBLICAPIKEY',
        'hmac': ''
    }

    try:
        request = requests.Request(
        'POST', url,
        data=payload, files=files, headers=headers
        )
        prepped = request.prepare()
        signature = hmac.new(
            b'91I8sOjqp6hIE99o',
            prepped.body,
            digestmod=hashlib.sha256
        )
        prepped.headers['hmac'] = signature.hexdigest()
        response = s.send(prepped)
    except Exception as e:
        print(e)

    time.sleep(30)
