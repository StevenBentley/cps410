# Generated by Django 2.1.14 on 2019-11-26 20:02

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('parking_picture', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='parkinglotspots',
            name='parking_spots',
            field=models.TextField(default='', max_length=1000),
        ),
    ]
