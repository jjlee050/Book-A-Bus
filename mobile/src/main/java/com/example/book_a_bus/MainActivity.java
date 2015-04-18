package com.example.book_a_bus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.book_a_bus.listeners.TaskListener;
import com.example.book_a_bus.objectmodel.BusArrivalInfo;
import com.example.book_a_bus.objectmodel.SBSInfo;
import com.example.book_a_bus.tasks.BusArrivalTask;

import java.util.ArrayList;

public class MainActivity extends Activity implements TaskListener {

	/*
	 * This is for BUS ROUTES ・SBST BUS SERVICE INFO API
	 */

    TextView test;

    ArrayList<SBSInfo> sbsInfoList = new ArrayList<SBSInfo>();

    ArrayList<BusArrivalInfo> busArrList = new ArrayList<BusArrivalInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = (TextView) findViewById(R.id.test);

        //To Start the task

        //BusServiceInfoTask s = new BusServiceInfoTask(MainActivity.this);
      // s.executeGetSBSInfo("SBST");
        BusArrivalTask bat = new BusArrivalTask(MainActivity.this);
        bat.executeGetArrivalTimeInfo("1.379820", "103.879626", "500");
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
        if (id == R.id.action_settings) {
            return true;
        }
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

        for(int i = 0; i < busArrList.size(); i++)
        {
            temp += "Bus Stop No: " + busArrList.get(i).getBusStopNo()
                    + "\nBus Desc: " + busArrList.get(i).getBusStopDesc()
                    + "\nLat: " + busArrList.get(i).getLat()
                    + "\nLon: " + busArrList.get(i).getLon()
                    + "\nServiceNo: " + busArrList.get(i).getServiceNo()
                    + "\nStatus: " + busArrList.get(i).getStatus()
                    + "\nEstimated Arr: " + busArrList.get(i).getEstimated_Arr()
                    + "\nLoad: " + busArrList.get(i).getLoad()
                    + "\nFeature: " + busArrList.get(i).getFeature()
                    + "\nEstimated ArrS: " + busArrList.get(i).getEstimated_ArrS()
                    + "\nLoadS: " + busArrList.get(i).getLoadS()
                    + "\nFeatureS: " + busArrList.get(i).getFeatureS() + "\n\n";

        }

        //Bus Arrival Time show GMT +0 Timing instead of GMT +8
        //API EstimatedArrival – Singapore Standard Time = Ariival Time

        test.setText(temp);
    }
}