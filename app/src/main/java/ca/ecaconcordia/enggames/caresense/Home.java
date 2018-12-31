package ca.ecaconcordia.enggames.caresense;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

import java.util.ArrayList;
import java.util.Date;

import ca.ecaconcordia.enggames.caresense.common.ActiveRoomInformation;
import ca.ecaconcordia.enggames.caresense.common.Room;
import ca.ecaconcordia.enggames.caresense.common.Sensor;


public class Home extends Fragment {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private Sensor sensorOne = new Sensor(Room.LIVING_ROOM, Room.BATHROOM);
    private Sensor sensorTwo = new Sensor(Room.LIVING_ROOM, Room.BEDROOM);
    private Sensor sensorThree = new Sensor(Room.LIVING_ROOM, Room.KITCHEN);

    private static Room currentRoom = Room.LIVING_ROOM;

    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }


    private ArrayList<ActiveRoomInformation> recentLocations = new ArrayList<>();
    private NotificationManagerCompat notificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        notificationManager = NotificationManagerCompat.from(this.getContext());

        addButton(view, R.id.sensorOne, sensorOne);
        addButton(view, R.id.sensorTwo, sensorTwo);
        addButton(view, R.id.sensorThree, sensorThree);

        mDatabase.child("sensorActivity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recentLocations.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    recentLocations.add(new ActiveRoomInformation(postSnapshot.child("room").getValue(Room.class),
                            postSnapshot.child("timestamp").getValue(Date.class)));

                }
                refreshRecyclerView(view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void addButton(final View view, int sensorId, Sensor sensor) {
        Button sensorButton = view.findViewById(sensorId);
        sensorButton.setOnClickListener(v -> {
            if (currentRoom.equals(sensor.getRoomOne())) {
                currentRoom = sensor.getRoomTwo();
            } else if (currentRoom.equals(sensor.getRoomTwo())) {
                currentRoom = sensor.getRoomOne();
            }

            mDatabase.child("sensorActivity").push().setValue(new ActiveRoomInformation(currentRoom, new Date()));

        });
    }

    private void refreshRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.actionList);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(recentLocations, getActivity());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void sendOnChannel1(View v) {
        String CHANNEL_ID = "CHANNEL_ONE";
        Notification notification = new NotificationCompat.Builder(v.getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(recentLocations.get(0).getRoom().name())
                .setContentText(recentLocations.get(0).getTimestamp().toString())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }
}