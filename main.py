from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
from PIL import Image
from io import BytesIO
import uuid  # For generating UUIDs
import numpy as np

# Import functions from predict.py
from predict import read_image, predict_eye, predict_cataract

app = FastAPI()

# Allow CORS for all origins during development
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["GET", "POST", "PUT", "DELETE", "OPTIONS"],
    allow_headers=["*"],
)

# home endpoint
@app.get("/")
async def root():
    return {"message": "Hello CThru!"}

# Check file size endpoint
@app.post("/filesize/")
async def file_size(file: bytes = File(...)):
    length_file = len(file)
    
    # Determine file size format
    if length_file < 1024:
        size = f"{length_file} bytes"
    elif length_file < 1024 * 1024:
        size = f"{length_file / 1024:.2f} KB"
    else:
        size = f"{length_file / (1024 * 1024):.2f} MB"
    
    return {"file_size": size}

# prediction endpoint, contains model prediction
@app.post("/predict/")
async def predict_endpoint(file: UploadFile = File(...)):
    try:
        # Read image
        image = read_image(await file.read())
        
        # Eye validation
        eye_prediction = await predict_eye(image)
        
        if eye_prediction["prediction"] == "Non-eye":
            return JSONResponse(content={
                "message": "Eye is not detected", 
                "data": 
                    {"prediction": "Non-eye", 
                     "confidence": eye_prediction["confidence"]}})
        else:
            # Cataract detection
            cataract_prediction = await predict_cataract(image)
            
            # Generate a random UUID for the 'id' property
            id_value = str(uuid.uuid4())
            
            # Prepare response data
            data = {
                "id": id_value,
                "eye_prediction": eye_prediction["prediction"],
                "eye_confidence": eye_prediction["confidence"],
                "cataract_prediction": cataract_prediction["prediction"],
                "cataract_confidence": cataract_prediction["confidence"]
            }
        
        # Return prediction result
        return JSONResponse(content={"message": "Prediction successful", "data": data})
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
