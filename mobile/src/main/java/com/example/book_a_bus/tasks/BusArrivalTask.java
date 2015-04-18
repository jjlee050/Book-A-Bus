package com.example.book_a_bus.tasks;

import android.util.Log;

import com.example.book_a_bus.listeners.TaskListener;
import com.example.book_a_bus.objectmodel.BusArrivalInfo;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static com.example.book_a_bus.utilities.ServerUtilities.getDefaultClient;


public class BusArrivalTask extends BaseTask<NameValuePair, BusArrivalInfo, BusArrivalInfo> {

    public final static String PARAM_LAT = "lat";
    public final static String PARAM_LON = "lon";
    public final static String PARAM_DIST = "distance";
    public final static String PARAM_API_KEY = "apikey";

    public final static String ATTR_API_KEY = "7p0FKaBf2eDo1iYhG7y6g14PbIh1thJM";

    public final static String URL = "http://api.onemobility.sg/lta-bus-stops-arrival?";

    public final static String ATTR_BUS_STOP_NO = "bus_stop_no";
    public final static String ATTR_DESC = "description";
    public final static String ATTR_LAT = "lat";
    public final static String ATTR_LON = "lon";
    public final static String ATTR_SERVICE_NO = "serviceno";
    public final static String ATTR_STATUS = "status";
    public final static String ATTR_ESTIMATED_ARRIVAL = "estimated_arrival";
    public final static String ATTR_LOAD = "load";
    public final static String ATTR_FEATURE = "feature"; //Wheel-chair enabled,etc

	public BusArrivalTask(TaskListener callback) {
		setTaskListener(callback);
	}
	
	public void executeGetArrivalTimeInfo(String lat, String lon, String dist) {
		//Must execute in order to start the doInBackground method below.
        BasicNameValuePair paramLat = new BasicNameValuePair(PARAM_LAT, lat);
        BasicNameValuePair paramLon = new BasicNameValuePair(PARAM_LON, lon);
        BasicNameValuePair paramDist = new BasicNameValuePair(PARAM_DIST, dist);
        execute(paramLat, paramLon, paramDist);

	}
	
	//Sample if there are attribute to be inserted
	/*
	public void executeGetCategory(String yearType, String year) {
		
		purpose = "getCategory";
		
		//Both Search Option Map or Area required the 5 variable below
		BasicNameValuePair paramYearType = new BasicNameValuePair(PARAM_YEAR_TYPE, yearType);
		BasicNameValuePair paramYear = new BasicNameValuePair(PARAM_YEAR, year);
		execute(paramYear, paramYearType);
	}*/
	
	@Override
	protected BusArrivalInfo doInBackground(NameValuePair... params) {
		/*if(params == null || params.length == 0){
			cancel(true);
			return null;
			
		}*/
		
		//If there are attribute to be inserted
		List<NameValuePair> URLparams = new LinkedList<NameValuePair>();
		URLparams.add(new BasicNameValuePair(PARAM_API_KEY,ATTR_API_KEY));
		//URLparams.add(new BasicNameValuePair(PARAM_CALLBACK, VALUE_CALLBACK));
		//URLparams.add(new BasicNameValuePair(PARAM_BUFFER, VALUE_BUFFER));
		
		for(NameValuePair param : params){
			URLparams.add(param);
		}
		
		String paramString = URLEncodedUtils.format(URLparams, "utf-8");
        String url = URL + paramString;
       // if(busType.equals("SBS"))
		//    url = SBS_URL; //+paramString;
     //   else if (busType.equals("SMRT"))
      //      url = SMRT_URL;

		
		Log.i("Test", url);

		HttpClient client = getDefaultClient();
		
		//Set the 3 required header, account key, unique userid and accept.
		HttpGet get = new HttpGet(url);
		//get.addHeader(PARAM_ACCOUNT_KEY, ATTR_ACCOUNT_KEY);
		//get.addHeader(PARAM_UNIQUE_USERID, ATTR_UNIQUE_USERID);
		//get.addHeader(PARAM_ACCEPT, ATTR_ACCEPT);
		
		ResponseHandler<String> handler = new BasicResponseHandler();
		
		try {
			String response = client.execute(get, handler);
			//JSONObject jsonResponse = new JSONObject(response);
			Log.i("Test", response);

			JSONArray searchResults = new JSONArray(response);

			
			//Log.i("Test", String.valueOf(jsonResponse));
			//searchResults = jsonResponse.getJSONArray("");
			//Log.i("Test", String.valueOf(searchResults));
			
			for(int i=0; i<searchResults.length(); i++) {
				if(isCancelled()){
					return null;
				}

                JSONObject result = searchResults.getJSONObject(i);
                String busStopNo = result.getString(ATTR_BUS_STOP_NO);
                String busDesc = result.getString(ATTR_DESC);

                JSONObject locationList = result.getJSONObject("location");
                String lat = locationList.getString(ATTR_LAT);
                String lon = locationList.getString(ATTR_LON);

                JSONArray busArrivalList = result.getJSONArray("bus_arrival");
                JSONObject result2 = busArrivalList.getJSONObject(0);
                String serviceNo = result2.getString(ATTR_SERVICE_NO);
                String status = result2.getString(ATTR_STATUS);

                JSONObject nextBusList = result2.getJSONObject("nextbus");
                String estimatedArr = nextBusList.getString(ATTR_ESTIMATED_ARRIVAL);
                String load = nextBusList.getString(ATTR_LOAD);
                String feature = nextBusList.getString(ATTR_FEATURE);

                JSONObject subsequentBusList = result2.getJSONObject("subsequentbus");
                String estimatedArrS = subsequentBusList.getString(ATTR_ESTIMATED_ARRIVAL);
                String loadS = subsequentBusList.getString(ATTR_LOAD);
                String featureS = subsequentBusList.getString(ATTR_FEATURE);

                BusArrivalInfo busArrInfo = new BusArrivalInfo(busStopNo, busDesc, lat, lon, serviceNo, status, estimatedArr, load, feature, estimatedArrS, loadS, featureS);
                //publishprogress to the onPreExecute in MainActivity. Basically throwing the object to the mainactivity to display, etc.
                publishProgress(busArrInfo);
				
			}
			
			
			return new BusArrivalInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	

}
