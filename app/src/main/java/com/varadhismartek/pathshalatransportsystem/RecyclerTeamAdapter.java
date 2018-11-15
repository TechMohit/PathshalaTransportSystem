package com.varadhismartek.pathshalatransportsystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.varadhismartek.pathshalatransportsystem.Fragment.Addvehicle;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTeamAdapter extends RecyclerView.Adapter<RecyclerTeamAdapter.MyViewHolder>  implements OnImageSelect{

    public List<String> team_pojoList;
    Context context;
    private int pos;
    private ImageView img;
    Dialog settingsDialog;

    public RecyclerTeamAdapter(Context context, List<String> team_pojos)
    {
        this.context = context;
        this.team_pojoList = team_pojos;
    }

    //overriding the interface
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100 && resultCode == Activity.RESULT_OK ) {

            Uri uri = data.getData();
            // img.setImageURI(uri);
            // arrayList.add(getPathFromUri(uri));
            Addvehicle.imgarraylist.set(pos,getPathFromUri(uri));
            Uri imageuri = Uri.parse("android.resource://"+context.getPackageName()+"/drawable/folderad");

            Log.d("njebtgh",imageuri+"");

            Addvehicle.imgarraylist.add(getPathFromUri(imageuri));
            notifyDataSetChanged();

        }
        if(requestCode == 200 && resultCode == Activity.RESULT_OK ) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), photo, "Title", null);
            Uri filePath = Uri.parse(path);
            Addvehicle.imgarraylist.set(pos,getPathFromUri(filePath));
            Uri imageuri = Uri.parse("android.resource://"+context.getPackageName()+"/drawable/folderad");
            Addvehicle.imgarraylist.add(getPathFromUri(imageuri));
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView img,cv_close;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cv_img);
            cv_close = itemView.findViewById(R.id.cv_close_img);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {


        pos=position;

        //intializing the imageview
        img = holder.img;

        //setting the uri
        Uri imageuri;

        //setting the image into the arraylist if it is empty then run the image from drawble
        //here its checking from the arraylsit
        if (team_pojoList.get(position).equals("/drawable/folderad"))
        {

            holder.cv_close.setVisibility(View.GONE);
            imageuri = Uri.parse("android.resource://" + context.getPackageName() + team_pojoList.get(position));
        }
        else { //else if it has the any string then it will load that image.

            imageuri = Uri.parse(team_pojoList.get(position));
            holder.cv_close.setVisibility(View.VISIBLE);

        }

        Log.d("imageloc",imageuri.toString());

        holder.img.setImageURI(imageuri);

        holder.img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnAddOnClick();
               /* Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image*//*");
                ((AppCompatActivity)context).startActivityForResult(
                        Intent.createChooser(intent, "Select File"), 100);*/
            }

        });

        holder.cv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if the image arraylist is greater than zero
                if (Addvehicle.imgarraylist.size()>1)
                {
                    //removing the string from the arraylist
                    Addvehicle.imgarraylist.remove(position);
                    notifyDataSetChanged();
                }

            }
        });

    }

    //getting the string from the uri
    private String getPathFromUri(Uri filePathWorkExpTransport) {


        Cursor cursor = context.getContentResolver().query(filePathWorkExpTransport, null, null, null, null);
        if (cursor == null) {
            return filePathWorkExpTransport.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    public int getItemCount()
    {
        return Addvehicle.imgarraylist.size();
    }


    private void btnAddOnClick() {
        settingsDialog = new Dialog(context);
        settingsDialog.setContentView(R.layout.attach_dialog_profile_picture);
        settingsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        settingsDialog.setTitle("Choose your option..");



        LinearLayout dialogcamera = settingsDialog.findViewById(R.id.dialog_ll_camera);
        LinearLayout dialoggallery = settingsDialog.findViewById(R.id.dialog_ll_gallery);



      dialoggallery.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(Intent.ACTION_PICK,
                      android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              intent.setType("image/*");
                ((AppCompatActivity)context).startActivityForResult(
                        Intent.createChooser(intent, "Select File"), 100);
              settingsDialog.dismiss();

          }
      });
      dialogcamera.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              ((AppCompatActivity)context).startActivityForResult(cameraIntent, 200);
              settingsDialog.dismiss();

          }
      }
      );
        settingsDialog.show();

    }

}
