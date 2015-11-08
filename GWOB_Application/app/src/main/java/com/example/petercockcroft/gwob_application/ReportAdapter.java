package com.example.petercockcroft.gwob_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.petercockcroft.gwob_application.storage.HarvestObject;
import com.example.petercockcroft.gwob_application.storage.InspectionObject;

import java.util.List;

public class ReportAdapter extends ArrayAdapter<Object> {

    public ReportAdapter(Context context, List<Object> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.report_card, parent, false);
        TextView left = (TextView) rowView.findViewById(R.id.left);
        TextView right = (TextView) rowView.findViewById(R.id.right);
        Object obj = getItem(position);
        String text = "";
        if (obj instanceof HarvestObject) text+= getContext().getResources().getString(R.string.harvest);
        if (obj instanceof InspectionObject) text+= getContext().getResources().getString(R.string.inspection);
        left.setText(text);
        right.setText("                                     A few minutes ago");
        return rowView;
    }
}
