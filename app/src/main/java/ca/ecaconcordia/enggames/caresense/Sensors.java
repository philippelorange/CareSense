package ca.ecaconcordia.enggames.caresense;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class Sensors extends Fragment {
    private AppCompatSpinner spinner1;
    private AppCompatSpinner spinner2;
    public static Sensors newInstance() {
        Sensors fragment = new Sensors();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_sensors, container, false);
        //populate spinners
        spinner1 = (AppCompatSpinner)view.findViewById(R.id.rooms1);
        spinner2 = (AppCompatSpinner)view.findViewById(R.id.rooms2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.rooms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        return view;
    }
}