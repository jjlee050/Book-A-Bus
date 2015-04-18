package com.example.book_a_bus.tasks;

import android.util.Log;

import com.example.book_a_bus.listeners.TaskListener;
import com.example.book_a_bus.objectmodel.SBSInfo;

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


public class BusServiceInfoTask extends BaseTask<NameValuePair, SBSInfo, SBSInfo> {

	public final static String PARAM_ACCOUNT_KEY = "AccountKey";
	public final static String PARAM_UNIQUE_USERID = "UniqueUserId";
	public final static String PARAM_ACCEPT = "accept"; // Type of information returned. In this case we want JSON
    public final static String PARAM_BUS_TYPE = "service";
    public final static String PARAM_DIRECTION = "direction";
    public final static String PARAM_API_KEY = "apikey";

	public final static String ATTR_ACCOUNT_KEY = "974D1929-8E9D-4C43-A368-B83D35B1D048";
	public final static String ATTR_UNIQUE_USERID = "AA119825-B67E-4D4A-8F07-22CED2D3D029";
	public final static String ATTR_ACCEPT = "application/json";

    public final static String ATTR_API_KEY = "7p0FKaBf2eDo1iYhG7y6g14PbIh1thJM";

    public final static String SBS_URL = "http://api.onemobility.sg/lta-bus-services?";

    public final static String ATTR_BUS_SERVICE = "service";
    public final static String ATTR_BUS_SERVICE_NO = "num";
    public final static String ATTR_DIRECTION = "direction";
    public final static String ATTR_CATEGORY = "category";
    public final static String ATTR_START_BS = "start_bus_stop";
    public final static String ATTR_END_BS = "end_bus_stop";
    public final static String ATTR_FREQ_AM_PEAK = "freq_peak_am";
    public final static String ATTR_FREQ_AM_OFF_PEAK = "freq_off_peak_am";
    public final static String ATTR_FREQ_PM_PEAK = "freq_peak_pm";
    public final static String ATTR_FREQ_PM_OFF_PEAK = "freq_off_peak_pm";
    public final static String ATTR_LOOP_DESC = "loop_description";

    public String busType;

	public BusServiceInfoTask(TaskListener callback) {
		setTaskListener(callback);
	}
	
	public void executeGetSBSInfo(String bus_type) {
		//Must execute in order to start the doInBackground method below.
        busType = bus_type;
        BasicNameValuePair paramBusType = new BasicNameValuePair(PARAM_BUS_TYPE, bus_type);
        BasicNameValuePair paramDirection = new BasicNameValuePair(PARAM_DIRECTION, "1");
		execute(paramBusType, paramDirection);
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
	protected SBSInfo doInBackground(NameValuePair... params) {
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
        String url = SBS_URL + paramString;
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
				String busService = result.getString(ATTR_BUS_SERVICE);
				String busNo = result.getString(ATTR_BUS_SERVICE_NO);
				String directionNo = result.getString(ATTR_DIRECTION);
                String category = result.getString(ATTR_CATEGORY);
				String startLocation = result.getString(ATTR_START_BS);
				String endLocation = result.getString(ATTR_END_BS);
				String amPeakFreq = result.getString(ATTR_FREQ_AM_PEAK);
				String amOffPeakFreq = result.getString(ATTR_FREQ_AM_OFF_PEAK); 
				String pmPeakFreq = result.getString(ATTR_FREQ_PM_PEAK); 
				String pmOffPeakFreq = result.getString(ATTR_FREQ_PM_OFF_PEAK);
				String loopDesc = result.getString(ATTR_LOOP_DESC);
				
				SBSInfo sbsInfo = new SBSInfo(busService, busNo, directionNo, category, startLocation, endLocation, amPeakFreq, amOffPeakFreq, pmPeakFreq, pmOffPeakFreq, loopDesc);
				//publishprogress to the onPreExecute in MainActivity. Basically throwing the object to the mainactivity to display, etc.
				publishProgress(sbsInfo);
				
			}
			
			
			return new SBSInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	

}
