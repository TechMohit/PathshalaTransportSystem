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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row_document_info, parent, false);

        return new ImageViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder,  final int position) {
        ImageModel imageModel = arrayList.get(position);
        if (imageModel.getImage() != null) {


            Glide.with(mContext)
                    .load(Uri.parse(imageModel.getImage().toString()))
                    .into(holder.showClicekdImage);

            holder.showClicekdImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResourcestoViews(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    private void setResourcestoViews( final int position) {
        final Dialog settingsDialog = new Dialog(mContext);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(LayoutInflater.from(mContext).inflate(R.layout.dialog_for_image_editing
                , null));

        ImageView imageView = settingsDialog.findViewById(R.id.clickedImageShow);
        Button deleteImage = settingsDialog.findViewById(R.id.delete);
        Button closeDialog = settingsDialog.findViewById(R.id.close);
        imageView.setImageURI(arrayList.get(position).getImage());

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());
                settingsDialog.dismiss();
            }
        });

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });

        settingsDialog.show();

    }
}





