
package com.exam.movieviewer.module.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.exam.movieviewer.R;

import java.util.List;
/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class SelectedSeatAdapter extends ArrayAdapter<String> {
    private List<String> items;
    private Activity activity;

    public SelectedSeatAdapter(Activity a, int textViewResourceId, List<String> items) {
        super(a, textViewResourceId, items);
        this.items = items;
        this.activity = a;
    }

    public static class ViewHolder {
        public Button bt_selected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi =
                    (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_selected_seat, null);
            holder = new ViewHolder();
            holder.bt_selected = (Button) v.findViewById(R.id.bt_selected);
            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();

        final String seat = items.get(position);
        if (seat != null) {
            holder.bt_selected.setText(seat);
        }
        return v;
    }

}

