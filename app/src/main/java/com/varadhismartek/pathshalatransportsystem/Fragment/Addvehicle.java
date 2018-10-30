package com.varadhismartek.pathshalatransportsystem.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.varadhismartek.pathshalatransportsystem.Constant;
import com.varadhismartek.pathshalatransportsystem.MainActivity;
import com.varadhismartek.pathshalatransportsystem.R;
import com.varadhismartek.pathshalatransportsystem.RecyclerTeamAdapter;
import com.varadhismartek.pathshalatransportsystem.Recyclerfinancial;
import com.varadhismartek.pathshalatransportsystem.Recyclerotherdocuments;
import com.varadhismartek.pathshalatransportsystem.Recyclervehiclefitness;
import com.varadhismartek.pathshalatransportsystem.Team_Pojo;
import com.varadhismartek.pathshalatransportsystem.TransBarrierModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Addvehicle extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    RecyclerView recyclerView,otherdocuments,recyclerfinancial,recyclervehiclefitness;
    private List<Team_Pojo> teamList = new ArrayList<>();

    private RecyclerTeamAdapter adapter;
    private Recyclerfinancial recyclerfinancialadapter;
    private Recyclervehiclefitness recyclervehiclefitnessadapter;
    private String[] vehicletype = {"BUS","AC BUS","MINI BUS","TRAVELLER"};
    private String[] bodytype = {"NEW","SECOND HAND"};
    Spinner vehicletypespin,bodytypespin;
    String Tag = "Addvehicle";
    String  str_vehicle_type,str_body_type,vehicleregno,vehiclename,vehiclegpsdetails,chasisnumber,enginenumber,manufacturename,modelnumber,
            manufactureyear,seatingcapacity,registeringauthority,registeringstate;
    EditText vehicle_regno,vehicle_name,vehicle_gpsdetails,chasis_number,engine_number,manufacture_name,
            model_number,manufacture_year,seating_capacity,registering_authority,registering_state;
    DatabaseReference lastIdref,lastIdref1;
    DatabaseReference mref1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Vehicle_Registration");
    ImageView calendarForDob;
    TextView bt_save,vehicle_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View  v= inflater.inflate(R.layout.fragment_addvehicle, container, false);
        initViews(v);
        initListner();

        lastIdref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers");
        lastIdref1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers/Transport_Ids");
        //lastIdref1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers/Transport_Ids/Current_Registration_Id");
        getStudentRegistrationIdFromBarriers();

        CustomSpinnerAdapter customSpinnerAdaptervehicle=new CustomSpinnerAdapter(getActivity(),vehicletype,"#717071");
        vehicletypespin.setAdapter(customSpinnerAdaptervehicle);
        vehicletypespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_vehicle_type = parent.getItemAtPosition(position).toString();
                Log.d(Tag, str_vehicle_type);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter customSpinnerAdapterbody=new CustomSpinnerAdapter(getActivity(),bodytype,"#717071");
        bodytypespin.setAdapter(customSpinnerAdapterbody);
        bodytypespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_body_type = parent.getItemAtPosition(position).toString();
                Log.d(Tag, str_body_type);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter= new RecyclerTeamAdapter(getActivity(),teamList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Recyclerotherdocuments recyclerotherdocuments = new Recyclerotherdocuments(getActivity(), teamList);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        otherdocuments.setLayoutManager(mLayoutManager2);
        otherdocuments.setItemAnimator(new DefaultItemAnimator());
        otherdocuments.setAdapter(recyclerotherdocuments);

        recyclerfinancialadapter= new Recyclerfinancial(getActivity(),teamList);
        LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerfinancial.setLayoutManager(mLayoutManager3);
        recyclerfinancial.setItemAnimator(new DefaultItemAnimator());
        recyclerfinancial.setAdapter(recyclerfinancialadapter);

        recyclervehiclefitnessadapter = new Recyclervehiclefitness(getActivity(),teamList);
        LinearLayoutManager mLayoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclervehiclefitness.setLayoutManager(mLayoutManager4);
        recyclervehiclefitness.setItemAnimator(new DefaultItemAnimator());
        recyclervehiclefitness.setAdapter(recyclervehiclefitnessadapter);
        getData();




        return v;


    }




    private void initViews(View v) {
        vehicletypespin         = v.findViewById(R.id.vehicle_type);
        bodytypespin            = v.findViewById(R.id.bodytype_id);
        vehicle_id              = v.findViewById(R.id.vehicle_id);
        vehicle_regno           = v.findViewById(R.id.registration_num);
        vehicle_name            = v.findViewById(R.id.vehicle_name);
        vehicle_gpsdetails      = v.findViewById(R.id.vehicle_gps_details);
        recyclerView            = v.findViewById(R.id.recyclerteammembers);
        otherdocuments          = v.findViewById(R.id.otherdocumentsrecyclerview);
        recyclerfinancial       = v.findViewById(R.id.recyclerfinancialdetails);
        recyclervehiclefitness  = v.findViewById(R.id.vehiclefitness);
        chasis_number           = v.findViewById(R.id.chasis_number);
        engine_number           = v.findViewById(R.id.engine_number);
        manufacture_name        = v.findViewById(R.id.manufacture_name);
        model_number            = v.findViewById(R.id.model_number);
        manufacture_year        = v.findViewById(R.id.manufacture_year);
        seating_capacity        = v.findViewById(R.id.seating_capacity);
        registering_authority   = v.findViewById(R.id.registering_authority);
        registering_state       = v.findViewById(R.id.registering_state);
       // calendarForDob          = v.findViewById(R.id.calendar);
        bt_save                 = v.findViewById(R.id.button_send);


    }
    private void initListner() {
      // calendarForDob.setOnClickListener(this);
       bt_save.setOnClickListener(this);
    }
    private void saveaddvehicledata() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Storing data...please wait");
        progressDialog.show();

        //get data from each field
        vehicleregno = vehicle_regno.getText().toString();
        vehiclename = vehicle_name.getText().toString();
        vehiclegpsdetails = vehicle_name.getText().toString();
        chasisnumber = chasis_number.getText().toString();
        enginenumber = engine_number.getText().toString();
        manufacturename = manufacture_name.getText().toString();
        modelnumber = model_number.getText().toString();
        manufactureyear = manufacture_year.getText().toString();
        seatingcapacity = seating_capacity.getText().toString();
        registeringauthority = registering_authority.getText().toString();
        registeringstate = registering_state.getText().toString();
        //create data model

        //send to firebase
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(Tag,"Run method call");
                Log.d(Tag,"Test"+Constant.FINAL_REGISTRATION_ID);
                TransBarrierModel transBarrierModel = new TransBarrierModel(str_vehicle_type,vehicleregno,vehiclename,vehiclegpsdetails);
                TransBarrierModel transBarrierModelreg = new TransBarrierModel(str_body_type,chasisnumber,
                        enginenumber,manufacturename,modelnumber,manufactureyear,seatingcapacity,registeringauthority,registeringstate);
                mref1.child(Constant.FINAL_REGISTRATION_ID).child("Basic_vehicle_Details").setValue(transBarrierModel);
                mref1.child(Constant.FINAL_REGISTRATION_ID).child("vehicle_registration_Details").setValue(transBarrierModelreg);





                setdata();
            }

        },1000);
        progressDialog.dismiss();


    }

    private void getData() {

        Team_Pojo team_pojo = new Team_Pojo(R.drawable.emp4, "Raghu");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "Abhi");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "nizar");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "sarup");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "sree");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "EM");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "EM");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "EM");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "EM");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "EM");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "EM");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "EM");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "EM");
        teamList.add(team_pojo);

        team_pojo = new Team_Pojo(R.drawable.emp4, "EM");
        teamList.add(team_pojo);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(),vehicletype[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void getStudentRegistrationIdFromBarriers() {
          final ProgressDialog progressDialog = new ProgressDialog(getActivity());
          progressDialog.setMessage("Getting data please wait...");
          progressDialog.show();
          lastIdref.child("Transport_Ids").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // Log.d("dataSnapShotA", ""+dataSnapshot.getKey());
                for(DataSnapshot postSnapShotA : dataSnapshot.getChildren()){
                  //  Log.d("postsnapShotA", ""+postSnapShotA.getKey());
                    if(postSnapShotA.getKey().equals("Default_Registration_Id")){
                        Log.d("empID_Default", ""+postSnapShotA.getValue());
                        Constant.REGISTRATION_DEFAULT_ID = (String) postSnapShotA.getValue();

                    }

                    if(postSnapShotA.getKey().equals("Start_Registration_Id")){
                        Log.d("Start_Registration_Id", ""+postSnapShotA.getValue());
                        Constant.REGISTRATION_START_ID = (String) postSnapShotA.getValue();
                        incrementRegistrationID();
                    }

                    if(postSnapShotA.getKey().equals("Current_Registration_Id")){
                        Log.d("Current_Registration_Id", ""+postSnapShotA.getValue());
                        Constant.REGISTRATION_CURRENT_ID = (String) postSnapShotA.getValue();

                    }
                    progressDialog.dismiss();

                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void incrementRegistrationID() {

        if(Constant.REGISTRATION_START_ID.equalsIgnoreCase(Constant.REGISTRATION_DEFAULT_ID)){
            increment();
        }

        else {

            Constant.REGISTRATION_CURRENT_ID = Constant.REGISTRATION_DEFAULT_ID;
            Constant.REGISTRATION_START_ID= Constant.REGISTRATION_DEFAULT_ID;
            increment();

        }
    }

    private void increment() {

        int val =lastAlphaNumeric(Constant.REGISTRATION_CURRENT_ID);
        String half1 = Constant.REGISTRATION_CURRENT_ID.substring(0, val+1);
        String half2 = Constant.REGISTRATION_CURRENT_ID.substring(val+1 , Constant.REGISTRATION_CURRENT_ID.length());

        String newHalf2 = "9"+half2;

        int lastnumberincrease = Integer.parseInt((newHalf2))+1;

        String newHalf2Icreae = String.valueOf(lastnumberincrease);

        String finalNumber = newHalf2Icreae.substring(1,newHalf2Icreae.length() );

        Constant.FINAL_REGISTRATION_ID = half1+finalNumber;
        Constant.REGISTRATION_CURRENT_TEMP_ID = Constant.FINAL_REGISTRATION_ID;

        Log.d("Increment Id", ""+ Constant.FINAL_REGISTRATION_ID);
        vehicle_id.setText(Constant.FINAL_REGISTRATION_ID);

    }

    private int lastAlphaNumeric(String employeeCurrentId) {
        for (int i = employeeCurrentId.length() - 1; i >= 0; i--) {
            char c = employeeCurrentId.charAt(i);
            if (Character.isLetter(c))
                return i;
        }
        return -1;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
          //  case R.id.calendar:
                // Get Current Date
              //  final Dialog dialog = new Dialog(getActivity());
             //   dialog.setContentView(R.layout.datepicker);
              //  dialog.setTitle("");
               // DatePicker datePicker = dialog.findViewById(R.id.datePickerDialog);
            case R.id.button_send:
                saveaddvehicledata();
                break;
        }
    }

    private void setdata(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Sending data...");
        if(!Constant.REGISTRATION_CURRENT_TEMP_ID.equals(Constant.FINAL_REGISTRATION_ID)){
            lastIdref.child("Current_Registration_Id").setValue(Constant.FINAL_REGISTRATION_ID);
            Toast.makeText(getActivity(), "Registration Finished1...", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(getActivity() , MainActivity.class);
            startActivity(in);
            progressDialog.dismiss();
        }
        else {

            lastIdref1.child("Current_Registration_Id").setValue(Constant.FINAL_REGISTRATION_ID);
            Toast.makeText(getActivity(), "Registration Finished...", Toast.LENGTH_SHORT).show();
             Intent in = new Intent(getActivity() , MainActivity.class);
             startActivity(in);
            progressDialog.dismiss();
        }
        Log.d(Tag,"set data");


    }
}
