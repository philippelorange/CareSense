package ca.ecaconcordia.enggames.caresense.backend;

import android.support.annotation.NonNull;

import ca.ecaconcordia.enggames.caresense.backend.api.ApiService;
import ca.ecaconcordia.enggames.caresense.common.Sensor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class SensorMapper {

    private static SensorMapper instance = null;

    private SensorMapper() {

    }

    void sensorTriggered(Sensor sensor) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://care-sense.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Sensor> call = apiService.sensorUpdate(sensor);
        call.enqueue(new Callback<Sensor>() {
            @Override
            public void onResponse(@NonNull Call<Sensor> call, @NonNull Response<Sensor> response) {
                System.out.println("Success!");
            }

            @Override
            public void onFailure(Call<Sensor> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    static SensorMapper getInstance() {
        if (instance == null) {
            instance = new SensorMapper();
        } else {
            System.err.println("Cannot instantiate singleton SensorMapper twice");
        }
        return instance;
    }


}
