import os
import numpy as np
import cv2
import mrcnn.config
import mrcnn.utils
from mrcnn.model import MaskRCNN
from pathlib import Path
#from .models import ParkingLotSpots

# Configuration that will be used by the Mask-RCNN library
class MaskRCNNConfig(mrcnn.config.Config):
    NAME = "coco_pretrained_model_config"
    IMAGES_PER_GPU = 1
    GPU_COUNT = 1
    NUM_CLASSES = 1 + 80  # COCO dataset has 80 classes + one background class
    DETECTION_MIN_CONFIDENCE = 0.6

# Filter a list of Mask R-CNN detection results to get only the detected cars / trucks
def get_car_boxes(boxes, class_ids):
    car_boxes = []

    for i, box in enumerate(boxes):
        # car - 3, truck - 8
        if class_ids[i] in [3, 8]:
            car_boxes.append(box)

    return np.array(car_boxes)

def configure_rcnn_model():
    # Directory to save logs and trained model
    MODEL_DIR = os.path.join(ROOT_DIR, "logs")
    # Create a Mask-RCNN model in inference mode
    model = MaskRCNN(mode="inference", model_dir=MODEL_DIR, config=MaskRCNNConfig())
    return model

def load_pre_trained_weights(model):
    # Local path to trained weights file
    COCO_MODEL_PATH = os.path.join(ROOT_DIR, "mask_rcnn_coco.h5")
    # Download COCO trained weights from Releases if needed
    if not os.path.exists(COCO_MODEL_PATH):
        mrcnn.utils.download_trained_weights(COCO_MODEL_PATH)
    # Load pre-trained model
    model.load_weights(COCO_MODEL_PATH, by_name=True)
    return model

def analyze_images_using_model(model, image_dir):
    for img_file in os.listdir(image_dir):
        # Read image in color using OpenCV
        img = cv2.imread('../media/' + img_file, 1)
        # OpenCV is in BGR mode. Convert to RGB Mode for RCNN
        rgb_img = img[:, :, ::-1]
        # Run the image through the Mask R-CNN model to get results.
        results = model.detect(images=[rgb_img])

        # Mask R-CNN assumes we are running detection on multiple images.
        # We only passed in one image to detect, so only grab the first result.
        r = results[0]

         # The r variable will now have the results of detection:
            # - r['rois'] are the bounding box of each detected object
            # - r['class_ids'] are the class id (type) of each detected object
            # - r['scores'] are the confidence scores for each detection
            # - r['masks'] are the object masks for each detected object (which gives you the object outline)

        print("bounding box of each object: ",)
        print (r['rois'])
        print("Object class ids: ",)
        print (r['class_ids'])

        # Filter the results to only grab the car / truck bounding boxes
        car_boxes = get_car_boxes(r['rois'], r['class_ids'])
        print("Cars found in picture:")

        # Draw each box on the frame
        for box in car_boxes:
            print("Car: ", box)

            y1, x1, y2, x2 = box

            # Draw the box
            cv2.rectangle(img, (x1, y1), (x2, y2), (0, 255, 0), 1)

            # Show the frame of video on the screen
            #not working right now
            #cv2.imshow('image', img)

            # Hit 'q' to quit
            #if cv2.waitKey(1) & 0xFF == ord('q'):
            #    break

            # Location of parking spaces
            # Need some type of model object to hold previous images
            # parked car locations. This is just for now
            parked_car_boxes = None
            check_open_spots(parked_car_boxes, r)
        print ("------------------img done---------------------------------")

            # lot_data = check_open_spots...
            # return lot_data

def check_open_spots(parked_car_boxes, r):
    # try:
        # parked_car_boxes = ParkingLotSpots.objects.get(name=name)
    #except:
    if parked_car_boxes is None:
        # Somehow we need to set this variable to have a parked car in each location
        # for now we just use the whats currently there and check against it later
        # Save the location of each car as a parking space box and go to the next frame.
        parked_car_boxes = get_car_boxes(r['rois'], r['class_ids'])
        # ParkingLotSpots.objects.create(lot_name=name, data=parked_car_boxes)
    else:
        # We already know where the parking spaces are. Check if any are currently unoccupied.

        # Get where cars are currently located in the frame
        car_boxes = get_car_boxes(r['rois'], r['class_ids'])

        # See how much those cars overlap with the known parking spaces
        overlaps = mrcnn.utils.compute_overlaps(parked_car_boxes, car_boxes)

        # Loop through each known parking space box
        for parking_area, overlap_areas in zip(parked_car_boxes, overlaps):

            # For this parking space, find the max amount it was covered by any
            # car that was detected in our image (doesn't really matter which car)
            max_IoU_overlap = np.max(overlap_areas)

            # Get the top-left and bottom-right coordinates of the parking area
            y1, x1, y2, x2 = parking_area

            # Check if the parking space is occupied by seeing if any car overlaps
            # it by more than 0.15 using IoU
            if max_IoU_overlap < 0.15:
                # Parking space not occupied! Draw a green box around it
                cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 255, 0), 3)
            else:
                # Parking space is still occupied - draw a red box around it
                cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 0, 255), 1)

            # Write the IoU measurement inside the box
            font = cv2.FONT_HERSHEY_DUPLEX
            cv2.putText(frame, f"{max_IoU_overlap:0.2}", (x1 + 6, y2 - 6), font, 0.3, (255, 255, 255))

        # return new lot_data

def get_data():
    # Root directory of the project
    ROOT_DIR = Path("..")
    # Directory of images to run detection on
    IMAGE_DIR = os.path.join(ROOT_DIR, "media/")
    model = configure_rcnn_model()
    model = load_pre_trained_weights(model)
    analyze_images_using_model(model, IMAGE_DIR)
    return []

if __name__ == "__main__":
    # Root directory of the project
    ROOT_DIR = Path("..")
    # Directory of images to run detection on
    IMAGE_DIR = os.path.join(ROOT_DIR, "media/")
    model = configure_rcnn_model()
    model = load_pre_trained_weights(model)
    analyze_images_using_model(model, IMAGE_DIR)

# Clean up everything when finished
# not working on ubuntu subsystem
#cv2.destroyAllWindows()
