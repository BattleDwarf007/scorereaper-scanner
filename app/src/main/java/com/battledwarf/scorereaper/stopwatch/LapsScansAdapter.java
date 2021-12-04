package com.battledwarf.scorereaper.stopwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.battledwarf.scorereaper.util.Constants;
import com.battledwarf.scorereaper.R;

import java.util.List;

import static com.battledwarf.scorereaper.util.StringUtils.getFormattedTime;

public class LapsScansAdapter extends ArrayAdapter<laps> {

    //storing all the names in the list
    private final List<laps> names;

    //context object
    private final Context context;

    //constructor
    public LapsScansAdapter(Context context, int resource, List<laps> names) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        @SuppressLint("InflateParams") View listViewItem = inflater.inflate(R.layout.lv_laps, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.textViewCarNumber);
        TextView textViewTime = listViewItem.findViewById(R.id.textViewDriverName);
        ImageView imageViewStatus = listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name
        laps name = names.get(position);

        //setting the name to textview  String.format("Version: %s", name.getCar())
        textViewName.setText(String.format("CAR: %s", name.getCar()));

        //setting the time to textview
        textViewTime.setText(String.format("Lap time: %s", getFormattedTime(name.getlapTime())));


        //if the synced status is 0 displaying
        //queued icon
        //else displaying synced icon
        if (name.getStatus() == Constants.NOT_SYNCED)
            imageViewStatus.setBackgroundResource(R.drawable.outline_pending_black_24);
        else if (name.getStatus() == Constants.SYNC_ERROR){
            imageViewStatus.setBackgroundResource(R.drawable.outline_error_outline_black_24);
        }
        else
            imageViewStatus.setBackgroundResource(R.drawable.outline_check_circle_black_24);

        return listViewItem;
    }
}