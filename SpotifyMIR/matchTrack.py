import numpy as np
import librosa
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
import json
import sys

def add_numbers(num1, num2):
    return num1 + num2

# Read input arguments from Java
num1 = int(sys.stdin.readline().strip())
num2 = int(sys.stdin.readline().strip())

# Call the Python function and return the result to Java
result = add_numbers(num1, num2)
print(result)
sys.stdout.flush()