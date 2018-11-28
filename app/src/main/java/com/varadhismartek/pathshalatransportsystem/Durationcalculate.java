package com.varadhismartek.pathshalatransportsystem;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;
import com.varadhismartek.pathshalatransportsystem.Fragment.Createroute;

import java.io.IOException;

/**
 * Created by varadhi22 on 28/11/18.
 */

public class Durationcalculate extends AsyncTask<String,String,String> {

    static String s;
    Context context ;
    EditText editText ;
    private static final String API_KEY1 = "AIzaSyCw3hM21S93-hSuAHWjW86jlVM_rGR4vWM";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }
    public Durationcalculate(Context context, EditText editText){
       this.context = context;
       this.editText = editText;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        editText.setText(s);


    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d("Durationtest1","1");

       String duration = getDurationForRoute(strings[0],strings[1]);
        return duration;
    }

    public String  getDurationForRoute(String origin, String destination){
        // - We need a context to access the API
        Log.d("Durationtest1","2");
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(API_KEY1)
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
