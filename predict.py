import tensorflow as tf
import numpy as np
from PIL import Image
from io import BytesIO
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Load cataract detection model
# def load_model(model_url, model_name):
#     try:
#         model_path = tf.keras.utils.get_file(model_name, origin=model_url)
#         model = tf.keras.models.load_model(model_path)
#         # logger.info(f"{model_name} loaded successfully.")
#         return model
#     except Exception as e:
#         logger.error(f"Error loading {model_name}: {e}")
#         raise e

# Cataract detection model
model_cataract = tf.keras.models.load_model('cataract-VGG16.h5')

# Eye validation model
model_eye = tf.keras.models.load_model('eyeval-VGG16.h5')

# Read Image:
def read_image(file: bytes) -> Image.Image:
    try:
        pil_image = Image.open(BytesIO(file))
        # logger.info("Image read successfully.")
        return pil_image
    except Exception as e:
        logger.error(f"Error reading image: {e}")
        raise e

# Cataract model async function
async def predict_cataract(image: Image.Image):
    try:
        image = image.resize((200, 200))
        image_array = np.array(image) / 255.0
        image_array = np.expand_dims(image_array, axis=0)
        detection = model_cataract.predict(image_array)
        result = detection[0][0]
        pred_info = "Non-cataract" if result > 0.5 else "Cataract"
        
        data = {
            "detection": pred_info,
            "confidence": float(1 - result)
        }
        
        return data
    except Exception as e:
        # logger.error(f"Error in predict_cataract function: {e}")
        raise e

# Eye validation async function
async def predict_eye(image: Image.Image):
    try:
        image_array = np.array(image.resize((224, 224))) / 255.0
        image_array = np.expand_dims(image_array, axis=0)
        detection = model_eye.predict(image_array)
        result = detection[0][0]
        pred_info = "Eye" if result > 0.5 else "Non-eye"
        data = {
            "detection": pred_info,
            "confidence": float(result)
        }
        # logger.info(f"Eye validation prediction: {data}")
        return data
    except Exception as e:
        logger.error(f"Error in predict_eye function: {e}")
        raise e
