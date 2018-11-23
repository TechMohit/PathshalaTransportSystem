package com.varadhismartek.pathshalatransportsystem.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.varadhismartek.pathshalatransportsystem.Constant;
import com.varadhismartek.pathshalatransportsystem.ImageAdapter;
import com.varadhismartek.pathshalatransportsystem.ImageModel;
import com.varadhismartek.pathshalatransportsystem.MainActivity;
import com.varadhismartek.pathshalatransportsystem.R;
import com.varadhismartek.pathshalatransportsystem.RecyclerTeamAdapter;
import com.varadhismartek.pathshalatransportsystem.Recyclerfinancial;
import com.varadhismartek.pathshalatransportsystem.Recyclerotherdocuments;
import com.varadhismartek.pathshalatransportsystem.Recyclervehiclefitness;
import com.varadhismartek.pathshalatransportsystem.Team_Pojo;
import com.varadhismartek.pathshalatransportsystem.TransBarrierModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Addvehicle extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    RecyclerView recyclerView, otherdocuments, recyclerfinancial, recyclervehiclefitness;
    private List<Team_Pojo> teamList = new ArrayList<>();

    public static RecyclerTeamAdapter recyclerTeamAdapter;
    public static Recyclerotherdocuments recyclerotherdocuments;
    public static Recyclerfinancial recyclerfinancialadapter;
    public static Recyclervehiclefitness recyclervehiclefitnessadapter;
    private String[] vehicletype = {"BUS", "AC BUS", "MINI BUS", "TRAVELLER"};
    private String[] bodytype = {"NEW", "SECOND HAND"};
    private Spinner vehicletypespin, bodytypespin;
    private String Tag = "Addvehicle";
    Context context;
    Dialog imageChooserDialog;
    public static int i =0;
    public static int FROM_CAMERA= 2;
    public static int FROM_GALLERY= 1;
    public int requestcode;
    public Context mContext;




    private String str_vehicle_type, str_body_type, vehicleregno, vehiclename, vehiclegpsdetails, chasisnumber, enginenumber, manufacturename, modelnumber,
            manufactureyear,seatingcapacity, registeringauthority, registeringstate, selectedYear, selectedMonth, selectedDate,
            registereddate,purchasedate, preownerpurchasedate,previousownerremark, preownerpurchasecost, previousownername,
            totalfreeservice,remanningservice, nextservicekms,nextservicedays, lastservicedate,insurancetype,insurancenum,
            insurancedate,insurancerenewdate,insurancenextrenewdate,agentname,agentid,insurancecomname,agentcontnum,insurancecomnum,
            otherdocinsurancenum,rcnumber,nocnumber,pollutioncertificatenum,pollutioncertificateissuedate,pollutioncertificaterenewdate,fitnesscertificatenum,fitnesscertificateissuedate,
            fitnesscertificaterenewdate,taxpermitnum,taxpayabledate,taxpayableamount,fdvehiclepurchaseprice,fdbankname,fdloanaccountnum,fdloanamount,fdloaninterestrate,
            fddownpayment,fdlaonapprovedate,fdloanduedate,fdloanenddate,fdloanemiamount,fdmaintaincecharges,fdreparingcharges,fdremarks;

    private EditText vehicle_regno, vehicle_name, vehicle_gpsdetails, chasis_number, engine_number, manufacture_name,
            model_number, manufacture_year, seating_capacity, registering_authority, registering_state, registered_date, purchase_date,
            preownerpurchase_date, previousowner_remark, preownerpurchase_cost, previousowner_name,
            mtotalfreeservice, mremanningservice, mnextservicekms, mnextservicedays, mlastservicedate,minsurancetype,minsurancenum,minsurancedate,
            minsurancerenewdate,minsurancenextrenewdate,magentname,magentid,minsurancecomname,magentcontnum,minsurancecomnum,motherdocinsurancenum,mrcnumber,mnocnumber,mpollutioncertificatenum,mpollutioncertificateissuedate,mpollutioncertificaterenewdate,
            mfitnesscertificatenum,mfitnesscertificateissuedate,mfitnesscertificaterenewdate,mtaxpermitnum,mtaxpayabledate,mtaxpayableamount,mfdvehiclepurchaseprice,mfdbankname,mfdloanaccountnum,mfdloanamount,mfdloaninterestrate,mfddownpayment,
            mfdlaonapprovedate,mfdloanduedate,mfdloanenddate,mfdloanemiamount,mfdmaintaincecharges,mfdreparingcharges,mfdremarks;;

    DatabaseReference lastIdref, lastIdref1;
    DatabaseReference mref1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Vehicle_Registration");
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("School/SchoolId/Vehicle_Registration");

    ImageView iv_calendarregisterdate, iv_calendarpurchasedate, iv_previousownerpurchasedate,iv_attach;
    TextView bt_save, vehicle_id;
    TransBarrierModel transBarrierModel, transBarrierModelreg, transBarrierModelService,transBarrierModelInsurance,transBarrierModelOtherDocument,transBarrierModelFinancialDetails;
    public static ArrayList<String> imgarraylist;
    public static ArrayList<ImageModel> imgarrayliststore;
    public static ArrayList<String> othrdocarraylist;
    public static ArrayList<String> finnancearraylist;
    public static ArrayList<String> fittnesarraylist;
    public static ArrayList<ImageModel> arrayList;
    private Uri filePath;
    String pathProfile;
    public static ImageAdapter imageAdapter;
    ArrayList<RecyclerView> arrayListDocRecycelrview         = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_addvehicle, container, false);
        initViews(v);
        initListner();

        lastIdref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers");
        lastIdref1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers/Transport_Ids");
        //lastIdref1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers/Transport_Ids/Current_Registration_Id");
        getStudentRegistrationIdFromBarriers();
        //nextservicedayscheck();
        imgarraylist = new ArrayList<>();
        imgarrayliststore = new ArrayList<>();
        othrdocarraylist = new ArrayList<>();
        finnancearraylist = new ArrayList<>();
        fittnesarraylist = new ArrayList<>();
        arrayList = new ArrayList<>();
        ImageAdapter imageAdapter;

        //setting the imageuri as constant for the image drawable
        Uri imageuri = Uri.parse("android.resource://"+getContext().getPackageName()+"/drawable/folderad");

        //adding the above image uri in the arraylist
        imgarraylist.add(getPathFromUri(imageuri));
        othrdocarraylist.add(getPathFromUri(imageuri));
        finnancearraylist.add(getPathFromUri(imageuri));
        fittnesarraylist.add(getPathFromUri(imageuri));

        CustomSpinnerAdapter customSpinnerAdaptervehicle = new CustomSpinnerAdapter(getActivity(), vehicletype, "#717071");
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

        CustomSpinnerAdapter customSpinnerAdapterbody = new CustomSpinnerAdapter(getActivity(), bodytype, "#717071");
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

       /* recyclerTeamAdapter = new RecyclerTeamAdapter(getActivity(), imgarraylist);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerTeamAdapter);
        recyclerTeamAdapter.notifyDataSetChanged();*/

        recyclerotherdocuments = new Recyclerotherdocuments(getActivity(), othrdocarraylist);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        otherdocuments.setHasFixedSize(true);
        otherdocuments.setLayoutManager(new GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false));
        otherdocuments.setItemAnimator(new DefaultItemAnimator());
        otherdocuments.setAdapter(recyclerotherdocuments);
        recyclerotherdocuments.notifyDataSetChanged();

        recyclerfinancialadapter = new Recyclerfinancial(getActivity(), finnancearraylist);
        LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerfinancial.setHasFixedSize(true);
        recyclerfinancial.setLayoutManager(new GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false));
        recyclerfinancial.setItemAnimator(new DefaultItemAnimator());
        recyclerfinancial.setAdapter(recyclerfinancialadapter);
        recyclerfinancialadapter.notifyDataSetChanged();

        recyclervehiclefitnessadapter = new Recyclervehiclefitness(getActivity(), fittnesarraylist);
        LinearLayoutManager mLayoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclervehiclefitness.setHasFixedSize(true);
        recyclervehiclefitness.setLayoutManager(new GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false));
        recyclervehiclefitness.setItemAnimator(new DefaultItemAnimator());
        recyclervehiclefitness.setAdapter(recyclervehiclefitnessadapter);
        recyclerfinancialadapter.notifyDataSetChanged();
        //getData();


        return v;


    }



    //creating the method to return the string from the uri
    private String getPathFromUri(Uri filePathWorkExpTransport) {


        Cursor cursor = getContext().getContentResolver().query(filePathWorkExpTransport, null, null, null, null);
        if (cursor == null) {
            return filePathWorkExpTransport.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }






    private void initViews(View v) {
        vehicletypespin = v.findViewById(R.id.vehicle_type);
        bodytypespin = v.findViewById(R.id.bodytype_id);
        vehicle_id = v.findViewById(R.id.vehicle_id);
        vehicle_regno = v.findViewById(R.id.registration_num);
        vehicle_name = v.findViewById(R.id.vehicle_name);
        vehicle_gpsdetails = v.findViewById(R.id.vehicle_gps_details);
        recyclerView = v.findViewById(R.id.recyclerteammembers);
        otherdocuments = v.findViewById(R.id.otherdocumentsrecyclerview);
        recyclerfinancial = v.findViewById(R.id.recyclerfinancialdetails);
        recyclervehiclefitness = v.findViewById(R.id.vehiclefitness);
        chasis_number = v.findViewById(R.id.chasis_number);
        engine_number = v.findViewById(R.id.engine_number);
        manufacture_name = v.findViewById(R.id.manufacture_name);
        model_number = v.findViewById(R.id.model_number);
        manufacture_year = v.findViewById(R.id.manufacture_year);
        seating_capacity = v.findViewById(R.id.seating_capacity);
        registering_authority = v.findViewById(R.id.registering_authority);
        registering_state = v.findViewById(R.id.registering_state);
        iv_calendarregisterdate = v.findViewById(R.id.calendarregistereddate);
        iv_attach = v.findViewById(R.id.img_attachfile);
        iv_calendarpurchasedate = v.findViewById(R.id.calendarpurchasedate);
        iv_previousownerpurchasedate = v.findViewById(R.id.iv_previousownerpurchasedate);
        bt_save = v.findViewById(R.id.button_send);
        registered_date = v.findViewById(R.id.et_registereddate);
        purchase_date = v.findViewById(R.id.et_purchasedate);
        preownerpurchase_date = v.findViewById(R.id.previousownerpurchasedate);
        previousowner_remark = v.findViewById(R.id.previousownerremark);
        preownerpurchase_cost = v.findViewById(R.id.previousownerpurchasecost);
        previousowner_name = v.findViewById(R.id.previousownername);
        mtotalfreeservice = v.findViewById(R.id.et_totalfreeservice_transsport);
        mremanningservice = v.findViewById(R.id.et_remanningservice_transport);
        mnextservicedays = v.findViewById(R.id.et_nextservicedays_transport);
        mnextservicekms = v.findViewById(R.id.et_nextservicekms_transport);
        mlastservicedate = v.findViewById(R.id.et_lastservicedate_transport);
        minsurancedate = v.findViewById(R.id.et_insurancedate_transport);
        minsurancetype = v.findViewById(R.id.et_insurancetype_transport);
        minsurancenum = v.findViewById(R.id.et_insurancenumber_transport);
        minsurancerenewdate = v.findViewById(R.id.et_insurancerenewdate_transport);
        minsurancenextrenewdate = v.findViewById(R.id.et_insurancenextrenewdate_transport);
        magentname = v.findViewById(R.id.et_agentname_transport);
        magentid = v.findViewById(R.id.et_agentid_transport);
        minsurancecomname = v.findViewById(R.id.et_insurancecompanyname_transport);
        magentcontnum = v.findViewById(R.id.et_agentcontnum_transport);
        minsurancecomnum = v.findViewById(R.id.et_insurancecomnum_transport);
        motherdocinsurancenum = v.findViewById(R.id.et_otherdocumentinsurancenum_transport);
        mrcnumber = v.findViewById(R.id.et_rcnum_transport);
        mnocnumber = v.findViewById(R.id.et_nocnum_transport);


        mpollutioncertificatenum = v.findViewById(R.id.et_pollutioncertificatenum_transport);
        mpollutioncertificateissuedate = v.findViewById(R.id.et_pollutioncertificateissuedate_transport);
        mpollutioncertificaterenewdate = v.findViewById(R.id.et_pollutioncertificaterenewdate_transport);
        mfitnesscertificatenum = v.findViewById(R.id.et_fitnesscertificatenumber_transport);
        mfitnesscertificateissuedate = v.findViewById(R.id.et_fitnesscertificateissuedate_transport);
        mfitnesscertificaterenewdate = v.findViewById(R.id.et_fitnesscertificaterenewdate_transport);
        mtaxpermitnum = v.findViewById(R.id.et_texpermitnumber_transport);
        mtaxpayabledate = v.findViewById(R.id.et_texpayabledate_transport);
        mtaxpayableamount = v.findViewById(R.id.et_texpayableamount_transport);

        mfdvehiclepurchaseprice = v.findViewById(R.id.et_fdpurchaseprice_transport);
        mfdbankname = v.findViewById(R.id.et_fdbankname_transport);
        mfdloanaccountnum = v.findViewById(R.id.et_fdlaonaccountnum_transport);
        mfdloanamount = v.findViewById(R.id.et_fdloanamount_transport);
        mfdloaninterestrate = v.findViewById(R.id.et_fdloanintrestrate_transport);
        mfddownpayment = v.findViewById(R.id.et_fddownpayment_transport);
        mfdlaonapprovedate = v.findViewById(R.id.et_fdloanapprovedate_transport);
        mfdloanduedate = v.findViewById(R.id.et_fdloanduedate_transport);
        mfdloanenddate = v.findViewById(R.id.et_fdloanenddate_transport);
        mfdloanemiamount = v.findViewById(R.id.et_fdloanemiamount_transport);
        mfdmaintaincecharges = v.findViewById(R.id.et_fdmaintenanceamount_transport);
        mfdreparingcharges = v.findViewById(R.id.et_fdreparingcharges_transport);
        mfdremarks = v.findViewById(R.id.et_fdremarkes_transport);




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("mohittest",""+data);


            Log.d("mohittest1",""+data);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), photo, "Title", null);
            filePath= Uri.parse(path);

           // setImageToRecyclerView();
            // pathProfile = getPathFromUri(filePath);

            arrayList.add(new ImageModel(filePath));
            imageAdapter  = new ImageAdapter(imgarrayliststore,mContext,requestcode);
            ImageModel imageModel = new ImageModel(filePath);
            imgarrayliststore.add(imageModel);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            recyclerView.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();



    }

    private void setImageToRecyclerView() {
        LinearLayoutManager guardianLayoutManager1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        arrayListDocRecycelrview.get(0).setLayoutManager(guardianLayoutManager1);





    }

    private void initListner() {
        iv_calendarregisterdate.setOnClickListener(this);
        iv_calendarpurchasedate.setOnClickListener(this);
        iv_previousownerpurchasedate.setOnClickListener(this);
        mlastservicedate.setOnClickListener(this);
        minsurancedate.setOnClickListener(this);
        minsurancerenewdate.setOnClickListener(this);
        minsurancenextrenewdate.setOnClickListener(this);
        bt_save.setOnClickListener(this);
        iv_attach.setOnClickListener(this);


    }

    private void saveaddvehicledata() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Storing data...please wait");
        progressDialog.show();

        //get data from each field
        vehicleregno = vehicle_regno.getText().toString();
        vehiclename = vehicle_name.getText().toString();
        vehiclegpsdetails = vehicle_gpsdetails.getText().toString();
        chasisnumber = chasis_number.getText().toString();
        enginenumber = engine_number.getText().toString();
        manufacturename = manufacture_name.getText().toString();
        modelnumber = model_number.getText().toString();
        manufactureyear = manufacture_year.getText().toString();
        seatingcapacity = seating_capacity.getText().toString();
        registeringauthority = registering_authority.getText().toString();
        registeringstate = registering_state.getText().toString();
        registereddate = registered_date.getText().toString();
        purchasedate = purchase_date.getText().toString();
        preownerpurchasedate = preownerpurchase_date.getText().toString();
        previousownerremark = previousowner_remark.getText().toString();
        preownerpurchasecost = preownerpurchase_cost.getText().toString();
        previousownername = previousowner_name.getText().toString();
        totalfreeservice = mtotalfreeservice.getText().toString();
        remanningservice = mremanningservice.getText().toString();
        lastservicedate = mlastservicedate.getText().toString();
        nextservicedays = mnextservicedays.getText().toString();
        nextservicekms = mnextservicekms.getText().toString();

        insurancetype = minsurancetype.getText().toString();
        insurancenum = minsurancenum.getText().toString();
        insurancedate = minsurancedate.getText().toString();
        insurancerenewdate = minsurancerenewdate.getText().toString();
        insurancenextrenewdate = minsurancenextrenewdate.getText().toString();
        agentname = magentname.getText().toString();
        agentid   = magentid.getText().toString();
        insurancecomname   = minsurancecomname.getText().toString();
        insurancecomnum   = magentcontnum.getText().toString();
        agentcontnum   = magentcontnum.getText().toString();

        otherdocinsurancenum = motherdocinsurancenum.getText().toString();
        rcnumber = mrcnumber.getText().toString();
        nocnumber = mnocnumber.getText().toString();
        pollutioncertificatenum = mpollutioncertificatenum.getText().toString();
        pollutioncertificateissuedate = mpollutioncertificateissuedate.getText().toString();
        pollutioncertificaterenewdate = mpollutioncertificaterenewdate.getText().toString();
        fitnesscertificatenum = mfitnesscertificatenum.getText().toString();
        fitnesscertificateissuedate = mfitnesscertificateissuedate.getText().toString();
        fitnesscertificaterenewdate = mfitnesscertificaterenewdate.getText().toString();
        Log.d("fitness","dated"+fitnesscertificaterenewdate);
        taxpermitnum = mtaxpermitnum.getText().toString();
        taxpayabledate = mtaxpayabledate.getText().toString();
        taxpayableamount = mtaxpayableamount.getText().toString();

        fdvehiclepurchaseprice = mfdvehiclepurchaseprice.getText().toString();
        fdbankname = mfdbankname.getText().toString();
        fdloanaccountnum = mfdloanaccountnum.getText().toString();
        fdloanamount = mfdloanamount.getText().toString();
        fdloaninterestrate = mfdloaninterestrate.getText().toString();
        fddownpayment = mfddownpayment.getText().toString();
        fdlaonapprovedate = mfdlaonapprovedate.getText().toString();
        fdloanduedate = mfdloanduedate.getText().toString();
        fdloanenddate = mfdloanenddate.getText().toString();
        fdloanemiamount = mfdloanemiamount.getText().toString();
        fdmaintaincecharges = mfdmaintaincecharges.getText().toString();
        fdreparingcharges = mfdreparingcharges.getText().toString();
        fdremarks = mfdremarks.getText().toString();

        //create data model
        transBarrierModel = new TransBarrierModel(str_vehicle_type, vehicleregno, vehiclename, vehiclegpsdetails);
        transBarrierModelreg = new TransBarrierModel(str_body_type, chasisnumber,
                enginenumber, manufacturename, modelnumber, manufactureyear, seatingcapacity, registeringauthority,
                registeringstate, registereddate, purchasedate, preownerpurchasedate, previousownerremark, preownerpurchasecost, previousownername);

        transBarrierModelService = new TransBarrierModel(totalfreeservice, remanningservice, lastservicedate, nextservicedays, nextservicekms);
        transBarrierModelInsurance = new TransBarrierModel(insurancetype,insurancenum, insurancedate, insurancerenewdate,insurancenextrenewdate,
                                     agentname,agentid,insurancecomname,agentcontnum,insurancecomnum);
        transBarrierModelOtherDocument = new TransBarrierModel(otherdocinsurancenum,rcnumber,nocnumber,pollutioncertificatenum,pollutioncertificateissuedate,
                pollutioncertificaterenewdate,fitnesscertificatenum,fitnesscertificateissuedate,fitnesscertificaterenewdate,taxpermitnum,taxpayabledate,taxpayableamount);

        transBarrierModelFinancialDetails = new TransBarrierModel(fdvehiclepurchaseprice,fdbankname,fdloanaccountnum,fdloanamount,fdloaninterestrate,
                fddownpayment,fdlaonapprovedate,fdloanduedate,fdloanenddate,fdloanemiamount,fdmaintaincecharges,fdreparingcharges,fdremarks);


        //send to firebase
        final StorageReference storageReference = mStorageRef.child(Constant.FINAL_REGISTRATION_ID).child("Basic_vehicle_Details");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Log.d(Tag, "Run method call");
                Log.d(Tag, "Test" + Constant.FINAL_REGISTRATION_ID+" "+imgarrayliststore.size()+""+imgarrayliststore.get(0));
              /*  for(  i=0;i<imgarrayliststore.size();i++) {
                    storageReference.child("" + i).putFile(imgarrayliststore.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("Basic_vehicle_Details").child("insurance_document_picture" + i).setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });
                }*/





                mref1.child(Constant.FINAL_REGISTRATION_ID).child("Basic_vehicle_Details").setValue(transBarrierModel);
                mref1.child(Constant.FINAL_REGISTRATION_ID).child("vehicle_registration_Details").setValue(transBarrierModelreg);
                mref1.child(Constant.FINAL_REGISTRATION_ID).child("Servicing_Details").setValue(transBarrierModelService);
                mref1.child(Constant.FINAL_REGISTRATION_ID).child("Insurance_Details").setValue(transBarrierModelInsurance);
                mref1.child(Constant.FINAL_REGISTRATION_ID).child("Other_Document").setValue(transBarrierModelOtherDocument);
                mref1.child(Constant.FINAL_REGISTRATION_ID).child("Financial_Details").setValue(transBarrierModelFinancialDetails);


                setdata();
            }

        }, 1000);
        progressDialog.dismiss();


    }

  /*  private void getData() {

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
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), vehicletype[position], Toast.LENGTH_LONG).show();
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
                for (DataSnapshot postSnapShotA : dataSnapshot.getChildren()) {
                    //  Log.d("postsnapShotA", ""+postSnapShotA.getKey());
                    if (postSnapShotA.getKey().equals("Default_Registration_Id")) {
                        Log.d("empID_Default", "" + postSnapShotA.getValue());
                        Constant.REGISTRATION_DEFAULT_ID = (String) postSnapShotA.getValue();

                    }

                    if (postSnapShotA.getKey().equals("Start_Registration_Id")) {
                        Log.d("Start_Registration_Id", "" + postSnapShotA.getValue());
                        Constant.REGISTRATION_START_ID = (String) postSnapShotA.getValue();
                        incrementRegistrationID();
                    }

                    if (postSnapShotA.getKey().equals("Current_Registration_Id")) {
                        Log.d("Current_Registration_Id", "" + postSnapShotA.getValue());
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

        if (Constant.REGISTRATION_START_ID.equalsIgnoreCase(Constant.REGISTRATION_DEFAULT_ID)) {
            increment();
        } else {

            Constant.REGISTRATION_CURRENT_ID = Constant.REGISTRATION_DEFAULT_ID;
            Constant.REGISTRATION_START_ID = Constant.REGISTRATION_DEFAULT_ID;
            increment();

        }
    }

    private void increment() {

        int val = lastAlphaNumeric(Constant.REGISTRATION_CURRENT_ID);
        String half1 = Constant.REGISTRATION_CURRENT_ID.substring(0, val + 1);
        String half2 = Constant.REGISTRATION_CURRENT_ID.substring(val + 1, Constant.REGISTRATION_CURRENT_ID.length());

        String newHalf2 = "9" + half2;

        int lastnumberincrease = Integer.parseInt((newHalf2)) + 1;

        String newHalf2Icreae = String.valueOf(lastnumberincrease);

        String finalNumber = newHalf2Icreae.substring(1, newHalf2Icreae.length());

        Constant.FINAL_REGISTRATION_ID = half1 + finalNumber;
        Constant.REGISTRATION_CURRENT_TEMP_ID = Constant.FINAL_REGISTRATION_ID;

        Log.d("Increment Id", "" + Constant.FINAL_REGISTRATION_ID);
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
        switch (v.getId()) {
            //  case R.id.calendar:
            // Get Current Date
            //  final Dialog dialog = new Dialog(getActivity());
            //   dialog.setContentView(R.layout.datepicker);
            //  dialog.setTitle("");
            // DatePicker datePicker = dialog.findViewById(R.id.datePickerDialog);
            case R.id.button_send:
                saveaddvehicledata();
                break;

            case R.id.calendarregistereddate:
                String registerdate = "Registereddate";
                getDOB(registerdate);
                break;
            case R.id.calendarpurchasedate:
                String purchasedate = "Purchasedate";
                getDOB(purchasedate);
                break;
            case R.id.iv_previousownerpurchasedate:
                String previousownerpurchasedate = "Previousownerpurchasedate";
                getDOB(previousownerpurchasedate);
                break;

            case R.id.et_lastservicedate_transport:
                String lastservicedate = "Lastservicedate";
                getDOB(lastservicedate);
                break;

            case R.id.img_attachfile:
                openDialogForImageChoose();
                break;


            case R.id.camera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Constant.FROM_CAMERA);
                imageChooserDialog.dismiss();
                break;

            case R.id.gallery:
                Intent i = new Intent(
                        Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,Constant.FROM_GALLERY);
                imageChooserDialog.dismiss();
                break;


        }
    }

    private void openDialogForImageChoose() {

        imageChooserDialog = new Dialog(getActivity());
        imageChooserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        imageChooserDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageChooserDialog.setContentView(R.layout.attach_image_dialog);
        ImageView camera  = imageChooserDialog.findViewById(R.id.camera);
        ImageView gallery  = imageChooserDialog.findViewById(R.id.gallery);

        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        imageChooserDialog.show();
    }


    private void getDOB(final String dob) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.datepicker);
        dialog.setTitle("");
        DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePickerDialog);
        final Calendar calendar = Calendar.getInstance();
        selectedYear = String.valueOf(calendar.get(Calendar.YEAR));
        selectedMonth = String.valueOf(calendar.get(Calendar.MONTH));
        selectedDate = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, 0);

        if (selectedDate.length() == 1) {
            selectedDate = "0" + selectedDate;
        }

        if (selectedMonth.length() == 1) {
            selectedMonth = "0" + selectedMonth;
        }

        Log.e("selected date", selectedDate + "");
        Log.e("selected month", selectedMonth + "");
        Log.e("selected year", selectedYear + "");


        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                String newMonth = "", stringNewDate = "", stringyear = String.valueOf(year);

                if (String.valueOf(month + 1).length() == 1) {
                    newMonth = "0" + (month + 1);
                } else {
                    newMonth = String.valueOf(month + 1);
                }

                if (String.valueOf(dayOfMonth).length() == 1) {
                    stringNewDate = "0" + dayOfMonth;
                } else {
                    stringNewDate = String.valueOf(dayOfMonth);
                }
                Log.e("month", "" + newMonth);
                Log.e("date", "" + stringNewDate);
                Log.e("year", "" + stringyear);


                switch (dob) {

                    case "Registereddate":
                        if (selectedDate.equals(stringNewDate) && selectedMonth.equals(newMonth) && selectedYear.equals(stringyear)) {
                            String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                    + "/" + String.valueOf(stringyear);

                            registered_date.setText(date);

                            dialog.dismiss();
                        } else {

                            if (!selectedDate.equals(stringNewDate)) {
                                String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                        + "/" + String.valueOf(stringyear);

                                registered_date.setText(date);

                                dialog.dismiss();
                            } else {
                                if (!selectedMonth.equals(newMonth)) {
                                    String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                            + "/" + String.valueOf(stringyear);

                                    registered_date.setText(date);
                                    dialog.dismiss();
                                }
                            }
                        }
                        break;


                    case "Purchasedate":
                        if (selectedDate.equals(stringNewDate) && selectedMonth.equals(newMonth) && selectedYear.equals(stringyear)) {
                            String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                    + "/" + String.valueOf(stringyear);

                            purchase_date.setText(date);

                            dialog.dismiss();
                        } else {

                            if (!selectedDate.equals(stringNewDate)) {
                                String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                        + "/" + String.valueOf(stringyear);

                                purchase_date.setText(date);

                                dialog.dismiss();
                            } else {
                                if (!selectedMonth.equals(newMonth)) {
                                    String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                            + "/" + String.valueOf(stringyear);

                                    purchase_date.setText(date);
                                    dialog.dismiss();
                                }
                            }
                        }
                        break;

                    case "Previousownerpurchasedate":
                        if (selectedDate.equals(stringNewDate) && selectedMonth.equals(newMonth) && selectedYear.equals(stringyear)) {
                            String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                    + "/" + String.valueOf(stringyear);

                            preownerpurchase_date.setText(date);

                            dialog.dismiss();
                        } else {

                            if (!selectedDate.equals(stringNewDate)) {
                                String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                        + "/" + String.valueOf(stringyear);

                                preownerpurchase_date.setText(date);

                                dialog.dismiss();
                            } else {
                                if (!selectedMonth.equals(newMonth)) {
                                    String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                            + "/" + String.valueOf(stringyear);

                                    preownerpurchase_date.setText(date);
                                    dialog.dismiss();
                                }
                            }
                        }

                        break;

                    case "Lastservicedate":
                        if (selectedDate.equals(stringNewDate) && selectedMonth.equals(newMonth) && selectedYear.equals(stringyear)) {
                            String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                    + "/" + String.valueOf(stringyear);

                            mlastservicedate.setText(date);

                            dialog.dismiss();
                        } else {

                            if (!selectedDate.equals(stringNewDate)) {
                                String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                        + "/" + String.valueOf(stringyear);

                                mlastservicedate.setText(date);

                                dialog.dismiss();
                            } else {
                                if (!selectedMonth.equals(newMonth)) {
                                    String date = String.valueOf(stringNewDate) + "/" + String.valueOf(newMonth)
                                            + "/" + String.valueOf(stringyear);

                                    mlastservicedate.setText(date);
                                    dialog.dismiss();
                                }
                            }
                        }

                        break;
                }

                selectedDate = String.valueOf(dayOfMonth);
                selectedMonth = String.valueOf(month);
                selectedYear = String.valueOf(year);
            }
        });
        dialog.show();
        datePicker.setMaxDate(calendar.getTimeInMillis());
    }

    private void setdata() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Sending data...");
        if (!Constant.REGISTRATION_CURRENT_TEMP_ID.equals(Constant.FINAL_REGISTRATION_ID)) {
            lastIdref.child("Current_Registration_Id").setValue(Constant.FINAL_REGISTRATION_ID);
            Toast.makeText(getActivity(), "Registration Finished1...", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(getActivity(), MainActivity.class);
            startActivity(in);
            progressDialog.dismiss();
        } else {

            lastIdref1.child("Current_Registration_Id").setValue(Constant.FINAL_REGISTRATION_ID);
            Toast.makeText(getActivity(), "Registration Finished...", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(getActivity(), MainActivity.class);
            startActivity(in);
            progressDialog.dismiss();
        }

        Log.d(Tag, "set data");


    }
    private void nextservicedayscheck(){
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
            if(lastservicedate==null){
                lastservicedate = date;
            }
            Date dateBefore = myFormat.parse(lastservicedate);
            Date dateAfter = myFormat.parse(date);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            float daysBetween = (difference / (1000*60*60*24));
               /* You can also convert the milliseconds to days using this method
                * float daysBetween =
                *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
                */
               mnextservicedays.setText((int) daysBetween);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

