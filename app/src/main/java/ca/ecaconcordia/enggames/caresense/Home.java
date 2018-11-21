package ca.ecaconcordia.enggames.caresense;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Home extends Fragment {
    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    //vars
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> timestamps = new ArrayList<>();


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
        locations.add("Living Room");
        locations.add("Bathroom");
        locations.add("Living Room");
        locations.add("Kitchen");
        locations.add("Living Room");
        locations.add("Bedroom");
        locations.add("Living Room");
        locations.add("Bathroom");
        locations.add("Living Room");
        locations.add("Kitchen");
        locations.add("Living Room");
        locations.add("Bedroom");

        timestamps.add("4:20");
        timestamps.add("4:21");
        timestamps.add("4:22");
        timestamps.add("4:23");
        timestamps.add("4:24");
        timestamps.add("4:25");
        timestamps.add("4:20");
        timestamps.add("4:21");
        timestamps.add("4:22");
        timestamps.add("4:23");
        timestamps.add("4:24");
        timestamps.add("4:25");

        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.actionList);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(locations, timestamps, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}