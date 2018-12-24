package ca.ecaconcordia.enggames.caresense.backend.api;

import java.util.List;

import ca.ecaconcordia.enggames.caresense.common.ActiveRoomInformation;
import ca.ecaconcordia.enggames.caresense.common.Sensor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("api/getLastRooms")
    Call<List<ActiveRoomInformation>> getLatestActivity();

    @POST("api/sensorUpdate")
    Call<Sensor> sensorUpdate(@Body Sensor sensor);

}
