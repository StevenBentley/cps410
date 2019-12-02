import requests, hmac, hashlib

# Todo: Allow cross origin request to url for this to work!!

url = 'http://localhost:8000/parking_picture/api/parking_picture'
files = { 'picture': open('test_image.png', 'rb')}
payload = {'parking_lot': 'lot 2'}
s = requests.Session()

headers = {
    'publicKey': 'PIPUBLICAPIKEY',
    'hmac': ''
}

try:
    #Encrpyt key to send along with requests. If key does not match the key
    #on the server, the request will fail.
    request = requests.Request(
    'POST', 'http://localhost:8000/parking_picture/api/parking_picture',
    data=payload, files=files, headers=headers
    )
    prepped = request.prepare()
    signature = hmac.new(
        b'ThisIsAFakeKey',
        prepped.body,
        digestmod=hashlib.sha256
    )
    prepped.headers['hmac'] = signature.hexdigest()
    response = s.send(prepped)
except Exception as e:
    print(e)
