package com.varadhismartek.pathshalatransportsystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.varadhismartek.pathshalatransportsystem.Fragment.Addvehicle;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private ArrayList<ImageModel> arrayList;
    private Context mContext;
    private int requestcode;

    public ImageAdapter(ArrayList<ImageModel> arrayList, Context mContext, int requestcode) {
        this.requestcode = requestcode;
        this.arrayList = arrayList;
        this.mContext = mContext;


    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards,parent,false);

      //  return new ImageViewHolder(view);

        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cards,null));

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageModel imageModel = arrayList.get(position);
        if(imageModel.getImage()!=null){


            Glide.with(mContext)
                    .load(Uri.parse(imageModel.getImage().toString()))
                    .into(holder.img);

            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  setResourcestoViews(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}





