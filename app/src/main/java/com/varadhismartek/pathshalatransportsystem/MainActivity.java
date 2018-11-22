package com.varadhismartek.pathshalatransportsystem;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.varadhismartek.pathshalatransportsystem.Fragment.Addvehicle;
import com.varadhismartek.pathshalatransportsystem.Fragment.Selectaction;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Selectaction selectaction = new Selectaction();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, selectaction).commit();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300||requestCode == 400 && resultCode == Activity.RESULT_OK) {
            Addvehicle.recyclerotherdocuments.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 100||requestCode == 200 && resultCode == Activity.RESULT_OK) {
            Addvehicle.recyclerTeamAdapter.onActivityResult(requestCode,resultCode,data);
        }
        if (requestCode == 500||requestCode == 600 && resultCode == Activity.RESULT_OK) {
            Addvehicle.recyclerfinancialadapter.onActivityResult(requestCode,resultCode,data);
        }
        if (requestCode == 700||requestCode == 800 && resultCode == Activity.RESULT_OK) {
            Addvehicle.recyclervehiclefitnessadapter.onActivityResult(requestCode,resultCode,data);
        }



    }


}
