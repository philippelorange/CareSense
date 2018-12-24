package ca.ecaconcordia.enggames.caresense.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sensor {
    @SerializedName("sensor_id")
    @Expose
    private int sensorId;

    public Sensor(int sensorId) {
        this.sensorId = sensorId;
    }
}
