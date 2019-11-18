from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('api/parking_picture', views.parking_picture, name='get_parking_pic'),
    path('api/parking_data/(?P<lot>)/$', views.parking_data, name='get_parking_data')
]
