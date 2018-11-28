package com.varadhismartek.pathshalatransportsystem.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;
import com.varadhismartek.pathshalatransportsystem.AddStop;
import com.varadhismartek.pathshalatransportsystem.Constant;
import com.varadhismartek.pathshalatransportsystem.Constants;
import com.varadhismartek.pathshalatransportsystem.Durationcalculate;
import com.varadhismartek.pathshalatransportsystem.MarkerLists;
import com.varadhismartek.pathshalatransportsystem.PlaceArrayAdapter;
import com.varadhismartek.pathshalatransportsystem.R;
import com.varadhismartek.pathshalatransportsystem.Stop_Address;
import com.varadhismartek.pathshalatransportsystem.StopviewRecyclerAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class Createroute extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,View.OnClickListener,OnMapReadyCallback {
    private String Tag = "Createroute";
    private String[] vehicletype = {"BUS", "AC BUS", "MINI BUS", "TRAVELLER"};
    private Uri filePath;

    private EditText mroutenum,latpoint,longpoint,totaldistance,travelduration;
    private String   routenum;
    private TextView bt_save,tv_decrease,tv_increase,tv_stop,tv_starttimetoschool,tv_starttimetohome;
    AutoCompleteTextView actStartingpnt,actEndingpoint,mAutoCompleteTextView;
    Geocoder geocoder;
    static LatLng origin,destiny,stop;
    public GoogleApiClient googleApiClient;
    public PlaceArrayAdapter mPlacearrayadpater;
    private ImageView stopimage;
    static GoogleMap map;
    SupportMapFragment supportMapFragment;
    double selctlat,selectlong,orignselctlat,orignselectlong,destselctlat,destselectlong;
    InputFilter[] filters ;
    private static final String API_KEY = "AIzaSyCw3hM21S93-hSuAHWjW86jlVM_rGR4vWM";
    Context context;
    LocationManager locationManager;
    Button btn_Add;
    Marker marker;
    ArrayList<Marker> markerArrayList;
    StopviewRecyclerAdapter recyclerAdapter;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;


    public static LatLngBounds latLngBounds;

    Dialog imageChooserDialog;
    String originselect,destinationselect,stopselect;




    private Spinner vehicletypespin;

    private String str_vehicle_type,starting,Stop_Latlng,names,names1,names2;
    Double latitude, longitutde;
    int i = 1;
    RecyclerView rv_stopview;
    static int stop_num;
    ArrayList<AddStop> arrayList;
    ArrayList<MarkerLists> markerLists;
    ArrayList<Stop_Address> stop_addressList;
    HashMap<Integer, Marker> markerHashMap;

    LatLng latLng;


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
        getCurrentTime();
        geocoder=new Geocoder(getActivity());
        //set filter on edit text.
        filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(2);
        stop_num = Integer.parseInt(tv_stop.getText().toString());


        //putting the marker on the map
        markerHashMap = new HashMap<>();

        //arraylist for map
        markerArrayList = new ArrayList<>();

        markerLists = new ArrayList<>();

        stop_addressList = new ArrayList<>();

        //this is for the recyclerview;
        arrayList = new ArrayList<>();

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

        //  Durationcalculate durationcalculate = new Durationcalculate(getActivity(),travelduration);
      //  Log.d("Durationtest",""+travelduration.getText());
      //  durationcalculate.execute("12.8983601,77.6179465", "28.660962100000003,77.2276794");

        // getDurationForRoute("Bommanahalli, Bengaluru, Karnataka, India", "Electronic City, Bengaluru, Karnataka, India");

        // String duration = getDurationForRoute("Bommanahalli, Bengaluru, Karnataka, India", "Electronic City, Bengaluru, Karnataka, India");
        // Log.d("Timetest", duration);
        //setting up for autocomplete textview

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                 getDurationForRoute("Bommanahalli, Bengaluru, Karnataka, India", "Electronic City, Bengaluru, Karnataka, India");
                 Log.d("Timetest", duration);

            }
        },1000);*/


        //setting an adapter for the autocompletextview

        actStartingpnt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }
                else {
                    originselect = actStartingpnt.getText().toString();
                    Log.d("Timetest", originselect);
                }
            }
        });

        mAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }
                else {
                    destinationselect = mAutoCompleteTextView.getText().toString();
                    Log.d("Timetest", destinationselect);
                }
            }
        });






       // destinationselect = mAutoCompleteTextView.getText().toString();
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

        actStartingpnt.setText(starting);
        actEndingpoint.setText(starting);
        mAutoCompleteTextView.setText(starting);


        if (googleApiClient==null||!googleApiClient.isConnected())
        {
            try {

                googleApiClient = new GoogleApiClient.Builder(getActivity())
                        .addApi(Places.GEO_DATA_API)
                        .enableAutoManage((FragmentActivity) getContext(), 1, this)
                        .addConnectionCallbacks(this)
                        .build();
            }catch (Exception e)
            {
                Log.d("exception",e.getMessage());
            }
        }



        //setting an adapter for the autocomplete textview
        mPlacearrayadpater=new PlaceArrayAdapter(getContext(),android.R.layout.simple_list_item_1,latLngBounds,null);
        actStartingpnt=view.findViewById(R.id.et_startpointtoschool);
        actEndingpoint=view.findViewById(R.id.et_startpointtohome);

        //setting the threshold for the autocomplete textview
        actStartingpnt.setThreshold(3);
        actEndingpoint.setThreshold(3);
        mAutoCompleteTextView.setThreshold(3);

        //placing the adapter for the autocomplete textview
        actStartingpnt.setAdapter(mPlacearrayadpater);
        actEndingpoint.setAdapter(mPlacearrayadpater);
        mAutoCompleteTextView.setAdapter(mPlacearrayadpater);



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
                destinationselect="select";
                actEndingpoint.setOnItemClickListener(mAutocompleteItemclick);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
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
        });

        return view;
    }

    public void getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("hh:mm:ss a");
        String strDate =  mdformat.format(calendar.getTime());
        tv_starttimetoschool.setText(strDate);
        tv_starttimetohome.setText(strDate);

    }

    private void initListner() {
        bt_save.setOnClickListener(this);
        stopimage.setOnClickListener(this);
        btn_Add.setOnClickListener(this);
    }

    private void initViews(View view) {
        bt_save = view.findViewById(R.id.button_send);
        vehicletypespin = view.findViewById(R.id.spinner_vehicle_type);
        mroutenum       = view.findViewById(R.id.editext_enterrouteno);
        tv_decrease = view.findViewById(R.id.tv_nostopdesc);
        tv_increase = view.findViewById(R.id.tv_nostopinsc);
        tv_stop = view.findViewById(R.id.tv_selectstop);
        actStartingpnt = view.findViewById(R.id.et_startpointtoschool);
        actEndingpoint = view.findViewById(R.id.et_startpointtohome);
        tv_starttimetoschool = view.findViewById(R.id.tv_starttimetoschool);
        tv_starttimetohome = view.findViewById(R.id.et_starttimetohome);
        stopimage = view.findViewById(R.id.image_picker_stop);
        mAutoCompleteTextView = view.findViewById(R.id. tv_stop_name);
        rv_stopview = view.findViewById(R.id.add_route_recyclerview);
        latpoint = view.findViewById(R.id.et_latitude);
        longpoint = view.findViewById(R.id.et_longitude);
        totaldistance = view.findViewById(R.id.et_distance);
        travelduration = view.findViewById(R.id.et_traveltime);
        btn_Add = view.findViewById(R.id.btn_addstop);



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

    private AdapterView.OnItemClickListener mAutocompleteItemclick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {


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


    ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback=new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()){
                Log.e("ResultError", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }

            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            //for getting the origin value
            if (originselect=="select"){
                LatLng latLng=place.getLatLng();
                origin=new LatLng(latLng.latitude,latLng.longitude);

                orignselctlat = latLng.latitude;
                orignselectlong = latLng.longitude;

                Log.d("ORIGINVALUE", "1"+origin);
                //declaring marker and storing the current marker
                map.addMarker(new MarkerOptions().position(origin).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN )).title(names1));
                map.moveCamera(CameraUpdateFactory.newLatLng(origin));
                map.animateCamera(CameraUpdateFactory.zoomTo(11));

              //  callback.onMapReady(map);


            }

            //for getting the destiny value if the origin value is not null
            if (destinationselect=="select"){
                LatLng latLng1 = place.getLatLng();
                destiny = new LatLng(latLng1.latitude , latLng1.longitude);
                Log.d("ORIGINVALUE", "2"+destiny);
                map.addMarker(new MarkerOptions().position(destiny).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED )).title(names2));
                map.moveCamera(CameraUpdateFactory.newLatLng(destiny));
                map.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
            //for getting the stop value if the stop value is not null
            if(stopselect=="select") {

                latLng= place.getLatLng();
                stop = new LatLng(latLng.latitude, latLng.longitude);
                Log.d("ORIGINVALUE", "3"+latLng);
                selctlat = latLng.latitude;
                selectlong = latLng.longitude;
                Stop_Latlng = selctlat+","+selectlong;
                latpoint.setText(""+selctlat);
                longpoint.setText(""+selectlong);
                Location startPoint=new Location("locationA");
                startPoint.setLatitude(orignselctlat);
                startPoint.setLongitude(orignselectlong);

                Location endPoint=new Location("locationA");
                endPoint.setLatitude(selctlat);
                endPoint.setLongitude(selectlong);
                double distance=startPoint.distanceTo(endPoint)/1000;
                if(distance!=0&& distance>=1000){
                  double dis = distance/1000;

                  totaldistance.setText(""+dis+" "+"km");

                }
                else {
                    totaldistance.setText("" + distance + " " + "km");
                    }

                Log.d("Timetest1","");




               //    String duration = getDurationForRoute("12.8983601,77.6179465", "28.660962100000003,77.2276794");
              //   String duration = getDurationForRoute("Bommanahalli, Bengaluru, Karnataka, India", "Electronic City, Bengaluru, Karnataka, India");
                //   Log.d("Timetest", duration);




            }



            if (attributions != null) {
                Toast.makeText(getContext(),"Some error while fetching",Toast.LENGTH_LONG).show();
            }
        }
    };

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
                Log.d("mohittest1", "" + data);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), photo, "Title", null);
                filePath = Uri.parse(path);
                stopimage.setImageURI(filePath);


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
                    stopimage.setImageURI(filePath);


                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send:
                saveaddroutedata();
                break;

            case R.id.image_picker_stop:
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
            case R.id.btn_addstop:
                String stop_name;
                String stop_dist;
                String stop_time;

                if (Constants.number_of_counts < stop_num)
                {
                    stop_name = mAutoCompleteTextView.getText().toString();
                    stop_dist = totaldistance.getText().toString();
                    stop_time = travelduration.getText().toString();
                    if (stop_name.isEmpty()) {
                        mAutoCompleteTextView.setError("Enter the route name");
                    }
                    else if (stop_dist.isEmpty()) {
                        totaldistance.setError("Enter the route distance");
                    }
                    else if (stop_time.isEmpty()) {
                        travelduration.setError("Enter the time");
                    }
                    else {

                        Constants.number_of_counts += 1;
                        AddStop addStop = new AddStop(String.valueOf(Constants.number_of_counts), stop_name, stop_dist, stop_time,originselect,destinationselect,Stop_Latlng);
                        arrayList.add(addStop);

                        //this is for the waypoints
                        Stop_Address stop_address = new Stop_Address(names, latitude, longitutde);
                        stop_addressList.add(stop_address);

                        markerLists.add(new MarkerLists(names, latitude, longitutde, latLng));
                        Log.d("stop1", ""+latLng);
                        //declaring marker and storing the current marker
                        marker = map.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title(names));

                        //markerHashMap.put(button_clik_count,marker);
                        markerArrayList.add(marker);


                        recyclerAdapter = new StopviewRecyclerAdapter(getContext(), arrayList, Constants.number_of_counts, stop_addressList, markerArrayList, map, markerLists,marker);

                        recyclerAdapter.notifyData(arrayList, stop_addressList,markerArrayList,markerLists);
                        mAutoCompleteTextView.setText("");
                        totaldistance.setText("");
                        travelduration.setText("");

                        rv_stopview.setAdapter(recyclerAdapter);
                    }

                }
                else
                {
                    Toast.makeText(getContext(), "you have already entered " + stop_num + " stops", Toast.LENGTH_SHORT).show();
                }
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

    private void saveaddroutedata() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Storing data...please wait");
        progressDialog.show();
        Toast.makeText(getActivity(),"save click",Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressDialog.dismiss();


            }
        },6000);


    }







    public String  getDurationForRoute(String origin, String destination){
    // - We need a context to access the API
    GeoApiContext geoApiContext = new GeoApiContext.Builder()
            .apiKey(API_KEY)
            .build();

    // - Perform the actual request
        DirectionsResult directionsResult = null;
        try {
            directionsResult = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.DRIVING)
                    .origin(origin)
                    .destination(destination)
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // - Parse the result
    DirectionsRoute route = directionsResult.routes[0];
    DirectionsLeg leg = route.legs[0];
    Duration duration = leg.duration;
    return duration.humanReadable;
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
}
