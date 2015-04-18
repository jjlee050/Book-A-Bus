package com.example.book_a_bus.objectmodel;

public class BusArrivalInfo {

	private String busStopNo;
	private String busStopDesc;
	private String lat;
    private String lon;
    private String serviceNo;
    private String status;
    private String estimated_Arr;
    private String load;
    private String feature;
    private String estimated_ArrS;
    private String loadS;
    private String featureS;

	public BusArrivalInfo(){}

    public BusArrivalInfo(String busStopNo, String busStopDesc, String lat, String lon, String serviceNo, String status, String estimated_Arr, String load, String feature, String estimated_ArrS, String loadS, String featureS) {
        this.busStopNo = busStopNo;
        this.busStopDesc = busStopDesc;
        this.lat = lat;
        this.lon = lon;
        this.serviceNo = serviceNo;
        this.status = status;
        this.estimated_Arr = estimated_Arr;
        this.load = load;
        this.feature = feature;
        this.estimated_ArrS = estimated_ArrS;
        this.loadS = loadS;
        this.featureS = featureS;
    }

    public String getBusStopNo() {
        return busStopNo;
    }

    public void setBusStopNo(String busStopNo) {
        this.busStopNo = busStopNo;
    }

    public String getBusStopDesc() {
        return busStopDesc;
    }

    public void setBusStopDesc(String busStopDesc) {
        this.busStopDesc = busStopDesc;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstimated_Arr() {
        return estimated_Arr;
    }

    public void setEstimated_Arr(String estimated_Arr) {
        this.estimated_Arr = estimated_Arr;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getEstimated_ArrS() {
        return estimated_ArrS;
    }

    public void setEstimated_ArrS(String estimated_ArrS) {
        this.estimated_ArrS = estimated_ArrS;
    }

    public String getLoadS() {
        return loadS;
    }

    public void setLoadS(String loadS) {
        this.loadS = loadS;
    }

    public String getFeatureS() {
        return featureS;
    }

    public void setFeatureS(String featureS) {
        this.featureS = featureS;
    }
}
