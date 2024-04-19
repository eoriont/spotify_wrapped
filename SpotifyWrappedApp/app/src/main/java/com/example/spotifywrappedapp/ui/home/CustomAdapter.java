package com.example.spotifywrappedapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.spotifywrappedapp.R;
import com.example.spotifywrappedapp.models.History;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<History> {
    private Context context;
    private List<History> values;

    private AdapterView.OnItemClickListener listener;

    public CustomAdapter(Context context, List<History> values,
                         AdapterView.OnItemClickListener listener) {
        super(context, R.layout.listview_item, values);
        this.context = context;
        this.values = values;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_item, parent, false);
        Button button = (Button) rowView.findViewById(R.id.text_view);

        button.setText(values.get(position).toString());

        button.setOnClickListener(v -> listener.onItemClick(null,
                convertView, position, getItemId(position)));

        return rowView;
    }
}
