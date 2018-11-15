package com.varadhismartek.pathshalatransportsystem;

/**
 * Created by varadhi22 on 24/10/18.
 */

public class TransBarrierModel {
    String route_id;
    String no_stop;
    String manufacturename;
    String modelnumber;
    String manufactureyear;
    String seatingcapacity;
    String registeringauthority;
    String registeringstate;
    String registereddate;
    String purchasedate;
    String preownerpurchasedate;
    String previousownerremark;
    String preownerpurchasecost;
    String previousownername;
    String vehicle_regno1;
    String vehicle_name1;
    String gps_details;
    String str_vehicle_type;
    String str_body_type;
    String chasisnumber;
    String enginenumber;
    String totalfreeservice;
    String remanningservice;
    String lastservicedate;
    String nextservicedays;
    String nextservicekms;
    String insurancetype;
    String insurancenum;
    String insurancedate;
    String insurancerenewdate;
    String agentname;
    String agentid;
    String insurancecomname;
    String otherdocinsurancenum,rcnumber,nocnumber,pollutioncertificatenum,
            pollutioncertificateissuedate,pollutioncertificaterenewdate,fitnesscertificatenum,
            fitnesscertificateissuedate,fitnesscertificaterenewdate,taxpermitnum,taxpayabledate,taxpayableamount;
    String fdvehiclepurchaseprice,fdbankname,fdloanaccountnum,fdloanamount,fdloaninterestrate,
            fddownpayment,fdlaonapprovedate,fdloanduedate,fdloanenddate,fdloanemiamount,fdmaintaincecharges,fdreparingcharges,fdremarks;
    String agentcontnum;
    String insurancecomnum;
    String insurancenextrenewdate;

    public String getFdvehiclepurchaseprice() {
        return fdvehiclepurchaseprice;
    }

    public String getFdbankname() {
        return fdbankname;
    }

    public String getFdloanaccountnum() {
        return fdloanaccountnum;
    }

    public String getFdloanamount() {
        return fdloanamount;
    }

    public String getFdloaninterestrate() {
        return fdloaninterestrate;
    }

    public String getFddownpayment() {
        return fddownpayment;
    }

    public String getFdlaonapprovedate() {
        return fdlaonapprovedate;
    }

    public String getFdloanduedate() {
        return fdloanduedate;
    }

    public String getFdloanenddate() {
        return fdloanenddate;
    }

    public String getFdloanemiamount() {
        return fdloanemiamount;
    }

    public String getFdmaintaincecharges() {
        return fdmaintaincecharges;
    }

    public String getFdreparingcharges() {
        return fdreparingcharges;
    }

    public String getFdremarks() {
        return fdremarks;
    }



    public String getOtherdocinsurancenum() {
        return otherdocinsurancenum;
    }

    public String getRcnumber() {
        return rcnumber;
    }

    public String getNocnumber() {
        return nocnumber;
    }

    public String getPollutioncertificatenum() {
        return pollutioncertificatenum;
    }

    public String getPollutioncertificateissuedate() {
        return pollutioncertificateissuedate;
    }

    public String getPollutioncertificaterenewdate() {
        return pollutioncertificaterenewdate;
    }

    public String getFitnesscertificatenum() {
        return fitnesscertificatenum;
    }

    public String getFitnesscertificateissuedate() {
        return fitnesscertificateissuedate;
    }

    public String getFitnesscertificaterenewdate() {
        return fitnesscertificaterenewdate;
    }

    public String getTaxpermitnum() {
        return taxpermitnum;
    }

    public String getTaxpayabledate() {
        return taxpayabledate;
    }

    public String getTaxpayableamount() {
        return taxpayableamount;
    }



    public String getAgentname() {
        return agentname;
    }

    public String getAgentid() {
        return agentid;
    }

    public String getInsurancecomname() {
        return insurancecomname;
    }

    public String getAgentcontnum() {
        return agentcontnum;
    }

    public String getInsurancecomnum() {
        return insurancecomnum;
    }



    public String getInsurancetype() {
        return insurancetype;
    }

    public String getInsurancenum() {
        return insurancenum;
    }

    public String getInsurancedate() {
        return insurancedate;
    }

    public String getInsurancerenewdate() {
        return insurancerenewdate;
    }

    public String getInsurancenextrenewdate() {
        return insurancenextrenewdate;
    }




    public String getTotalfreeservice() {
        return totalfreeservice;
    }

    public String getRemanningservice() {
        return remanningservice;
    }

    public String getLastservicedate() {
        return lastservicedate;
    }

    public String getNextservicedays() {
        return nextservicedays;
    }

    public String getNextservicekms() {
        return nextservicekms;
    }


    public String getRegistereddate() {
        return registereddate;
    }

    public String getPurchasedate() {
        return purchasedate;
    }

    public String getPreownerpurchasedate() {
        return preownerpurchasedate;
    }

    public String getPreviousownerremark() {
        return previousownerremark;
    }

    public String getPreownerpurchasecost() {
        return preownerpurchasecost;
    }

    public String getPreviousownername() {
        return previousownername;
    }

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
                             String registeringauthority,String registeringstate,String registereddate,String purchasedate,
                             String preownerpurchasedate,String previousownerremark,String preownerpurchasecost,String previousownername){
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
        this.registereddate = registereddate;
        this.purchasedate = purchasedate;
        this.preownerpurchasedate = preownerpurchasedate;
        this.previousownerremark = previousownerremark;
        this.preownerpurchasecost = preownerpurchasecost;
        this.previousownername = previousownername;

    }
    public TransBarrierModel( String totalfreeservice,String remanningservice, String lastservicedate, String nextservicedays, String nextservicekms  ) {
        this.totalfreeservice = totalfreeservice;
        this.remanningservice = remanningservice;
        this.lastservicedate = lastservicedate;
        this.nextservicedays = nextservicedays;
        this.nextservicekms = nextservicekms;

    }
    public TransBarrierModel( String insurancetype,String insurancenum, String insurancedate,
                              String insurancerenewdate, String insurancenextrenewdate,String agentname,String agentid,String insurancecomname,String agentcontnum,String insurancecomnum)
    {
        this.insurancetype = insurancetype;
        this.insurancenum = insurancenum;
        this.insurancedate = insurancedate;
        this.insurancerenewdate = insurancerenewdate;
        this.insurancenextrenewdate = insurancenextrenewdate;
        this.agentname = agentname;
        this.agentid = agentid;
        this.insurancecomname = insurancecomname;
        this.agentcontnum = agentcontnum;
        this.insurancecomnum = insurancecomnum;


    }
    public TransBarrierModel(String otherdocinsurancenum,String rcnumber,String nocnumber,String pollutioncertificatenum,
                             String pollutioncertificateissuedate,String pollutioncertificaterenewdate,String fitnesscertificatenum,
                             String fitnesscertificateissuedate,String fitnesscertificaterenewdate,String taxpermitnum,String taxpayabledate,
                             String taxpayableamount)
    {
        this.otherdocinsurancenum = otherdocinsurancenum;
        this.rcnumber = rcnumber;
        this.nocnumber = nocnumber;
        this.pollutioncertificatenum = pollutioncertificatenum;
        this.pollutioncertificateissuedate = pollutioncertificateissuedate;
        this.pollutioncertificaterenewdate = pollutioncertificaterenewdate;
        this.fitnesscertificatenum = fitnesscertificatenum;
        this.fitnesscertificateissuedate = fitnesscertificateissuedate;
        this.fitnesscertificaterenewdate = fitnesscertificaterenewdate;
        this.taxpermitnum = taxpermitnum;
        this.taxpayabledate = taxpayabledate;
        this.taxpayableamount = taxpayableamount;

    }

    public TransBarrierModel(String fdvehiclepurchaseprice,String fdbankname,String fdloanaccountnum,String fdloanamount,String fdloaninterestrate,
                             String fddownpayment,String fdlaonapprovedate,String fdloanduedate,String fdloanenddate,String fdloanemiamount,String fdmaintaincecharges,String fdreparingcharges,String fdremarks)
    {
       this.fdvehiclepurchaseprice = fdvehiclepurchaseprice;
       this.fdbankname = fdbankname;
       this.fdloanaccountnum = fdloanaccountnum;
       this.fdloanamount = fdloanamount;
       this.fdloaninterestrate = fdloaninterestrate;
       this.fddownpayment = fddownpayment;
       this.fdlaonapprovedate = fdlaonapprovedate;
       this.fdloanduedate = fdloanduedate;
       this.fdloanenddate = fdloanenddate;
       this.fdloanemiamount = fdloanemiamount;
       this.fdmaintaincecharges = fdmaintaincecharges;
       this.fdreparingcharges = fdreparingcharges;
       this.fdremarks = fdremarks;

    }

}
