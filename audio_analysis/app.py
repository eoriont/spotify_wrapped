from flask import Flask, request
import test02

app = Flask(__name__)

@app.route('/')
def hello_world():
    try:
        # Retrieve the 'data' parameter from the query string
        tracks = request.args.getlist('data')

        # Call the getResult function from test02 module and pass the tracks list
        result = test02.getResult(tracks)

        return result

    except Exception as e:

        return "No Input"

if __name__ == '__main__':
    app.run()


