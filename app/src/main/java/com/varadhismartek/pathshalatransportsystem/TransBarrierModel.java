package com.varadhismartek.pathshalatransportsystem;

/**
 * Created by varadhi22 on 24/10/18.
 */

public class TransBarrierModel {
    String route_id;
    String no_stop;

    public String getVehicle_regno1() {
        return vehicle_regno1;
    }

    public String getVehicle_name1() {
        return vehicle_name1;
    }

    public String getGps_details() {
        return gps_details;
    }

    public String getStr_vehicle_type() {
        return str_vehicle_type;
    }

    String vehicle_regno1;
    String vehicle_name1;
    String gps_details;
    String str_vehicle_type;
    String str_body_type;
    String chasisnumber;
    String enginenumber;

    public String getStr_body_type() {
        return str_body_type;
    }

    public String getChasisnumber() {
        return chasisnumber;
    }

    public String getEnginenumber() {
        return enginenumber;
    }

    public String getManufacturename() {
        return manufacturename;
    }

    public String getModelnumber() {
        return modelnumber;
    }

    public String getManufactureyear() {
        return manufactureyear;
    }

    public String getSeatingcapacity() {
        return seatingcapacity;
    }

    public String getRegisteringauthority() {
        return registeringauthority;
    }

    public String getRegisteringstate() {
        return registeringstate;
    }

    String manufacturename;
    String modelnumber;
    String manufactureyear;
    String seatingcapacity;
    String registeringauthority;
    String registeringstate;

    public String getRoute_id() {
        return route_id;
    }

    public String getNo_stop() {
        return no_stop;
    }

    public TransBarrierModel(String route_id, String no_stop) {
        this.route_id = route_id;
        this.no_stop = no_stop;

    }
    public TransBarrierModel(String str_vehicle_type,String vehicle_regno1, String vehicle_name1,String gps_details  ) {
        this.str_vehicle_type = str_vehicle_type;
        this.vehicle_regno1 = vehicle_regno1;
        this.vehicle_name1 = vehicle_name1;
        this.gps_details = gps_details;

    }
    public TransBarrierModel(String str_body_type,String chasisnumber,String enginenumber,String manufacturename,String modelnumber,String manufactureyear,String seatingcapacity,
                             String registeringauthority,String registeringstate){
        this.str_body_type = str_body_type;
        this.chasisnumber = chasisnumber;
        this.enginenumber = enginenumber;
        this.manufacturename = manufacturename;
        this.modelnumber = modelnumber;
        this.manufacturename = manufacturename;
        this.manufactureyear = manufactureyear;
        this.seatingcapacity = seatingcapacity;
        this.registeringauthority = registeringauthority;
        this.registeringstate = registeringstate;
    }
}
