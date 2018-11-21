package ca.ecaconcordia.enggames.caresense.backend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import ca.ecaconcordia.enggames.caresense.common.ActiveRoomInformation;
import ca.ecaconcordia.enggames.caresense.common.Sensor;
import ca.ecaconcordia.enggames.caresense.common.SensorActivity;

public class SensorMapper {

    private static SensorMapper instance = null;

    private Stack<ActiveRoomInformation> sensorActivityStack = new Stack<>();
    private String currentRoom;
    private Map<String, Sensor> sensors = new HashMap<>();

    private SensorMapper() {
        currentRoom = "Living Room";

        sensors.put("sensorOne", new Sensor("Living Room", "Bathroom"));
        sensors.put("sensorTwo", new Sensor("Living Room", "Bedroom"));
        sensors.put("sensorThree", new Sensor("Living Room", "Kitchen"));
        sensors.put("sensorFour", new Sensor("Kitchen", "Bathroom"));

    }

    public void sensorTriggered(SensorActivity activity) {

        Sensor sensor = sensors.get(activity.getSensorId());
        if (sensor.getRoomOne().equals(currentRoom)) {
            currentRoom = sensor.getRoomTwo();
        } else {
            currentRoom = sensor.getRoomOne();
        }

        sensorActivityStack.push(new ActiveRoomInformation(currentRoom, activity.getTimestamp()));
    }

    public Stack<ActiveRoomInformation> getSensorActivityStack() {
        return sensorActivityStack;
    }

    public static SensorMapper getInstance() {
        if(instance == null) {
            instance = new SensorMapper();
        }
        else {
            System.err.println("Cannot instantiate singleton SensorMapper twice");
        }
        return instance;
    }

}
