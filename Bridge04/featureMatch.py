import numpy as np
import librosa
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
import json
import csv

CLIENT_SECRET = '25267d8d8dc5434b9f1d229b86fba5b1'
CLIENT_ID = '57630b7e960946ab83c1e0dbda46a4ca'

client_credentials_manager = SpotifyClientCredentials(client_id=CLIENT_ID, client_secret=CLIENT_SECRET)
sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

def add2():
    with open('C:/Users/colby/Desktop/O&D/spotify_wrapped/SpotifyMIR/muse_v3.csv', newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        counter = 0
        for i in reader:
            spid = i.get("spotify_id")
            if spid:
                track = sp.track(spid)
                prev = track['preview_url']
                song = track['name']
                if prev != None:
                    counter += 1
                    new = {}
                    new[song] = prev
                    path = 'C:/Users/colby/Desktop/O&D/spotify_wrapped/SpotifyMIR/preview_urls1'

                    #with open(path, 'r') as file:
                        #existing_data = json.load(file)

                    #existing_data.update(new)

                    #with open(path, 'w') as file:
                        #json.dump(existing_data, file, indent=4)
                    print('added ' + song)

add2()

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



