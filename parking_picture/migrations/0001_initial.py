# Generated by Django 2.1.14 on 2019-11-26 17:22

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='ParkingLot',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('lot_name', models.CharField(max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='ParkingLotSpots',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('data', models.TextField(max_length=1000)),
                ('parking_lot', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='parking_picture.ParkingLot')),
            ],
        ),
        migrations.CreateModel(
            name='ParkingPicture',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('picture', models.ImageField(upload_to='media/')),
                ('date', models.DateTimeField(auto_now=True)),
                ('parking_lot', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='parking_picture.ParkingLot')),
            ],
        ),
    ]
