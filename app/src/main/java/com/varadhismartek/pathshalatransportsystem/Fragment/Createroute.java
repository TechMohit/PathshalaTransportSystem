package com.varadhismartek.pathshalatransportsystem.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.varadhismartek.pathshalatransportsystem.AddStop;
import com.varadhismartek.pathshalatransportsystem.AsyncTask.FetchUrl;
import com.varadhismartek.pathshalatransportsystem.Constant;
import com.varadhismartek.pathshalatransportsystem.Constants;
import com.varadhismartek.pathshalatransportsystem.JSONParsePojo;
import com.varadhismartek.pathshalatransportsystem.MarkerLists;
import com.varadhismartek.pathshalatransportsystem.PlaceArrayAdapter;
import com.varadhismartek.pathshalatransportsystem.R;
import com.varadhismartek.pathshalatransportsystem.Stop_Address;
import com.varadhismartek.pathshalatransportsystem.StopviewRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class Createroute extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, View.OnClickListener, OnMapReadyCallback {

    private static final String API_KEY = "AIzaSyCw3hM21S93-hSuAHWjW86jlVM_rGR4vWM";
    public static LatLngBounds latLngBounds;
    static LatLng origin, destiny, stop;
    static GoogleMap map;
    static LatLng templatlng;
    static String Waypoints_Url;
    static int stop_num;
    public GoogleApiClient googleApiClient;
    public PlaceArrayAdapter mPlacearrayadpater;
    Location location;

    AutoCompleteTextView actStartingpnt, actEndingpoint, mAutoCompleteTextView, mroutenum;
    Geocoder geocoder;
    SupportMapFragment supportMapFragment;
    InputFilter[] filters;
    LocationManager locationManager;
    Button btn_Add;
    Marker marker;
    int i = 1;
    StopviewRecyclerAdapter recyclerAdapter;
    RecyclerView rv_stopview;
    Switch  locationselect;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    Dialog  imageChooserDialog;
    String  originselect, destinationselect,stopselect,route_distance, route_time,
            routenum,str_vehicle_type, starting, Stop_Latlng, names, names1, names2,timepick;
    Double latitude, longitutde, latitude1, longitutde1,selctlat,selectlong,orignselctlat,orignselectlong;
    ArrayList<Marker> markerArrayList;
    ArrayList<AddStop> arrayList;
    ArrayList<MarkerLists> markerLists;
    ArrayList<Stop_Address> stop_addressList;
    ArrayList<JSONParsePojo> jsonParsePojo;
    HashMap<Integer, Marker> markerHashMap;
    ArrayList<String> vehicleReg;

    LatLng latLng,mylocate;
    EditText latpoint, longpoint, totaldistance, travelduration,starttimetoschool,starttimetohome;
    private String Tag = "Createroute";
    private String[] vehicletype = {"BUS", "AC BUS", "MINI BUS", "TRAVELLER"};
    private Uri filePath;
    private TextView bt_save, tv_decrease, tv_increase,tv_stop,seatingcapacity, vehiclenameselct,vehiclegpsnumberselect;
    private ImageView currentlocation, imageclick;
    private Spinner vehicletypespin;

    private int mHour, mMinute;

    DatabaseReference vehicleref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Vehicle_Registration");

    public Createroute() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_createroute, container, false);

        initViews(view);
        initListner();
        //getCurrentTime();
        getBusNumberfromfirebase();

        geocoder = new Geocoder(getActivity());
        //set filter on edit text.
        filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(2);
        stop_num = Integer.parseInt(tv_stop.getText().toString());
        locationselect.setChecked(true);


        //putting the marker on the map
        markerHashMap = new HashMap<>();

        //arraylist for map
        markerArrayList = new ArrayList<>();

        markerLists = new ArrayList<>();

        stop_addressList = new ArrayList<>();

        //this is for the recyclerview;
        arrayList = new ArrayList<>();
        vehicleReg = new ArrayList<>();

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
        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapfragid);
        supportMapFragment.getMapAsync(this);

        rv_stopview.setHasFixedSize(true);
        rv_stopview.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));


        locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        //setting up the latlng for the latlngbounds for the places search
        latLngBounds = new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));


        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("School/SchoolId/Transport");
        //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        //Child the root before all the push() keys are found and add a ValueEventListener()
        database.child("Driver_Module").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {

                    //Add the retrieved string to the list
                    for (DataSnapshot suggestionSnapshot1 : suggestionSnapshot.getChildren()) {
                        autoComplete.add(suggestionSnapshot1.getKey());
                        Log.d("autocom", "2" + suggestionSnapshot1.getKey() + "" + suggestionSnapshot1.getValue());
                        Log.d("autocom", "5" + mroutenum.getText().toString());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAutoCompleteTextView.setAdapter(autoComplete);
        Log.d("autocom", "4" + mAutoCompleteTextView.getText().toString());

        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String) parent.getItemAtPosition(position);
                String stname = mAutoCompleteTextView.getText().toString();
                database.child("Driver_Module").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {

                            //Add the retrieved string to the list
                            for (DataSnapshot suggestionSnapshot1 : suggestionSnapshot.getChildren())

                            {
                                if (suggestionSnapshot1.getKey().equals(mAutoCompleteTextView.getText().toString())) {


                                    //studentFName = (String) postSnapShotB.getValue();
                                    //  student_Name.setText((String) postSnapShotB.getValue());
                                    for (DataSnapshot suggestionSnapshot2 : suggestionSnapshot1.getChildren()) {
                                        if (suggestionSnapshot2.getKey().equals("Latitude")) {
                                            latpoint.setText("" + suggestionSnapshot2.getValue());
                                            latitude1 = (Double) suggestionSnapshot2.getValue();


                                        }
                                        if (suggestionSnapshot2.getKey().equals("Longitude")) {
                                            longpoint.setText("" + suggestionSnapshot2.getValue());
                                            longitutde1 = (Double) suggestionSnapshot2.getValue();

                                        }

                                    }

                                    new loadAsync().execute(latitude1, longitutde1);
                                    latLng = new LatLng(latitude1, longitutde1);


                                }

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        actStartingpnt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    originselect = actStartingpnt.getText().toString();

                }
            }
        });

        mAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    destinationselect = mAutoCompleteTextView.getText().toString();

                }
            }
        });

        tv_decrease.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                String _stringVal;

                if (i > 1) {
                    i = i - 1;
                    _stringVal = String.valueOf(i);
                    tv_stop.setText(_stringVal);

                } else {

                }

            }
        });
        tv_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _stringVal;
                if (i < 20) {
                    i = i + 1;
                    _stringVal = String.valueOf(i);
                    tv_stop.setText(_stringVal);

                }
            }
        });

        actStartingpnt.setText(starting);
        actEndingpoint.setText(starting);
        mAutoCompleteTextView.setText(starting);


        if (googleApiClient == null || !googleApiClient.isConnected()) {
            try {

                googleApiClient = new GoogleApiClient.Builder(getActivity())
                        .addApi(Places.GEO_DATA_API)
                        .enableAutoManage((FragmentActivity) getContext(), 1, this)
                        .addConnectionCallbacks(this)
                        .build();
            } catch (Exception e) {
                Log.d("exception", e.getMessage());
            }
        }

        //setting an adapter for the autocomplete textview
        mPlacearrayadpater = new PlaceArrayAdapter(getContext(), android.R.layout.simple_list_item_1, latLngBounds, null);
        actStartingpnt = view.findViewById(R.id.et_startpointtoschool);
        actEndingpoint = view.findViewById(R.id.et_startpointtohome);

        //setting the threshold for the autocomplete textview
        actStartingpnt.setThreshold(3);
        actEndingpoint.setThreshold(3);
        mAutoCompleteTextView.setThreshold(3);

        //placing the adapter for the autocomplete textview
        actStartingpnt.setAdapter(mPlacearrayadpater);
        actEndingpoint.setAdapter(mPlacearrayadpater);
        //mAutoCompleteTextView.setAdapter(autoComplete);


        actStartingpnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                originselect = "select";
                actStartingpnt.setOnItemClickListener(mAutocompleteItemclick);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        actEndingpoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                destinationselect = "select";
                actEndingpoint.setOnItemClickListener(mAutocompleteItemclick);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

       /* mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                stopselect="select";
                mAutoCompleteTextView.setOnItemClickListener(mAutocompleteItemclick);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        return view;
    }


    private void initListner() {
        bt_save.setOnClickListener(this);
        imageclick.setOnClickListener(this);
        btn_Add.setOnClickListener(this);
        locationselect.setOnClickListener(this);
        currentlocation.setOnClickListener(this);
        starttimetoschool.setOnClickListener(this);
        starttimetohome.setOnClickListener(this);
    }


    private void initViews(View view) {

        bt_save                 = view.findViewById(R.id.button_send);
        vehicletypespin         = view.findViewById(R.id.spinner_vehicle_type);
        mroutenum               = view.findViewById(R.id.editext_enterrouteno);
        tv_decrease             = view.findViewById(R.id.tv_nostopdesc);
        tv_increase             = view.findViewById(R.id.tv_nostopinsc);
        tv_stop                 = view.findViewById(R.id.tv_selectstop);
        actStartingpnt          = view.findViewById(R.id.et_startpointtoschool);
        actEndingpoint          = view.findViewById(R.id.et_startpointtohome);
        starttimetoschool       = view.findViewById(R.id.et_starttimetoschool);
        starttimetohome         = view.findViewById(R.id.et_starttimetohome);
        mAutoCompleteTextView   = view.findViewById(R.id.tv_stop_name);
        rv_stopview             = view.findViewById(R.id.add_route_recyclerview);
        latpoint                = view.findViewById(R.id.et_latitude);
        longpoint               = view.findViewById(R.id.et_longitude);
        totaldistance           = view.findViewById(R.id.et_distance);
        travelduration          = view.findViewById(R.id.et_traveltime);
        btn_Add                 = view.findViewById(R.id.btn_addstop);
        locationselect          = view.findViewById(R.id.switchLoc);
        currentlocation         = view.findViewById(R.id.currentloc);
        imageclick              = view.findViewById(R.id.iv_stop);
        seatingcapacity         = view.findViewById(R.id.tv_seatingcapacity);
        vehiclenameselct        = view.findViewById(R.id.tv_vehicle_nameselct);
        vehiclegpsnumberselect  = view.findViewById(R.id.tv_vehicle_nameselct);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send:
                saveaddroutedata();
                break;

            case R.id.iv_stop:
                openDialogForImageChoose();
                break;

            case R.id.camera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Constant.FROM_CAMERA);
                imageChooserDialog.dismiss();
                break;

            case R.id.gallery:
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Constant.FROM_GALLERY);
                imageChooserDialog.dismiss();
                break;
            case R.id.btn_addstop:
                String stop_name;
                String stop_dist;
                String stop_time;

                if (Constants.number_of_counts < stop_num) {
                    stop_name = mAutoCompleteTextView.getText().toString();
                    stop_dist = totaldistance.getText().toString();
                    stop_time = travelduration.getText().toString();
                    /*if (stop_name.isEmpty()) {
                        mAutoCompleteTextView.setError("Enter the route name");
                    }
                    else if (stop_dist.isEmpty()) {
                        totaldistance.setError("Enter the route distance");
                    }
                    else if (stop_time.isEmpty()) {
                        travelduration.setError("Enter the time");
                    }
                    else {*/

                    Constants.number_of_counts += 1;
                    AddStop addStop = new AddStop(String.valueOf(Constants.number_of_counts), stop_name, stop_dist, stop_time, originselect, destinationselect, Stop_Latlng);
                    arrayList.add(addStop);

                    //this is for the waypoints
                    Stop_Address stop_address = new Stop_Address(names, latitude, longitutde);
                    stop_addressList.add(stop_address);

                    markerLists.add(new MarkerLists(names, latitude, longitutde, latLng));
                    Log.d("stop1", "" + latLng);
                    //declaring marker and storing the current marker
                    marker = map.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title(names));

                    //markerHashMap.put(button_clik_count,marker);
                    markerArrayList.add(marker);


                    recyclerAdapter = new StopviewRecyclerAdapter(getContext(), arrayList, Constants.number_of_counts, stop_addressList, markerArrayList, map, markerLists, marker);

                    recyclerAdapter.notifyData(arrayList, stop_addressList, markerArrayList, markerLists);
                    // mAutoCompleteTextView.setText("");
                    // totaldistance.setText("");
                    //travelduration.setText("");

                    rv_stopview.setAdapter(recyclerAdapter);
                    addroute();
                }

                // }
                else {
                    Toast.makeText(getContext(), "you have already entered " + stop_num + " stops", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.switchLoc:

                if (locationselect.isChecked()) {
                    currentlocation.setVisibility(View.GONE);
                    mAutoCompleteTextView.setText("");
                    latpoint.setText("");
                    longpoint.setText("");

                } else {
                    currentlocation.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.currentloc:
                currentlocation();
                break;

            case R.id.et_starttimetoschool:
                timepick ="A";
                selecttime();
                break;



            case R.id.et_starttimetohome:
                timepick = "B";
                Log.d("time","1");
                selecttime();
                break;


        }
    }




    ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("ResultError", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }

            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            //for getting the origin value
            if (originselect == "select") {
                LatLng latLng = place.getLatLng();
                origin = new LatLng(latLng.latitude, latLng.longitude);
                templatlng = new LatLng(latLng.latitude, latLng.longitude);
                orignselctlat = latLng.latitude;
                orignselectlong = latLng.longitude;
                //declaring marker and storing the current marker
                setmarker(origin);


            }

            //for getting the destiny value if the origin value is not null
            if (destinationselect == "select") {
                LatLng latLng1 = place.getLatLng();
                destiny = new LatLng(latLng1.latitude, latLng1.longitude);
                setmarker(destiny);
                //   map.addMarker(new MarkerOptions().position(destiny).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED )).title(names2));
                //  map.moveCamera(CameraUpdateFactory.newLatLng(destiny));
                //  map.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
            //for getting the stop value if the stop value is not null
            if (stopselect == "select") {

                latLng = place.getLatLng();
                stop = new LatLng(latLng.latitude, latLng.longitude);
                selctlat = latLng.latitude;
                selectlong = latLng.longitude;
                Stop_Latlng = selctlat + "," + selectlong;
                latpoint.setText("" + selctlat);
                longpoint.setText("" + selectlong);
                //    String duration = getDurationForRoute("12.8983601,77.6179465", "28.660962100000003,77.2276794");
                //   String duration = getDurationForRoute("Bommanahalli, Bengaluru, Karnataka, India", "Electronic City, Bengaluru, Karnataka, India");
                //   Log.d("Timetest", duration);


            }


            if (attributions != null) {
                Toast.makeText(getContext(), "Some error while fetching", Toast.LENGTH_LONG).show();
            }
        }
    };

    private AdapterView.OnItemClickListener mAutocompleteItemclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            final PlaceArrayAdapter.PlaceAutoComplete item = mPlacearrayadpater.getItem(i);
            final String placeId = String.valueOf(item.PlaceId);

            Log.i("LocationSelected", "Selected: " + item.Description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(googleApiClient, placeId);

            //write the setback call method here for loading the results we clicked

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);


            Log.i("Fetchingdetails", "Fetching details for ID: " + item.PlaceId);

        }
    };

    public void getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("hh:mm:ss a");
        String strDate = mdformat.format(calendar.getTime());
        starttimetoschool.setText(strDate);
        starttimetohome.setText(strDate);

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Createroute", "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(getContext(),
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlacearrayadpater.setmGoogleApiClient(googleApiClient);
        Log.i("Addroute", "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlacearrayadpater.setmGoogleApiClient(null);
        Log.e("ConnectionSuspended", "Google Places API connection suspended.");

    }

    private void setmarker(LatLng origin) {
        map.addMarker(new MarkerOptions().position(origin).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(names1));
        map.moveCamera(CameraUpdateFactory.newLatLng(origin));
        map.animateCamera(CameraUpdateFactory.zoomTo(11));
    }

    @Override
    public void onPause() {
        super.onPause();

        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.stopAutoManage((FragmentActivity) getContext());
            googleApiClient.disconnect();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), photo, "Title", null);
                filePath = Uri.parse(path);
                imageclick.setImageURI(filePath);


            }
        }


        if (requestCode == Constant.FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri contentURI = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);

                    int nh = (int) (bitmap.getHeight() * (256.0 / bitmap.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 256, nh, true);
                    String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), scaled, "Title", null);
                    filePath = Uri.parse(path);
                    imageclick.setImageURI(filePath);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }



    private void selecttime() {
        Log.d("time","1");
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if(timepick =="A") {
                            starttimetoschool.setText(hourOfDay + ":" + minute);
                        }
                        if(timepick =="B") {
                            starttimetohome.setText(hourOfDay + ":" + minute);
                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void currentlocation() {

        if (!isGPSEnabled)

        {
            Toast.makeText(getContext(), "Check your network settings", Toast.LENGTH_LONG).show();
        } else {

            //THIS CODE FOR THE GPS NETWORK PROVIDER
            if (isGPSEnabled) {
                if (location == null) {

                    Log.d("activity", "RLOC: GPS Enabled");

                    if (locationManager != null) {

                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }

                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        Log.d("location&manager", location + " " + locationManager.toString());

                        if (location != null) {
                            Log.d("activity", "RLOC: loc by GPS");

                            Double Fetchlatitude = location.getLatitude();
                            Double Fetchlongitude = location.getLongitude();

                            mylocate = new LatLng(Fetchlatitude, Fetchlongitude);

                            List<Address> addresses;
                            String name;
                            //geocoder=new Geocoder(getActivity(),Locale.getDefault());

                            latLng = mylocate;
                            latpoint.setText("" + latLng.latitude);
                            longpoint.setText("" + latLng.longitude);

                            try {

                                addresses = geocoder.getFromLocation(mylocate.latitude, mylocate.longitude, 1);
                                //addresses.get(0).getAddressLine(0);
                                name = addresses.get(0).getAddressLine(0);

                                mAutoCompleteTextView.setText(name);
                                Toast.makeText(getContext(), name + " " + mylocate, Toast.LENGTH_LONG).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                        location = null;


                    }

                }
            }

        }
    }

    private void openDialogForImageChoose() {

        imageChooserDialog = new Dialog(getActivity());
        imageChooserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        imageChooserDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageChooserDialog.setContentView(R.layout.attach_image_dialog);
        ImageView camera = imageChooserDialog.findViewById(R.id.camera);
        ImageView gallery = imageChooserDialog.findViewById(R.id.gallery);

        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        imageChooserDialog.show();
    }

    private void saveaddroutedata() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Storing data...please wait");
        progressDialog.show();
        Toast.makeText(getActivity(), "save click", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressDialog.dismiss();


            }
        }, 6000);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getContext(), "map loaded", Toast.LENGTH_LONG).show();
        this.map = googleMap;
        /*LatLng latLngtest = new LatLng(12.8399389, 77.6770031);



        map.addMarker(new MarkerOptions().position(latLngtest).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("start address"));
        map.addMarker(new MarkerOptions().position(latLngtest).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("End address"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLngtest));

        map.moveCamera(CameraUpdateFactory.newLatLng(latLngtest));
        map.animateCamera(CameraUpdateFactory.zoomTo(11));*/

    }

    private void getTheDistanceAuto(Double latitude, Double longitutde) {

        try {

            String origin_val = "origin=" + templatlng.latitude + "," + templatlng.longitude;
            String dest_value = "destination=" + latitude + "," + longitutde;
            String sensor = "sensor=false";


            String parameters = origin_val + "&" + dest_value + "&" + sensor;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + API_KEY;


            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("ieiowr", response.toString());
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONArray jRoutes;

                    try {
                        jRoutes = jsonObject.getJSONArray("routes");

                        for (int i = 0; i < jRoutes.length(); i++) {
                            JSONObject jsonRoute = jRoutes.getJSONObject(i);
                            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");


                            for (int m = 0; m < jsonLegs.length(); m++) {
                                JSONObject jsonLeg = jsonLegs.getJSONObject(m);

                                JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                                JSONObject jsonTime = jsonLeg.getJSONObject("duration");

                                route_distance = jsonDistance.getString("text");
                                route_time = jsonTime.getString("text");

                            }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("erbjubre", route_distance + " " + route_time);

                    totaldistance.setText(route_distance);
                    travelduration.setText(route_time);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            requestQueue.add(stringRequest);


        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }

    private void addroute() {

        try {

            String origin_val = "origin=" + origin.latitude + "," + origin.longitude;
            String dest_value = "destination=" + latLng.latitude + "," + latLng.longitude;
            String sensor = "sensor=false";


            StringBuilder builder = new StringBuilder("");
            builder.append("waypoints=");
            String stpname;

            Double lat, lng;

            for (int i = 0; i < stop_addressList.size(); i++) {

                Stop_Address stop_address = stop_addressList.get(i);
                lat = stop_address.getLatitude();
                lng = stop_address.getLongitude();
                builder.append(lat).append(",").append(lng).append("").append("|");

                if (i == stop_addressList.size() - 1) {
                    builder.append(lat).append(",").append(lng);
                }

            }


            String parameters = origin_val + "&" + dest_value + "&" + sensor + "&" + builder;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + API_KEY;


            Log.d("viewstops", url);
            Waypoints_Url = url;
            Log.d("Waypoints_Url", Waypoints_Url);

            //  stops_ref = databaseReference.child("Routestops");
            //  stops_ref.child("url").setValue(Waypoints_Url);

            // stops_ref.child("stoplist").setValue(arrayList);


            //fetching the detailste of that url
            FetchUrl fetchUrl = new FetchUrl(getContext(), map);
            fetchUrl.execute(url);

            // map.moveCamera(CameraUpdateFactory.newLatLng(origin));

            map.moveCamera(CameraUpdateFactory.newLatLng(origin));
            map.animateCamera(CameraUpdateFactory.zoomTo(11));

        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private List<LatLng> decodePoly(String polyline) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = polyline.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = polyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = polyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;

    }

    private class loadAsync extends AsyncTask<Double, Double, String> {
        private ProgressDialog dialog;


        @Override
        protected String doInBackground(Double... doubles) {

            getTheDistanceAuto(doubles[0], doubles[1]);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Fetching dist and time..Please Wait");
            dialog.show();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
        }
    }

    public class JParser {
        GoogleMap map;
        Context mContext;
        String distance;
        String duration;
        String start_address;
        String end_address;


        public JParser(Context mContext, GoogleMap googleMap) {
            jsonParsePojo = new ArrayList();
            this.map = googleMap;
            this.mContext = mContext;

        }

        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

            List<List<HashMap<String, String>>> routes = new ArrayList<>();
            JSONArray jRoutes;
            JSONArray jSteps;
            JSONArray jLegs;

            try {
                jRoutes = jObject.getJSONArray("routes");
                for (int i = 0; i < jRoutes.length(); i++) {
                    JSONObject jsonRoute = jRoutes.getJSONObject(i);
                    JSONArray jsonLegs = jsonRoute.getJSONArray("legs");

                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    for (int m = 0; m < jsonLegs.length(); m++) {
                        JSONObject jsonLeg = jsonLegs.getJSONObject(m);

                        JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                        JSONObject jsonTime = jsonLeg.getJSONObject("duration");

                        distance = jsonDistance.getString("text");
                        duration = jsonTime.getString("text");
                        start_address = jsonLeg.getString("start_address");
                        end_address = jsonLeg.getString("end_address");
                        //jsonParsePojo.add(distance);
                        Log.d("Distance", "Distance: " + distance + "\n" + "Duration: " + duration + "\n" + "Start Address: " + start_address + "\n" + "End Address: " + end_address);
                        //   Info.mapArrayList.add(new JSONParsePojo(distance , duration , start_address, end_address));
                    }

                    List path = new ArrayList();

                    for (int j = 0; j < jLegs.length(); j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");


                        for (int k = 0; k < jSteps.length(); k++) {
                            JSONObject obj = jSteps.getJSONObject(i);
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("lat", Double.toString((list.get(l)).latitude));
                                hm.put("lng", Double.toString((list.get(l)).longitude));
                                path.add(hm);

                            }
                        }
                        routes.add(path);
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }


            return routes;

        }
    }

    private void getBusNumberfromfirebase(){
        Log.d("firebasetest", ""+Constant.FINAL_REGISTRATION_ID);
        vehicleref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(vehicleReg.size()>=0){
                    vehicleReg.clear();

                }

                Log.d("firebasetest", ""+dataSnapshot.getKey());

                for (DataSnapshot postSnapShotA : dataSnapshot.getChildren()){
                    Log.d("firebasetest", ""+postSnapShotA.getKey());
                    vehicleReg.add(postSnapShotA.getKey());


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}



