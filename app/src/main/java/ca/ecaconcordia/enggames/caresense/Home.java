package ca.ecaconcordia.enggames.caresense;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;

import com.github.nkzawa.socketio.client.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.ecaconcordia.enggames.caresense.backend.SensorController;
import ca.ecaconcordia.enggames.caresense.backend.api.ApiService;
import ca.ecaconcordia.enggames.caresense.common.ActiveRoomInformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Home extends Fragment {
    private String CHANNEL_ID = "CHANNEL_ONE";

    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }
    public Socket mSocket;
    {
       try {
            mSocket = IO.socket("http://care-sense.herokuapp.com");// secret code carsesense69
       } catch (URISyntaxException e) {}
    }

    private ArrayList<ActiveRoomInformation> recentLocations = new ArrayList<>();
    private SensorController sensorController = SensorController.getInstance();
    private NotificationManagerCompat notificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mSocket.open();
//        mSocket.on("EVENT_NAME", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                getValues(getView());
//            }
//        });
    }
    @Override
    public void onDestroy() {
       // mSocket.close();
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        notificationManager = NotificationManagerCompat.from(this.getContext());

        addButton(view, R.id.sensorOne, 1);
        addButton(view, R.id.sensorTwo, 2);
        addButton(view, R.id.sensorThree, 3);

        addRefreshButton(view, R.id.refresh);

        getValues(view);
        return view;
    }

    private void addButton(final View view, int sensorId, final int controllerSensorId) {
        Button sensor = view.findViewById(sensorId);
        sensor.setOnClickListener(v -> sensorController.triggerSensor(controllerSensorId));
    }

    private void addRefreshButton(final View view, int buttonId) {
        FloatingActionButton sensor = view.findViewById(buttonId);
        sensor.setOnClickListener(v ->
        {
            getValues(view);
        });
    }


    private void getValues(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://care-sense.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<ActiveRoomInformation>> call = apiService.getLatestActivity();

        call.enqueue(new Callback<List<ActiveRoomInformation>>() {
            @Override
            public void onResponse(@NonNull Call<List<ActiveRoomInformation>> call, @NonNull Response<List<ActiveRoomInformation>> response) {
                recentLocations.clear();
                assert response.body() != null;
                for (ActiveRoomInformation activeRoomInformation : response.body()) {
                    recentLocations.add(new ActiveRoomInformation(activeRoomInformation
                            .getRoom(), activeRoomInformation.getTimestamp()));
                    Collections.sort(recentLocations, (o1, o2) -> o2.getTimestamp().compareTo(o1.getTimestamp()));
                    refreshRecyclerView(view);
                    //sendOnChannel1(view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ActiveRoomInformation>> call, @NonNull Throwable t) {
                System.err.println("Failure: " + t.getMessage());
            }
        });
        System.err.println("SIZE : " + recentLocations.size());
    }

    private void refreshRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.actionList);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(recentLocations, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    public void sendOnChannel1(View v) {
        Notification notification = new NotificationCompat.Builder(v.getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(recentLocations.get(0).getRoom())
                .setContentText(recentLocations.get(0).getTimestamp())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }
}