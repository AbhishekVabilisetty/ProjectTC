from flask import Flask, jsonify, request

app = Flask(__name__)

@app.route('/api/data', methods=['GET'])
def get_data():
    return jsonify({"message": "Hello from the backend!"})

@app.route('/api/index',methods=['POST'])
def hw():
    return "Hello World!!"

@app.route('/api/index',methods=['POST'])
def Details():
    pass

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
