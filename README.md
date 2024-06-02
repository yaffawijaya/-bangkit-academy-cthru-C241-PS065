# Cataract Detection - Machine Learning Model APIs

This project provides a simple API for predictions. It uses FastAPI and is designed to be run locally.

## Features

- Prediction API endpoint
- Easy setup and deployment

## Requirements

- Python 3.8+
- `uvicorn`
- Other dependencies listed in `requirements.txt`

## Installation

Follow these steps to get the project up and running:

1. **Clone the repository**

    ```sh
    git clone https://github.com/yaffawijaya/cataract-detection-model-api.git
    cd cataract-detection-model-api
    ```

2. **Create a virtual environment**

    ```sh
    python3 -m venv bangkit
    source bangkit/bin/activate  # On Windows use `venv\Scripts\activate`
    ```

3. **Install dependencies**

    ```sh
    pip install -r requirements.txt
    ```

## Running the API

To run the API locally, use the following command:

```sh
uvicorn main:app --reload

## Test the endpoint `/predict/` 

1. Click `Try it out`
2. Input some image test