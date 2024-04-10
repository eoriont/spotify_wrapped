#imports
import sys
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
import librosa
import io
import requests
from pydub import AudioSegment
from scipy.signal import butter, filtfilt
import numpy as np
import extractions2

#Spotify Authentication
CLIENT_SECRET = '25267d8d8dc5434b9f1d229b86fba5b1'
CLIENT_ID = '57630b7e960946ab83c1e0dbda46a4ca'
client_credentials_manager = SpotifyClientCredentials(client_id=CLIENT_ID, client_secret=CLIENT_SECRET)
sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

#Placeholder values
url = ''
song = ''

#Searches for first track with preview url available and saves url
def getURL(tracks):
    for i in range(500):
        #track = sys.stdin.readline().strip()
        track = tracks[i]
        t = sp.search(q=track, type='track')
        if len(t['tracks']['items']) > 0:
            tk = t['tracks']['items'][0]
            preview_url = tk.get('preview_url')
            if preview_url:
                url = preview_url
                song = track
                return url, song
                break

#Lowpass filter for better feature accuracy
def lowpass(y):
    nyquist = 11500
    normalized = 5000 / nyquist
    b, a = butter(4, normalized, btype='low')
    y = filtfilt(b, a, y)
    y = np.asarray(y, dtype=np.float32)
    y /= np.max(np.abs(y))

    return y

#Feature extraction suite for chosen track
def features(url):
    #Reading in url
    response = requests.get(url)
    audio_data = io.BytesIO(response.content)
    audio = AudioSegment.from_file(audio_data)
    audio = np.array(audio.get_array_of_samples())
    y = audio.astype(np.float32) / np.iinfo(audio.dtype).max
    sr = 22100

    #Lowpass application and trimming
    y = lowpass(y)
    y = y[:154700]

    onsets = librosa.onset.onset_detect(y=y, sr=sr)
    onsets = str(len(onsets))

    centroid = librosa.feature.spectral_centroid(y=y, sr=sr)[0]
    centroid_min = str(np.min(centroid))
    centroid_max = str(np.max(centroid))
    centroid_mean = str(np.mean(centroid))

    mfcc = librosa.feature.mfcc(y=y, sr=sr)
    mfcc = mfcc.flatten()
    mfcc_min = str(np.min(mfcc))
    mfcc_max = str(np.max(mfcc))
    mfcc_mean = str(np.mean(mfcc))

    zero_crossings = librosa.feature.zero_crossing_rate(y)
    zcr_min = str(np.min(zero_crossings))
    zcr_max = str(np.max(zero_crossings))
    zcr_mean = str(np.mean(zero_crossings))

    flatness = librosa.feature.spectral_flatness(y=y)
    flatness = flatness.flatten()
    flatness_min = str(np.min(flatness))
    flatness_max = str(np.max(flatness))
    flatness_mean = str(np.mean(flatness))

    rms = librosa.feature.rms(y=y)
    rms = rms.flatten()
    rms_min = str(np.min(rms))
    rms_max = str(np.max(rms))
    rms_mean = str(np.mean(rms))

    features = {
        "Onsets": onsets,
        "centroid_min": centroid_min,
        "centroid_max": centroid_max,
        "centroid_mean": centroid_mean,
        "mfcc_min": mfcc_min,
        "mfcc_max": mfcc_max,
        "mfcc_mean": mfcc_mean,
        "zero_crossing_rate_min": zcr_min,
        "zero_crossing_rate_max": zcr_max,
        "zero_crossing_rate_mean": zcr_mean,
        "flatness_min": flatness_min,
        "flatness_max": flatness_max,
        "flatness_mean": flatness_mean,
        "rms_min": rms_min,
        "rms_max": rms_max,
        "rms_mean": rms_mean
    }
    return features

#Search for closest matched track
def compare(url):
    #Python file containing songs and features
    data = extractions2.my_features

    #Input song features
    sf = features(url)
    sflist = []
    #Storing as list of floats for easy comparison
    for key, value in sf.items():
        sflist.append(float(value))
    #Placeholders
    finalval = 20000
    finalsong = ''

    #Search and store closest match
    for key, value in data.items():
        ftlist = []
        for ft, val in value.items():
            ftlist.append(float(val))

        ad = np.abs(np.array(sflist) - np.array(ftlist))
        mean = np.mean(ad)
        if mean < finalval:
            finalval = mean
            finalsong = key

    return finalsong

#Storing matched track title

def getResult(tracks):

    url, song = getURL(tracks)

    found = compare(url)
    inart = ''
    outart = ''

    #Artist names for input and output tracks
    t = sp.search(q=song, type='track')
    if len(t['tracks']['items']) > 0:
        tk = t['tracks']['items'][0]
        arts = tk['artists']
        for artist in arts:
            inart += artist['name']
    else:
        inart = 'uhh'
    t = sp.search(q=found, type='track')
    if len(t['tracks']['items']) > 0:
        tk = t['tracks']['items'][0]
        arts = tk['artists']
        for artist in arts:
            outart += artist['name']
    else:
        outart = 'uhhh'

    #Output
    result = "Matched: " + song + " by " + inart + "-> " + found + " by " + outart
    return result

tracks = ['Hello', 'Baby']
getResult(tracks)




