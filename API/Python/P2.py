from flask import Flask, jsonify, request, redirect, render_template
import re
app = Flask(__name__)


def verify(uid):
    pattern = r"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
    if re.match(pattern, uid):
        return True
    return False


def redirect_home():
    return redirect('/login')


@app.route("/", methods=['GET'])
def home():
    return redirect_home()


@app.route("/login", methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        data = request.get_json()
        if (verify(data['uid'])):
            # passvalidation should be done here
            return redirect("/home")
            # return jsonify({"status": "Email Validation Sucess"})
        else:
            return jsonify({"status": "Email Validation Failed"})
    return jsonify({"status": "Method is not post at login"})


@app.route("/home", methods=['GET', 'POST'])
def home_page():
    if request.method == 'POST':
        return jsonify({"status": "Request Method POST from Home Page"})
    elif request.method == 'GET':
        return jsonify({"status": "Request Method GET from Home Page"})
    else:
        return jsonify({"status": "Request Method Not POssible"})


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
