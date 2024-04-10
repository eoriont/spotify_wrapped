import numpy as np
import librosa
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
import json

CLIENT_SECRET = '25267d8d8dc5434b9f1d229b86fba5b1'
CLIENT_ID = '57630b7e960946ab83c1e0dbda46a4ca'

client_credentials_manager = SpotifyClientCredentials(client_id=CLIENT_ID, client_secret=CLIENT_SECRET)
sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

def add(playlist):

    playlist = sp.playlist(playlist)
    tracks = playlist['tracks']['items']

    track_urls = {}
    for track in tracks:
        url = track['track'].get('preview_url')
        if url != None:
            name = track['track']['name']
            track_urls[name] = url
    path = 'preview_urls1'

    existing_data = {}
    with open(path, 'r') as file:
        existing_data = json.load(file)

    existing_data.update(track_urls)

    with open(path, 'w') as file:
        json.dump(existing_data, file, indent=4)


playlist = 'https://open.spotify.com/playlist/0M7pfgi1ldYOdIMli7bFki?si=36e5cb5c0059452c'
add(playlist)
