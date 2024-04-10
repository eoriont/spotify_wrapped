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



CLIENT_SECRET = '25267d8d8dc5434b9f1d229b86fba5b1'
CLIENT_ID = '57630b7e960946ab83c1e0dbda46a4ca'

client_credentials_manager = SpotifyClientCredentials(client_id=CLIENT_ID, client_secret=CLIENT_SECRET)
sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

def main():

    url = ''
    song = ''
    tracks = []

    for i in range(0, 100):
        #track = sys.stdin.readline().strip()
        track = 'Hello'
        t = sp.search(q=track, type='track')
        if len(t['tracks']['items']) > 0:
            tk = t['tracks']['items'][0]
            preview_url = tk.get('preview_url')
            if preview_url:
                url = preview_url
                song = track
                break


    def lowpass(y):
        nyquist = 11500
        normalized = 5000 / nyquist

        b, a = butter(4, normalized, btype='low')

        y = filtfilt(b, a, y)
        y = np.asarray(y, dtype=np.float32)
        y /= np.max(np.abs(y))

        return y

    def features(url):
        response = requests.get(url)
        audio_data = io.BytesIO(response.content)

        audio = AudioSegment.from_file(audio_data)
        audio = np.array(audio.get_array_of_samples())

        y = audio.astype(np.float32) / np.iinfo(audio.dtype).max
        sr = 22100
        y = lowpass(y)
        y = y[:154700]

        onsets = librosa.onset.onset_detect(y=y, sr=sr)
        onsets = len(onsets)

        centroid = librosa.feature.spectral_centroid(y=y, sr=sr)[0]
        centroid_min = int(np.min(centroid))
        centroid_max = int(np.max(centroid))
        centroid_mean = int(np.mean(centroid))

        mfcc = librosa.feature.mfcc(y=y, sr=sr)
        mfcc = mfcc.flatten()
        mfcc_min = int(np.min(mfcc))
        mfcc_max = int(np.max(mfcc))
        mfcc_mean = int(np.mean(mfcc))

        zero_crossings = librosa.feature.zero_crossing_rate(y)
        zcr_min = int(np.min(zero_crossings))
        zcr_max = int(np.max(zero_crossings))
        zcr_mean = int(np.mean(zero_crossings))

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
            "zero_crossing_rate_mean": zcr_mean
        }
        return features

    def compare():
        x = sys.stdin.readline().strip()
        sys.stdout.write(str(x) + "\n")
        sys.stdout.flush()
        data = extractions2.my_feautres
        sf = features(url)
        sflist = []
        for key, value in sf.items():
            sflist.append(value)
        print(sflist)

        finalval = 20000
        finalsong = ''

        for key, value in data.items():
            ftlist = []
            if type(value) != str:
                for ft, val in value.items():
                    ftlist.append(val)
            ad = np.abs(np.array(sflist) - np.array(ftlist))
            mean = np.mean(ad)
            if mean < finalval:
                finalval = mean
                finalsong = key

        return finalsong


    result = "Matched: " + song + "-> " + found
    compare()

if __name__ == "__main__":
    main()