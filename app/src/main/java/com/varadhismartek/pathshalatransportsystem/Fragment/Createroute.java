package com.varadhismartek.pathshalatransportsystem.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;
import com.varadhismartek.pathshalatransportsystem.Constant;
import com.varadhismartek.pathshalatransportsystem.PlaceArrayAdapter;
import com.varadhismartek.pathshalatransportsystem.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Createroute extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,View.OnClickListener,OnMapReadyCallback {
    private String Tag = "Createroute";
    private String[] vehicletype = {"BUS", "AC BUS", "MINI BUS", "TRAVELLER"};
    private Uri filePath;

    private EditText mroutenum,latpoint,longpoint,totaldistance;
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
    String originselect,destinationselect;


    public static LatLngBounds latLngBounds;

    Dialog imageChooserDialog;




    private Spinner vehicletypespin;

    private String str_vehicle_type,starting;
    int i = 1;
    RecyclerView rv_stopview;



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

        //setting up for autocomplete textview


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
                origin=null;
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
                destiny=null;
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
                stop=null;
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
            if (origin==null){
                LatLng latLng=place.getLatLng();
                origin=new LatLng(latLng.latitude,latLng.longitude);

                orignselctlat = latLng.latitude;
                orignselectlong = latLng.longitude;

                Log.d("ORIGINVALUE", ""+origin);



            }

            //for getting the destiny value if the origin value is not null
            else if (origin!=null){
                LatLng latLng1 = place.getLatLng();
                destiny = new LatLng(latLng1.latitude , latLng1.longitude);
                Log.d("DESTINYVALUE", ""+destiny);
            }
            //for getting the stop value if the stop value is not null
            if(stop==null) {
                LatLng latLng1 = place.getLatLng();
                stop = new LatLng(latLng1.latitude, latLng1.longitude);
                Log.d("stop", ""+stop);
                selctlat = latLng1.latitude;
                selectlong = latLng1.longitude;
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




                 //  String duration = getDurationForRoute("12.8983601,77.6179465", "28.660962100000003,77.2276794");
                 String duration = getDurationForRoute("Bommanahalli, Bengaluru, Karnataka, India", "Electronic City, Bengaluru, Karnataka, India");
                 Log.d("Timetest", duration);




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

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public String getDurationForRoute(String origin, String destination){
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
}
