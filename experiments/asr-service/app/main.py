from fastapi import FastAPI, UploadFile, File
from pydantic import BaseModel
import time

app = FastAPI()

class TranscriptionResponse(BaseModel):
    text: str
    latency_ms: int

@app.get("/health")
def health():
    return {"status": "UP"}

@app.post("/transcribe")
async def transcribe(file: UploadFile = File(...)):
    start = time.time()
    
    # Mock: return fixed text
    text = "help me"  # Simulating recognized speech
    
    latency = int((time.time() - start) * 1000)
    
    return TranscriptionResponse(
        text=text,
        latency_ms=latency
    )
