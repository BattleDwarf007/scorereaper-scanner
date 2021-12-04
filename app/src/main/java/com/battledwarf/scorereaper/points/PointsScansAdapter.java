package com.battledwarf.scorereaper.points;

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

public class PointsScansAdapter extends ArrayAdapter<points> {

    //storing all the names in the list
    private final List<points> names;

    //context object
    private final Context context;

    //constructor
    public PointsScansAdapter(Context context, int resource, List<points> names) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.lv_points, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.textViewCarNumber);
        TextView textViewTime = listViewItem.findViewById(R.id.textViewDriverName);
        TextView textViewPoints = listViewItem.findViewById(R.id.textViewPoints);
        ImageView imageViewStatus = listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name
        points name = names.get(position);

        //setting the name to textview
        textViewName.setText(name.getName());

        //setting the time to textview
        textViewTime.setText(name.getTime());

        //setting the time to textview
        textViewPoints.setText(name.getPoints());

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