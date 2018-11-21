package ca.ecaconcordia.enggames.caresense.common;

import java.util.Date;

public class SensorActivity {

    private Date timestamp;
    private String sensorId;

    public SensorActivity(Date timestamp, String sensorId) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
    }

    public String getSensorId() {
        return sensorId;
    }


    public Date getTimestamp() {
        return timestamp;
    }


}
