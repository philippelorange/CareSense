package ca.ecaconcordia.enggames.caresense.backend;

import ca.ecaconcordia.enggames.caresense.common.Sensor;

public class SensorController {

    private static SensorController instance = null;

    private SensorMapper sensorMapper;

    private SensorController() {
        sensorMapper = SensorMapper.getInstance();
    }

    public void triggerSensor(int sensorId) {
        sensorMapper.sensorTriggered(new Sensor(sensorId));
    }

    public static SensorController getInstance() {
        if (instance == null) {
            instance = new SensorController();
        } else {
            System.err.println("Cannot instantiate singleton SensorController twice");
        }
        return instance;
    }

}
