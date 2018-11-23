package com.varadhismartek.pathshalatransportsystem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by varadhi22 on 23/11/18.
 */

class ImageViewHolder extends RecyclerView.ViewHolder {
     ImageView img,cv_close;

    public ImageViewHolder(View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.cv_img);
        cv_close = itemView.findViewById(R.id.cv_close_img);

    }
}
