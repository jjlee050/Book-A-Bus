package com.example.book_a_bus;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_a_bus.listeners.TaskListener;
import com.example.book_a_bus.objectmodel.BusArrivalInfo;
import com.example.book_a_bus.objectmodel.SBSInfo;
import com.example.book_a_bus.tasks.BusArrivalTask;

import java.util.ArrayList;

public class MainActivity extends Activity implements TaskListener, LocationListener {

	/*
	 * This is for BUS ROUTES ・SBST BUS SERVICE INFO API
	 */

    TextView test;
    private LocationManager locationManager;
    private String provider;
    private String currentLat, currentLon;

    ArrayList<SBSInfo> sbsInfoList = new ArrayList<SBSInfo>();

    ArrayList<BusArrivalInfo> busArrList = new ArrayList<BusArrivalInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = (TextView) findViewById(R.id.test);

        //To Start the task for Bus Services
        //BusServiceInfoTask s = new BusServiceInfoTask(MainActivity.this);
      // s.executeGetSBSInfo("SBST");


        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);

        } else {
            currentLat = "Location not available";
            currentLon = "Location not available";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 5, this); // provider , minTime, minDistance, listener
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        currentLat = String.valueOf(lat);
        currentLon = String.valueOf(lng);
        //To Start the task for Bus Arrival
        BusArrivalTask bat = new BusArrivalTask(MainActivity.this);
        bat.executeGetArrivalTimeInfo(currentLat, currentLon, "500"); //500 is the distance in metres
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPreExecute(Class<?> cname) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProgressUpdate(Class<?> cname, Object... values) {
        // TODO Auto-generated method stub
        for (Object value : values)
        {
            if(value instanceof SBSInfo)
            {
                //Store into a arraylist.
                SBSInfo sbsInfo = (SBSInfo) value;
                sbsInfoList.add(sbsInfo);
            }
            else if (value instanceof BusArrivalInfo)
            {
                BusArrivalInfo baInfo = (BusArrivalInfo) value;
                busArrList.add(baInfo);
            }
        }
    }

    @Override
    public void onPostExecute(Class<?> cname, Object result) {
        // TODO Auto-generated method stub
        String temp = "";
        //Display it on the textview.
       /*for(int i = 0; i < sbsInfoList.size(); i++)
        {
            temp += "Bus Service Number: " + sbsInfoList.get(i).getBusServiceNo()
                    + "\nBus Service: " + sbsInfoList.get(i).getBusService()
                    + "\nLoop Direction: " + sbsInfoList.get(i).getDirectionNo()
                    + "\nCategory: " + sbsInfoList.get(i).getCategory()
                    + "\nStarting Location: " + sbsInfoList.get(i).getStartLocation()
                    + "\nEnding Location: " + sbsInfoList.get(i).getEndLocation()
                    + "\nFreq Am Peak: " + sbsInfoList.get(i).getAmPeakFreq()
                    + "\nFreq Am Off Peak: " + sbsInfoList.get(i).getAmOffPeakFreq()
                    + "\nFreq Pm Peak: " + sbsInfoList.get(i).getPmPeakFreq()
                    + "\nFreq Pm Off Peak: " + sbsInfoList.get(i).getPmOffPeakFreq()
                    + "\nLoop Location: " + sbsInfoList.get(i).getLocationLoop() + "\n\n";

        }*/

       /* for(int i = 0; i < busStopList.size(); i++)
        {
            temp += "Bus Code: " + busStopList.get(i).getBusStopCode()
                    + "\nBus Road: " + busStopList.get(i).getBusStopRoad()
                    + "\nBus Desc: " + busStopList.get(i).getBusStopDesc() + "\n\n";
        }*/



       /* for(int i = 0; i < busArrList.size(); i++)
        {
            String dateArrTime = "";
            String timeArrTime = "";
            String dateSArrTime = "";
            String timeSArrTime = "";
            Date date = null;
            Date dateS = null;
            Date todayDate = null;

            //To get the date & Time from the API
            String estimatedArrList[] = busArrList.get(i).getEstimated_Arr().split("T");
            if(estimatedArrList.length == 2) {
                dateArrTime = estimatedArrList[0];
                timeArrTime = estimatedArrList[1];
            }
            String estimatedArrSList[] = busArrList.get(i).getEstimated_ArrS().split("T");
            if(estimatedArrSList.length == 2) {
                dateSArrTime = estimatedArrSList[0];
                timeSArrTime = estimatedArrSList[1];
            }
            //Set Date Format for GMT +0
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

            //Set Date Format for GMT + 8
            DateFormat converter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            converter.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));

            //Set Date Format for Local Time
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String localTime = df.format(c.getTime());

            int minutes = 0;
            int minutesS = 0;
            try {
                date =  formatter.parse(dateArrTime + " " + timeArrTime); //Still in GMT+0
                dateS =  formatter.parse(dateSArrTime + " " + timeSArrTime); //Still in GMT+0
                todayDate = converter.parse(localTime); //In GMT+8

                String convertedDate = converter.format(date); //In GMT+8
                String convertedDateS = converter.format(dateS); //In GMT+8
                date = formatter.parse(convertedDate); //In GMT+8
                dateS = formatter.parse(convertedDateS); //In GMT+8

                //get Time in milli seconds
                long todayTime_ms = todayDate.getTime();
                long Sub_bus_dateS_ms = dateS.getTime();
                long next_bus_date_ms = date.getTime();
                long diff = next_bus_date_ms - todayTime_ms;
                long diff1 = Sub_bus_dateS_ms - todayTime_ms;
                //Get the Minutes difference
                //minutes - For Next Bus
                //minutes - For Subsequent Bus
                minutes = (int) diff / (60 * 1000) % 60;
                minutesS = (int) diff1 / (60 * 1000) % 60;

                temp += "Bus Stop No: " + busArrList.get(i).getBusStopNo()
                        + "\nBus Desc: " + busArrList.get(i).getBusStopDesc()
                        + "\nLat: " + busArrList.get(i).getLat()
                        + "\nLon: " + busArrList.get(i).getLon()
                        + "\nServiceNo: " + busArrList.get(i).getServiceNo()
                        + "\nStatus: " + busArrList.get(i).getStatus()
                        + "\nEstimated Arr in Date: " + convertedDate
                        + "\nLoad: " + busArrList.get(i).getLoad()
                        + "\nFeature: " + busArrList.get(i).getFeature()
                        + "\nEstimated ArrS in Date: " + convertedDateS
                        + "\nLoadS: " + busArrList.get(i).getLoadS()
                        + "\nFeatureS: " + busArrList.get(i).getFeatureS() //"" or "WAB" //WAB == With Wheelchair enabled bus
                        + "\nEstimateTime: " + minutes
                        + "\nEstimateTimeS: " + minutesS + "\n\n";
            } catch (ParseException e) {
                e.printStackTrace();
            }







        }*/


        test.setText(temp);
    }
}