import numpy as np
import librosa
import librosa.feature
import io
import requests
from pydub import AudioSegment
import json
from scipy.signal import butter, filtfilt

def lowpass(y):

    nyquist = 11500
    normalized = 5000 / nyquist

    b, a = butter(4, normalized, btype='low')

    y = filtfilt(b, a, y)
    y = np.asarray(y, dtype=np.float32)
    y /= np.max(np.abs(y))

    return y

def features():
    path = 'preview_urls1'
    path2 = 'extraction8'
    path3 = 'test1'

    data = {}

    with open(path, 'r') as file:
        data = json.load(file)
    i = 0
    for track, url in data.items():
        response = requests.get(url)
        audio_data = io.BytesIO(response.content)

        audio = AudioSegment.from_file(audio_data)
        audio = np.array(audio.get_array_of_samples())

        y = audio.astype(np.float32) / np.iinfo(audio.dtype).max
        sr = 22100
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
        data[track] = features
        print(i, track)
        i += 1
        with open(path2, 'w') as file:
            json.dump(data, file, indent=4)


features()