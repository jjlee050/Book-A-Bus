package com.example.book_a_bus.objectmodel;

import java.util.ArrayList;

@org.parceler.Parcel
public class BusArrivalInfo {

	private String busStopNo;
	private String busStopDesc;
	private String lat;
    private String lon;
    ArrayList<BusArrivalTimeInfo> busArrivalTimeList = new ArrayList<BusArrivalTimeInfo>();


	public BusArrivalInfo(){}

    public BusArrivalInfo(String busStopNo, String busStopDesc, String lat, String lon, ArrayList<BusArrivalTimeInfo> busArrivalTimeList) {
        this.busStopNo = busStopNo;
        this.busStopDesc = busStopDesc;
        this.lat = lat;
        this.lon = lon;
        this.busArrivalTimeList = busArrivalTimeList;
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

    public ArrayList<BusArrivalTimeInfo> getBusArrivalTimeList() {
        return busArrivalTimeList;
    }

    public void setBusArrivalTimeList(ArrayList<BusArrivalTimeInfo> busArrivalTimeList) {
        this.busArrivalTimeList = busArrivalTimeList;
    }

}
