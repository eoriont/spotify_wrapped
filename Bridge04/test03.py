import sys
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
import json
import extractions2

CLIENT_SECRET = '25267d8d8dc5434b9f1d229b86fba5b1'
CLIENT_ID = '57630b7e960946ab83c1e0dbda46a4ca'

client_credentials_manager = SpotifyClientCredentials(client_id=CLIENT_ID, client_secret=CLIENT_SECRET)
sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)
def main():
    data = extractions2.my_feautres

    x = sys.stdin.readline().strip()
    sys.stdout.write(str(x) + "\n")
    sys.stdout.flush()

if __name__ == "__main__":
    main()

