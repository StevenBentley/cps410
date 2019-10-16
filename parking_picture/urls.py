from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('/api/parking_picture', views.parking_picture, name='index')
]
