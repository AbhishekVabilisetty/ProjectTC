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
            return jsonify({"status": "Email Validation Sucess"})
        else:
            return jsonify({"status": "Email Validation Failed"})
    return jsonify({"status": "Method is not post"})


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
