package com.varadhismartek.pathshalatransportsystem;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.varadhismartek.pathshalatransportsystem.Fragment.Addvehicle;
import com.varadhismartek.pathshalatransportsystem.Fragment.Selectaction;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String[] ALL_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_SMS


    };

    private AppPermissions mRuntimePermission;
    private static final int ALL_REQUEST_CODE = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mRuntimePermission = new AppPermissions(this);
        takeAllpermissionHere();


        if (savedInstanceState == null) {
            // only create fragment if activity is started for the first time
            Selectaction selectaction = new Selectaction();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, selectaction).commit();
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void takeAllpermissionHere() {
        if (mRuntimePermission.hasPermission(ALL_PERMISSIONS)) {
            Toast.makeText(this, "All permission already given", Toast.LENGTH_SHORT).show();
        } else {
            mRuntimePermission.requestPermission(this, ALL_PERMISSIONS, ALL_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_REQUEST_CODE:
                List<Integer> permissionResults = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults.add(grantResult);
                }
                if (permissionResults.contains(PackageManager.PERMISSION_DENIED)) {
                    // Toast.makeText(this, "All Permissions not granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "All Permissions granted", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }




}
