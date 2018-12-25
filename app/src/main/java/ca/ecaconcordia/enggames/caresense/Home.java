package ca.ecaconcordia.enggames.caresense;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    private ArrayList<ActiveRoomInformation> recentLocations = new ArrayList<>();


    private SensorController sensorController = SensorController.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

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
        //Button sensor = view.findViewById(buttonId);
        FloatingActionButton sensor = view.findViewById(buttonId);
        sensor.setOnClickListener(v -> getValues(view));
    }


    private void getValues(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.112:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<ActiveRoomInformation>> call = apiService.getLatestActivity();

        call.enqueue(new Callback<List<ActiveRoomInformation>>() {
            @Override
            public void onResponse(Call<List<ActiveRoomInformation>> call, Response<List<ActiveRoomInformation>> response) {
                recentLocations.clear();
                for (ActiveRoomInformation activeRoomInformation : response.body()) {
                    recentLocations.add(new ActiveRoomInformation(activeRoomInformation
                            .getRoom(), activeRoomInformation.getTimestamp()));
                    Collections.sort(recentLocations, (o1, o2) -> o2.getTimestamp().compareTo(o1.getTimestamp()));
                    refreshRecyclerView(view);
                }
            }

            @Override
            public void onFailure(Call<List<ActiveRoomInformation>> call, Throwable t) {
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
}