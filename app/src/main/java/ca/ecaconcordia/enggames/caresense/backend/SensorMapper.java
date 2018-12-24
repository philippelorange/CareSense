package ca.ecaconcordia.enggames.caresense.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ecaconcordia.enggames.caresense.backend.api.ApiService;
import ca.ecaconcordia.enggames.caresense.common.ActiveRoomInformation;
import ca.ecaconcordia.enggames.caresense.common.Sensor;
import ca.ecaconcordia.enggames.caresense.common.SensorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SensorMapper {

    private static SensorMapper instance = null;

    private List<ActiveRoomInformation> sensorActivityStack = new ArrayList<>();
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

    }

    public List<ActiveRoomInformation> getSensorActivityStack() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.110:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<ActiveRoomInformation>> call = apiService.getLatestActivity();
        call.enqueue(new Callback<List<ActiveRoomInformation>>() {
            @Override
            public void onResponse(Call<List<ActiveRoomInformation>> call, Response<List<ActiveRoomInformation>> response) {
                for (ActiveRoomInformation activeRoomInformation : response.body()) {
                    sensorActivityStack.add(new ActiveRoomInformation(activeRoomInformation
                            .getRoom(), activeRoomInformation.getTimestamp()));
                }
            }

            @Override
            public void onFailure(Call<List<ActiveRoomInformation>> call, Throwable t) {
                System.err.println("Failure: " + t.getMessage());
            }
        });
        return sensorActivityStack;
    }

    public static SensorMapper getInstance() {
        if (instance == null) {
            instance = new SensorMapper();
        } else {
            System.err.println("Cannot instantiate singleton SensorMapper twice");
        }
        return instance;
    }

}
