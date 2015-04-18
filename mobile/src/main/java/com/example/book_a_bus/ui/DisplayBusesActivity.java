package com.example.book_a_bus.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.book_a_bus.R;
import com.example.book_a_bus.objectmodel.BusArrivalTimeInfo;
import com.example.book_a_bus.utilities.ExpandableListAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;


public class DisplayBusesActivity extends ActionBarActivity {

    ExpandableListView listView;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    ArrayList<BusArrivalTimeInfo> busList = new ArrayList<BusArrivalTimeInfo>();
    ArrayList<ArrayList<BusArrivalTimeInfo>> allBusStop_busList = new ArrayList<ArrayList<BusArrivalTimeInfo>>();
    int position;

    private GoogleApiClient mGoogleApiClient;
    private final String TAG="GoogleApi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_buses);

        savedInstanceState = getIntent().getExtras();
        position = savedInstanceState.getInt("position");
        String busStopName = savedInstanceState.getString("bus_stop");
        Parcelable wrapped = savedInstanceState.getParcelable("bus_service_no");
        allBusStop_busList = Parcels.unwrap(wrapped);

        getSupportActionBar().setTitle(busStopName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ExpandableListView) findViewById(R.id.lv_view_all_bus_services);

        // preparing list data
        prepareListData();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                    }
                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, mGoogleApiClient, getSupportActionBar().getTitle().toString());

        // setting list adapter
        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        //listDataHeader.add("Top 250");
        //listDataHeader.add("Now Showing");
        //listDataHeader.add("Coming Soon..");

        for (int i = 0; i<allBusStop_busList.get(position).size(); i++)
        {

            try {
                listDataHeader.add(allBusStop_busList.get(position)
                        .get(i).getServiceNo());
                List<String> tempList = new ArrayList<String>();

                String dateArrTime = "";
                String timeArrTime = "";
                String dateSArrTime = "";
                String timeSArrTime = "";
                Date date = null;
                Date dateS = null;
                Date todayDate = null;

                //To get the date & Time from the API
                String estimatedArrList[] = allBusStop_busList.get(position).get(i).getEstimated_Arr().split("T");
                if(estimatedArrList.length == 2) {
                    dateArrTime = estimatedArrList[0];
                    timeArrTime = estimatedArrList[1];
                }
                String estimatedArrSList[] = allBusStop_busList.get(position).get(i).getEstimated_ArrS().split("T");
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

                String WAB = "No";
                String WABS = "No";
                String load;
                String loadS;
                if (allBusStop_busList.get(position).get(i).getFeature().equals("WAB"))
                    WAB = "Yes";
                if (allBusStop_busList.get(position).get(i).getFeatureS().equals("WAB"))
                    WABS = "Yes";

                load = allBusStop_busList.get(position).get(i).getLoad();
                loadS = allBusStop_busList.get(position).get(i).getLoadS();


                tempList.add("Next Bus: " + minutes + "min" + "\nWAB:" + WAB + "\nLoad: " + load + "\n\nSubsequent Bus: " + minutesS + "min" + "\nWAB:" + WABS + "\nLoad: " + loadS);

                listDataChild.put(listDataHeader.get(i), tempList);

            } catch (ParseException e) {
                e.printStackTrace();
            }



        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_buses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
