from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
import uuid
import logging
import firebase_admin
from firebase_admin import credentials, firestore
from datetime import datetime

# Import functions from predict.py
from predict import read_image, predict_eye, predict_cataract

# Configure firebase credentials
cred = credentials.Certificate('credentials.json')
firebase_admin.initialize_app(cred)

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI()

# Allow CORS for all origins during development
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["GET", "POST"],
    allow_headers=["*"],
)

# Initialize Firestore client
try:
    db = firestore.client()
    logger.info("Connected to Firestore successfully.")
except Exception as e:
    logger.error(f"Error connecting to Firestore: {e}")

# Home endpoint
@app.get("/")
async def root():
    return {"message": "Hello CThru!"}

# Prediction endpoint, contains model prediction
@app.post("/predict/")
async def predict_endpoint(file: UploadFile = File(...)):
    try:
        # Read image
        image = read_image(await file.read())
        # logger.info("Image read successfully.")

        # Eye validation
        eye_detection = await predict_eye(image)
        # logger.info(f"Eye detection result: {eye_detection}")

        if eye_detection["detection"] == "Non-eye":
            return JSONResponse(content={
                "message": "Eye is not detected",
                "data": {
                    "detection": "Non-eye",
                    "confidence": eye_detection["confidence"]
                }
            })
        else:
            # Cataract detection
            cataract_detection = await predict_cataract(image)
            # logger.info(f"Cataract detection result: {cataract_detection}")

            # Generate a random UUID for the 'id' property
            id_value = str(uuid.uuid4())

            # Current datetime
            current_datetime = datetime.now().isoformat()

            # Prepare response data
            data = {
                "id": id_value,
                "datetime": current_datetime,
                "eye_detection": eye_detection["detection"],
                "eye_confidence": eye_detection["confidence"],
                "cataract_detection": cataract_detection["detection"],
                "cataract_confidence": cataract_detection["confidence"]
            }

            # Store prediction result in Firestore
            try:
                doc_ref = db.collection('detections').document(id_value)
                doc_ref.set(data)
                # logger.info(f"Data stored successfully with id: {id_value}")
            except Exception as e:
                logger.error(f"Error storing data in Firestore: {e}")
                raise HTTPException(status_code=500, detail="Error storing data in Firestore")

        # Return prediction result
        return JSONResponse(content={"message": "Detection successful", "data": data})

    except Exception as e:
        logger.error(f"Error during prediction: {e}")
        raise HTTPException(status_code=500, detail=f"An error occurred during prediction: {e}")

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
