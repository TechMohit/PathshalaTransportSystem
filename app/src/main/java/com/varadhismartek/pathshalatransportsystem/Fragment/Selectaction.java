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
public class Selectaction extends Fragment {


    public Selectaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selectaction, container, false);
        Button setBarrier = view.findViewById(R.id.btnsetBarrier);
        Button addvehicle = view.findViewById(R.id.btnaddvehicle);
        setBarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtransportbarriers();
            }
        });

        addvehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addvehicleinfo();
            }
        });
        return view;
    }
    private void addtransportbarriers(){
        Log.d("Selectaction","addtransportBarriersClick");
        Transportbarriers transportbarriers = new Transportbarriers();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, transportbarriers);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void addvehicleinfo(){
        Log.d("Selectaction","addvehicleinfo");
        Addvehicle addvehicle= new Addvehicle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, addvehicle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
