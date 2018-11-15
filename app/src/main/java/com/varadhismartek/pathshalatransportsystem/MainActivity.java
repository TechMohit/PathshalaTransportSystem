package com.varadhismartek.pathshalatransportsystem;


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

        Addvehicle.recyclerTeamAdapter.onActivityResult(requestCode,resultCode,data);

    }
}
