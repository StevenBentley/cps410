# Generated by Django 2.1.14 on 2019-11-26 20:08

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('parking_picture', '0002_parkinglotspots_parking_spots'),
    ]

    operations = [
        migrations.RenameField(
            model_name='parkinglotspots',
            old_name='data',
            new_name='spot_status',
        ),
    ]
