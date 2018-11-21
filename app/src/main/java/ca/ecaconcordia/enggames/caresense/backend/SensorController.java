package ca.ecaconcordia.enggames.caresense.backend;

import java.util.Date;
import java.util.Stack;

import ca.ecaconcordia.enggames.caresense.common.ActiveRoomInformation;
import ca.ecaconcordia.enggames.caresense.common.SensorActivity;

public class SensorController {

    private static SensorController instance = null;

    private SensorMapper sensorMapper;

    private SensorController() {
        sensorMapper = SensorMapper.getInstance();
    }

    public void triggerSensor(Date timestamp, String sensorId) {
        sensorMapper.sensorTriggered(new SensorActivity(timestamp, sensorId));
    }

    public Stack<ActiveRoomInformation> getActivities() {
        return sensorMapper.getSensorActivityStack();
    }

    public static SensorController getInstance() {
        if(instance == null) {
            instance = new SensorController();
        }
        else {
            System.err.println("Cannot instantiate singleton SensorController twice");
        }
        return instance;
    }

}
