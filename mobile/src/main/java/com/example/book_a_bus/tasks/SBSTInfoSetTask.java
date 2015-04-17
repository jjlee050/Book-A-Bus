package com.example.book_a_bus.tasks;

import android.util.Log;

import com.example.book_a_bus.listeners.TaskListener;
import com.example.book_a_bus.objectmodel.SBSInfo;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.book_a_bus.utilities.ServerUtilities.getDefaultClient;


public class SBSTInfoSetTask extends BaseTask<NameValuePair, SBSInfo, SBSInfo> {
	
	public final static String PARAM_ACCOUNT_KEY = "AccountKey";
	public final static String PARAM_UNIQUE_USERID = "UniqueUserId";
	public final static String PARAM_ACCEPT = "accept"; // Type of information returned. In this case we want JSON
	
	/*
	 * Account key & Unique UserId will be expired on 16 April, 5pm. 
	 * A new one will be given during registration on 16 April, 6pm.
	 */
	public final static String ATTR_ACCOUNT_KEY = "LTAHackathon2015";
	public final static String ATTR_UNIQUE_USERID = "0A45163B-ACE5-4FE0-AFBE-2632BF705F3C";
	public final static String ATTR_ACCEPT = "application/json";
	
	//If i am not wrong, once the current account key and unique userid is expire, we have to use this link instead.
	//http://datamall.mytransport.sg/ltaodataservice.svc/SBSTInfoSet
	public final static String URL = "http://datamallplus.cloudapp.net/ltaodataservice.svc/SBSTInfoSet";
	
	public final static String ATTR_BUS_SERVICE_NO = "SI_SVC_NUM";
	public final static String ATTR_DIR_NO = "SI_SVC_DIR"; 
	public final static String ATTR_CATEGORY = "SI_SVC_CAT";
	public final static String ATTR_START_LOCATION = "SI_BS_CODE_ST";
	public final static String ATTR_END_LOCATION = "SI_BS_CODE_END";
	public final static String ATTR_FREQ_AM_PEAK = "SI_FREQ_AM_PK";
	public final static String ATTR_FREQ_AM_OFF_PEAK = "SI_FREQ_AM_OF";
	public final static String ATTR_FREQ_PM_PEAK = "SI_FREQ_PM_PK";
	public final static String ATTR_FREQ_PM_OFF_PEAK = "SI_FREQ_PM_OF";
	public final static String ATTR_LOOP_STATION = "SI_LOOP";
	
	public SBSTInfoSetTask(TaskListener callback) {
		setTaskListener(callback);
	}
	
	public void executeGetSBSInfo() {
		//Must execute in order to start the doInBackground method below.
		execute();
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
		//List<NameValuePair> URLparams = new LinkedList<NameValuePair>();
		//URLparams.add(new BasicNameValuePair(PARAM_TOKEN, PROPERTY_TOKEN));
		//URLparams.add(new BasicNameValuePair(PARAM_CALLBACK, VALUE_CALLBACK));
		//URLparams.add(new BasicNameValuePair(PARAM_BUFFER, VALUE_BUFFER));
		
		//for(NameValuePair param : params){
		//	URLparams.add(param);
		//}
		
		//String paramString = URLEncodedUtils.format(URLparams, "utf-8");
		String url = URL; //+paramString;

		
		Log.i("Test", url);

		HttpClient client = getDefaultClient();
		
		//Set the 3 required header, account key, unique userid and accept.
		HttpGet get = new HttpGet(url);
		get.addHeader(PARAM_ACCOUNT_KEY, ATTR_ACCOUNT_KEY);
		get.addHeader(PARAM_UNIQUE_USERID, ATTR_UNIQUE_USERID);
		get.addHeader(PARAM_ACCEPT, ATTR_ACCEPT);
		
		ResponseHandler<String> handler = new BasicResponseHandler();
		
		try {
			String response = client.execute(get, handler);
			JSONObject jsonResponse = new JSONObject(response);
			
			JSONArray searchResults = null;
			
			
			Log.i("Test", String.valueOf(jsonResponse));
			searchResults = jsonResponse.getJSONArray("d");
			Log.i("Test", String.valueOf(searchResults));
			
			for(int i=0; i<searchResults.length(); i++) {
				if(isCancelled()){
					return null;
				}
				
				JSONObject result = searchResults.getJSONObject(i);
				String busServiceNo = result.getString(ATTR_BUS_SERVICE_NO);
				String directionNo = result.getString(ATTR_DIR_NO);
				String category = result.getString(ATTR_CATEGORY);
				String startLocation = result.getString(ATTR_START_LOCATION); 
				String endLocation = result.getString(ATTR_END_LOCATION);
				String amPeakFreq = result.getString(ATTR_FREQ_AM_PEAK);
				String amOffPeakFreq = result.getString(ATTR_FREQ_AM_OFF_PEAK); 
				String pmPeakFreq = result.getString(ATTR_FREQ_PM_PEAK); 
				String pmOffPeakFreq = result.getString(ATTR_FREQ_PM_OFF_PEAK);
				String locationLoop = result.getString(ATTR_LOOP_STATION);
				
				SBSInfo sbsInfo = new SBSInfo(busServiceNo, directionNo, category, startLocation, endLocation, amPeakFreq, amOffPeakFreq, pmPeakFreq, pmOffPeakFreq, locationLoop);
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
