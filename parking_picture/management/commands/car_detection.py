from django.core.management.base import BaseCommand, CommandError
from django.conf import settings

import os
import warnings

warnings.simplefilter(action='ignore', category=FutureWarning)

from parking_picture.models import ParkingLotSpots
from parking_picture.test_ml import ( configure_rcnn_model,
    load_pre_trained_weights, analyze_images_using_model )

class Command(BaseCommand):
    help = ''

    def handle(self, *args, **options):
        # Root directory of the project
        BASE_DIR = settings.BASE_DIR
        # Directory of images to run detection on
        img_dir = os.path.join(BASE_DIR, "media/")
        copies_dir = os.path.join(BASE_DIR, "media/copies/")
        if not os.path.exists(copies_dir):
            os.makedirs(copies_dir)
            
        model = configure_rcnn_model(BASE_DIR)
        model = load_pre_trained_weights(BASE_DIR, model)

        # Sort list of img files and remove copies directory
        img_dir_list = sorted(os.listdir(img_dir))
        img_dir_list.remove('copies')

        for i, img_file in enumerate(img_dir_list):
            copied_img = os.path.join(copies_dir, img_file)
            if i == (len(img_dir_list)-1) or os.path.isfile(copied_img) == False:
                values = analyze_images_using_model(model, img_file, img_dir, copies_dir)

        return str(values)
