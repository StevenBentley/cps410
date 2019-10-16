import requests

# TO DO : implement public-private key exchange for accessing url
# Allow cross origin request to url for this to work

url = 'http://localhost:8000/parking_picture/api/parking_picture'
files = { 'media': open('test_image.png', 'rb')}
requests.post(url, files=files)
