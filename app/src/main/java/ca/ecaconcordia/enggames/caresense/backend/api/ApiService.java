package ca.ecaconcordia.enggames.caresense.backend.api;

import java.util.List;

import ca.ecaconcordia.enggames.caresense.common.ActiveRoomInformation;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api/getLastRooms")
    Call<List<ActiveRoomInformation>> getLatestActivity();

}
