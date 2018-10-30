package com.varadhismartek.pathshalatransportsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class Recyclerfinancial extends RecyclerView.Adapter<Recyclerfinancial.MyViewHolder>{

    public List<Team_Pojo> team_pojoList;
    Context context;

    public Recyclerfinancial(Context context, List<Team_Pojo> team_pojos)
    {
        this.context = context;
        this.team_pojoList = team_pojos;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.team_imageview);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Recyclerfinancial.MyViewHolder holder, int position)
    {
        Team_Pojo team = team_pojoList.get(position);

        //holder.imageView.setImageResource(team.getImage());

//        holder.textView.setText(team.getText());
    }

    @Override
    public int getItemCount()
    {
        return team_pojoList.size();
    }
}
