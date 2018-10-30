package com.varadhismartek.pathshalatransportsystem.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.varadhismartek.pathshalatransportsystem.R;




public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    Context mContext;
    String[] leaves_types;
    String colorcode;

    public CustomSpinnerAdapter(Context mContext, String[] arrayList,String colorcode) {
        this.mContext=mContext;
        this.leaves_types = arrayList;
        this.colorcode = colorcode;

    }

    @Override
    public int getCount() {
        return leaves_types.length;
    }

    @Override
    public Object getItem(int position) {
        return leaves_types[position];
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(mContext);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(12);

        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_circle_black_24dp, 0);
        txt.setText(leaves_types[position]);
        txt.setTextColor(Color.parseColor(colorcode));
        return  txt;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(mContext);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(leaves_types[position]);
        txt.setTextColor(Color.parseColor("#464646"));
        return  txt;
    }

}
