from django.urls import path

from . import views

urlpatterns = [
    path('', views.view_car_pics, name='view_car_pics'),
    path('api/parking_picture', views.parking_picture, name='get_parking_pic'),
    path('api/parking_data/<int:lot>/', views.parking_data, name='get_parking_data')
]
