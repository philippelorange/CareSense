from flask import Flask, request, jsonify
from flaskext.mysql import MySQL
import json

from Sensor import Sensor

app = Flask(__name__)
mysql = MySQL()

# MySQL configurations


app.config['MYSQL_DATABASE_USER'] = 'b1070249d11520'
app.config['MYSQL_DATABASE_PASSWORD'] = 'd6f231fe'
app.config['MYSQL_DATABASE_DB'] = 'heroku_102789b37bf7053'
app.config['MYSQL_DATABASE_HOST'] = 'us-cdbr-iron-east-01.cleardb.net'
mysql.init_app(app)

sensorOne = Sensor(1, "Living Room", "Bathroom")
sensorTwo = Sensor(2, "Living Room", "Bedroom")
sensorThree = Sensor(3, "Living Room", "Kitchen")

sensors = {
    sensorOne.id: sensorOne,
    sensorTwo.id: sensorTwo,
    sensorThree.id: sensorThree
}

currentRoom = "Living Room"

conn = mysql.connect()


# root
@app.route("/")
def index():
    return "CareSense index page"


# GET
@app.route('/api/getLastRooms', methods=['GET'])
def get_latest_activity():
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM `current_room`")
    row_headers = [x[0] for x in cursor.description]  # this will extract row headers
    rv = cursor.fetchall()
    json_data = []
    for result in rv:
        json_data.append(dict(zip(row_headers, result)))
    cursor.close()
    return json.dumps(json_data, default=str)


# POST
@app.route('/api/sensorUpdate', methods=['POST'])
def sensor_update():
    cursor = conn.cursor()

    global currentRoom
    json = request.get_json()

    sensor_id = json["sensor_id"]
    cursor.execute(
        "INSERT INTO `sensor_activity` VALUES (NULL, CURRENT_TIME(), \'%s\')", sensor_id)
    conn.commit()

    if currentRoom == sensors[sensor_id].roomOne:
        currentRoom = sensors[sensor_id].roomTwo
    elif currentRoom == sensors[sensor_id].roomTwo:
            currentRoom = sensors[sensor_id].roomOne
    elif currentRoom != sensors[sensor_id].roomTwo and currentRoom != sensors[sensor_id].roomOne:
        currentRoom = "Living Room"

    cursor.execute(
        "INSERT INTO `current_room`(`id`, `current_room`, `timestamp`) VALUES(NULL, %s, CURRENT_TIME())", currentRoom)
    conn.commit()
    cursor.close()
    return jsonify({'you sent this': sensor_id})


# running web app in local machine
if __name__ == '__main__':
    app.run(host='192.168.0.112', port=5000)
