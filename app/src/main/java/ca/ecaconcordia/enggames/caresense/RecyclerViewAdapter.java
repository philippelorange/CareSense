package ca.ecaconcordia.enggames.caresense;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> timestamps = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> locations, ArrayList<String> timestamps, Context mContext) {
        this.locations = locations;
        this.timestamps = timestamps;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.location.setText(locations.get(i));
        holder.timestamp.setText(timestamps.get(i));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView location;
        TextView timestamp;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location_text);
            timestamp = itemView.findViewById(R.id.timestamp_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
