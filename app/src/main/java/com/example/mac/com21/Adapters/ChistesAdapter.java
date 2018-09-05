package com.example.mac.com21.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mac.com21.Pojos.Chiste;
import com.example.mac.com21.R;


import java.util.List;



public class ChistesAdapter extends BaseAdapter {
    private Context context;

    List<Chiste> chisteList;

    private static String IMAGE_PREFIX = "http://34.211.243.185:8080/images/";

    public ChistesAdapter(Context context, List<Chiste> chisteList){
        this.context = context;
        this.chisteList = chisteList;
    }

    @Override
    public int getCount() {
        return this.chisteList.size();
    }

    @Override
    public Object getItem(int i) {
        return chisteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return chisteList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.imagenchiste);
        TextView textView = (TextView) view.findViewById(R.id.titulochiste);
        final Chiste chiste = chisteList.get(i);
        Glide.with(imageView.getContext()).load(IMAGE_PREFIX+chiste.getImage()).placeholder(R.mipmap.ic_launcher).into(imageView);
        textView.setText(chiste.getNombre());
        return view;

    }
    }
