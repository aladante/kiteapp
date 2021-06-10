from typing import Optional
from fastapi import FastAPI
from pydantic import BaseSettings
from pydantic.main import BaseModel

class Settings(BaseSettings):
    app_name: str = "Awesome API"
    counter :int =0

app = FastAPI()

settings = Settings()


class ResponseModel(BaseModel):
    canyoukit: bool

@app.get("/wind_server")
async def root(lat: Optional[str] = None, lon: Optional[str] = None) -> bool:

    if settings.counter < 20:
        settings.counter += 1 
        print(settings.counter)
        return False
    else:
        settings.counter = 0
        print(settings.counter)
        return True

