package com.example.petercockcroft.gwob_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReportAdapter extends ArrayAdapter<Object> {

    public ReportAdapter(Context context, List<Object> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.report_card, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text);
        Object obj = getItem(position);
        textView.setText(obj.toString());
        return rowView;
    }
}
