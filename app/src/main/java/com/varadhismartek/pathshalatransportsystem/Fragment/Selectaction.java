package com.varadhismartek.pathshalatransportsystem.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.varadhismartek.pathshalatransportsystem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Selectaction extends Fragment implements View.OnClickListener {
    Button setBarrier,addvehicle,createRoute;


    public Selectaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selectaction, container, false);
        initviews(view);
        InitListener();

        return view;

    }

    private void InitListener() {
        setBarrier.setOnClickListener(this);
        addvehicle.setOnClickListener(this);
        createRoute.setOnClickListener(this);
    }

    private void initviews(View view) {
        setBarrier = view.findViewById(R.id.btnsetBarrier);
        addvehicle = view.findViewById(R.id.btnaddvehicle);
        createRoute = view.findViewById(R.id.btncreateroute);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsetBarrier:

                addtransportbarriers();
                break;

            case R.id.btnaddvehicle:
                addvehicleinfo();
                break;

            case R.id.btncreateroute:
                createRoute();
                break;


        }
    }

    private void addtransportbarriers(){

        Transportbarriers transportbarriers = new Transportbarriers();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, transportbarriers);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void addvehicleinfo(){

        Addvehicle addvehicle= new Addvehicle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, addvehicle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void createRoute(){

        Createroute createroute = new Createroute();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, createroute);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
