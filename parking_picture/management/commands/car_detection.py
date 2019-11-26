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

    #def add_arguments(self, parser):
    #    parser.add_argument('poll_ids', nargs='+', type=int)

    def handle(self, *args, **options):
        # Root directory of the project
        BASE_DIR = settings.BASE_DIR

        # Directory of images to run detection on
        IMAGE_DIR = os.path.join(BASE_DIR, "media/")
        model = configure_rcnn_model(BASE_DIR)
        model = load_pre_trained_weights(BASE_DIR, model)
        analyze_images_using_model(model, IMAGE_DIR)
