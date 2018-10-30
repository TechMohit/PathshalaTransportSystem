package com.varadhismartek.pathshalatransportsystem.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.varadhismartek.pathshalatransportsystem.MainActivity;
import com.varadhismartek.pathshalatransportsystem.R;
import com.varadhismartek.pathshalatransportsystem.TransBarrierModel;


public class Transportbarriers extends Fragment {
    View view;
    //Declaring database
    DatabaseReference lastIdref;
    DatabaseReference mref;
    EditText ed_bus_id, ed_route_id;
    Button btn_save;
    String Tag = "Transportbarriers";
    TextView tv_stop, nostop, tv_decrease, tv_increase;
    ImageView iv_backarrow;
    int i = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transportbarriers, container, false);
        ed_bus_id = view.findViewById(R.id.bus_id);
        ed_route_id = view.findViewById(R.id.route_id);
        btn_save = view.findViewById(R.id.button_send);
        tv_decrease = view.findViewById(R.id.decr);
        tv_increase = view.findViewById(R.id.incr);
        tv_stop = view.findViewById(R.id.stop);
        nostop = view.findViewById(R.id.shifts);
        iv_backarrow = view.findViewById(R.id.img_backbtnaddress);
        lastIdref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers/Transport_Ids");


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Tag, "savebuttonclick");
                Log.d(Tag, ed_bus_id.getText().toString());
                Log.d(Tag, ed_route_id.getText().toString());
                Log.d(Tag, tv_stop.getText().toString());
                TransBarrierModel transBarrierModel = new TransBarrierModel(ed_route_id.getText().toString(),tv_stop.getText().toString());
                //accessing firebase database
                mref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers");
                mref.child("Transport_Barriers").setValue(null);
                mref.child("Transport_Barriers").child(ed_bus_id.getText().toString()).setValue(transBarrierModel);
                lastIdref.child("Current_Registration_Id").setValue(ed_bus_id.getText().toString());
                lastIdref.child("Default_Registration_Id").setValue(ed_bus_id.getText().toString());
                lastIdref.child("Start_Registration_Id").setValue(ed_bus_id.getText().toString());
              //  mref.child("Route_id").setValue(ed_route_id.getText().toString());
               // mref.child("no_stop").setValue(tv_stop.getText().toString());
                Toast.makeText(getActivity(), "Data send successfully", Toast.LENGTH_SHORT).show();
                ed_bus_id.setText("");
                ed_route_id.setText("");
                tv_stop.setText("");
                goToAttract(view);


            }
        });

        tv_decrease.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                String _stringVal;
                Log.d("src", "Decreasing value...");
                if (i > 1) {
                    i = i - 1;
                    _stringVal = String.valueOf(i);
                    tv_stop.setText(_stringVal);

                } else {
                    Log.d(Tag, "Value can't be less than 0");
                }

            }
        });
        tv_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _stringVal;

                Log.d(Tag, "Increasing value...");
                if (i < 20) {
                    i = i + 1;
                    _stringVal = String.valueOf(i);
                    tv_stop.setText(_stringVal);

                }
            }
        });

        iv_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAttract(view);
            }
        });

        return view;
    }

         public void goToAttract(View v) {
           Intent intent = new Intent(getActivity(), MainActivity.class);
           startActivity(intent);
           }


}
