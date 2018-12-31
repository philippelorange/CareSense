package ca.ecaconcordia.enggames.caresense.common;

import java.util.Date;

public class SensorActivity {
    private int sensorId;
    private Date timestamp;

    public SensorActivity(int sensorId, Date timestamp) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
    }

    public SensorActivity() {
    }

    public int getSensorId() {
        return sensorId;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
