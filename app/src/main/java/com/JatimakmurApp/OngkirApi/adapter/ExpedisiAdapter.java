package com.JatimakmurApp.OngkirApi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.JatimakmurApp.R;
import com.JatimakmurApp.OngkirApi.model.expedisi.ItemExpedisi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robby Dianputra on 1/5/2018.
 */

public class ExpedisiAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ItemExpedisi> movieItems;
    private ArrayList<ItemExpedisi> listlokasiasli;


    /**
     * Instantiates a new Inbox adapter.
     *
     * @param activity   the activity
     * @param movieItems the movie items
     */
    public ExpedisiAdapter(Activity activity, List<ItemExpedisi> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;

        listlokasiasli = new ArrayList<ItemExpedisi>();
        listlokasiasli.addAll(movieItems);
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_category, null);

        TextView tv_category = (TextView) convertView.findViewById(R.id.tv_category);
        TextView tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);

        ItemExpedisi m = movieItems.get(position);

        tv_category.setText(m.getName());
        tv_detail.setText(m.getId());

        return convertView;
    }

    @SuppressLint("DefaultLocale")
    public void filter(String charText)
    {
        charText = charText.toLowerCase();

        movieItems.clear();
        if (charText.length() == 0) {
			/* tampilkan seluruh data */
            movieItems.addAll(listlokasiasli);

        } else {
            for (ItemExpedisi lok : listlokasiasli) {
                if (lok.getName().toLowerCase().contains(charText)) {
                    movieItems.add(lok);
                } else {
                }

            }
        }

        notifyDataSetChanged();
    }

    public void setList(List<ItemExpedisi> movieItems){
        this.listlokasiasli.addAll(movieItems);
    }
}
