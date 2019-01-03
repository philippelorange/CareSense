package ca.ecaconcordia.enggames.caresense;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ca.ecaconcordia.enggames.caresense.common.ActiveRoomInformation;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<ActiveRoomInformation> activities;

    public RecyclerViewAdapter(ArrayList<ActiveRoomInformation> activities, Context mContext) {
        this.activities = activities;
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
            holder.location.setText(activities.get(i).getRoom().getRoom());
            Date date = activities.get(i).getTimestamp();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM DD kk:mm");

            holder.timestamp.setText(sdf.format(date));
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView location;
        TextView timestamp;
        RelativeLayout parentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location_text);
            timestamp = itemView.findViewById(R.id.timestamp_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
