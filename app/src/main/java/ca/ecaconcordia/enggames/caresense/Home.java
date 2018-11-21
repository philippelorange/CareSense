package ca.ecaconcordia.enggames.caresense;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;

import ca.ecaconcordia.enggames.caresense.backend.SensorController;
import ca.ecaconcordia.enggames.caresense.common.ActiveRoomInformation;

public class Home extends Fragment {

    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    //vars
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> timestamps = new ArrayList<>();

    private SensorController sensorController = SensorController.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initTestValues(rootView);

        return rootView;
    }

    private void initTestValues(View view) {
        Stack<ActiveRoomInformation> activityStack = sensorController.getActivities();
        for (ActiveRoomInformation activeRoomInformation : activityStack) {
            DateFormat format = new SimpleDateFormat("hh:mm  yyyy-MM-dd");

            locations.add(activeRoomInformation.getRoom());
            timestamps.add(format.format(activeRoomInformation.getTimestamp()));
        }

        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.actionList);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(locations, timestamps, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}