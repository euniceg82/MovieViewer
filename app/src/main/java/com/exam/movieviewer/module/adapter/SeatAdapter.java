
package com.exam.movieviewer.module.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.exam.movieviewer.R;

import java.util.List;
/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class SeatAdapter extends ArrayAdapter<String> {
    private List<String> items;
    private List<String> available;
    private Activity activity;

    public SeatAdapter(Activity a, int textViewResourceId, List<String> items, List<String> available) {
        super(a, textViewResourceId, items);
        this.items = items;
        this.activity = a;
        this.available = available;
    }

    public static class ViewHolder {
        public ImageView iv_icon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi =
                    (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_seat, null);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView) v.findViewById(R.id.iv_icon);
            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();

        final String seat = items.get(position);
        if (seat != null) {

            if (!available.contains(seat)) {
                if (seat.contains("(")) {
                    holder.iv_icon.setVisibility(View.INVISIBLE);
                } else holder.iv_icon.setImageResource(R.drawable.ic_check_box_outline_blue);
            } else {
                if (seat.contains("(")) {
                    holder.iv_icon.setVisibility(View.INVISIBLE);
                } else holder.iv_icon.setImageResource(R.drawable.ic_check_box_outline_blank);
            }
        }
        return v;
    }

}

