from flask import Flask, jsonify, request, redirect, render_template

app = Flask(__name__)


@app.route('/hello', methods=['GET'])
def get_data():
    return jsonify({"message": "Hello from the backend!"})


@app.route("/login", methods=['GET', 'POST'])
def login():
    return render_template('index.html')


@app.route("/", methods=['GET'])
def route():
    return redirect("/login")


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
