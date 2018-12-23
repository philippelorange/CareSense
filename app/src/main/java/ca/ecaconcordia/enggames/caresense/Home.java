package ca.ecaconcordia.enggames.caresense;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        getValues(view);

        addButton(view, R.id.sensorOne, "sensorOne");
        addButton(view, R.id.sensorTwo, "sensorTwo");
        addButton(view, R.id.sensorThree, "sensorThree");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void addButton(final View view, int sensorId, final String controllerSensorId) {
        Button sensor = (Button) view.findViewById(sensorId);
        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorController.triggerSensor(new Date(), controllerSensorId);
                getValues(view);
            }
        });
    }

    private void getValues(View view) {
        locations.clear();
        timestamps.clear();
        Stack<ActiveRoomInformation> activityStack = sensorController.getActivities();
        for (ActiveRoomInformation activeRoomInformation : activityStack) {
            DateFormat format = new SimpleDateFormat("hh:mm  yyyy-MM-dd");
            locations.add(activeRoomInformation.getRoom());
            timestamps.add(format.format(activeRoomInformation.getTimestamp()));
        }
        Collections.reverse(locations);
        Collections.reverse(timestamps);
        refreshRecyclerView(view);
    }

    private void refreshRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.actionList);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(locations, timestamps, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}