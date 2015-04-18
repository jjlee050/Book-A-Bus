package com.example.book_a_bus.utilities;

/**
 * Created by Rui on 18/4/2015.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.book_a_bus.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private GoogleApiClient mGoogleApiClient;

    private String actionBarTitle;

    public static final String NOTIFICATION_PATH = "/notification";
    public static final String NOTIFICATION_TIMESTAMP = "timestamp";
    public static final String NOTIFICATION_TITLE = "title";
    public static final String NOTIFICATION_CONTENT = "content";

    public static final String ACTION_DISMISS = "com.example.book_a_bus.utilities.DISMISS";

    private ToggleButton toggleBtn;

    private String lat;
    private String lon;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData, GoogleApiClient mGoogleApiClient,
                                 String actionBarTitle, String lat, String lon) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.mGoogleApiClient = mGoogleApiClient;
        this.actionBarTitle = actionBarTitle;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_bus_services, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.tv_event_name);

        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_bus_services_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.tv_header);

        toggleBtn = (ToggleButton) convertView.findViewById(R.id.flag_toggle_btn);
        toggleBtn.setTextOff("Flag");
        toggleBtn.setTextOn("Flagged");
        toggleBtn.setText("Flag");

        ParseQuery query = ParseQuery.getQuery("Flag");
        query.whereEqualTo("busStopNo", actionBarTitle.substring(actionBarTitle.length()-6 , actionBarTitle.length()));
        query.whereEqualTo("busServiceNo",  getGroup(groupPosition));
        query.whereEqualTo("busDeviceID", "leezx");
        query.whereEqualTo("userID", "testuser");
        try{
            List<ParseObject> objectArrList = query.find();
            if(objectArrList.size() > 0){
                for(int i = 0;i < objectArrList.size();i++){
                    if(headerTitle.equals(objectArrList.get(i).getString("busServiceNo"))){
                        toggleBtn.toggle();
                    }
                }
            }
        }
        catch(Exception ex){

        }
        /*toggleBtn.setOnCheckedChangeListener(new {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                    if(toggleBtn.isChecked()==true) {
                        sendNotification((String) getGroup(groupPosition), "Flagged");
                    }
                    else{
                        sendNotification((String) getGroup(groupPosition), "Flag");
                    }
                    return true;
                }
                return false;
            }
        });*/

        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked()==true) {
                    boolean check = sendNotification((String) getGroup(groupPosition), "Flagged");
                    if(!check) {
                        buttonView.toggle();
                    }
                }
                else{
                    boolean check = sendNotification((String) getGroup(groupPosition), "Flag");
                    if(!check) {
                        buttonView.toggle();
                    }
                }
            }
        });

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private boolean sendNotification(String title, String options) {
        String busStopNo = actionBarTitle.substring(actionBarTitle.length()-6 , actionBarTitle.length());

        System.out.println(options);
        System.out.println("Bus Stop No: " + busStopNo);
        System.out.println("Bus Service No: " + title);

        ParseQuery checkQuery = ParseQuery.getQuery("Flag");
        checkQuery.whereEqualTo("busStopNo", busStopNo);
        checkQuery.whereEqualTo("busDeviceID", "leezx");
        checkQuery.whereEqualTo("userID", "testuser");

        if(options.equals("Flagged")) {
            try{
                List<ParseObject> objectArrList = checkQuery.find();
                if(objectArrList.size() > 0){
                    Toast.makeText(_context, "You have already flagged a bus", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else{
                    ParseObject testObject = new ParseObject("Flag");
                    testObject.put("busStopNo", busStopNo);
                    testObject.put("busServiceNo", title);
                    testObject.put("busDeviceID", "leezx");
                    testObject.put("userID", "testuser");
                    testObject.put("lat",lat);
                    testObject.put("lon",lon);
                    try {
                        testObject.save();

                        /*ParsePush.subscribeInBackground("leezx", new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                                } else {
                                    Log.e("com.parse.push", "failed to subscribe for push", e);
                                }
                            }
                        });*/

                        String message = "Someone flagged!";
                        JSONObject data = new JSONObject();
                        data.put("alert",message);
                        data.put("action", "com.example.book_a_bus.ui.NotificationActivity");
                        data.put("busStopNo", busStopNo);
                        data.put("busServiceNo", title);
                        data.put("installationId", "4c78bf79-a228-4634-9cf8-4b4c7ef90bdb");
                        data.put("lat",lat);
                        data.put("lon",lon);
                        ParsePush push = new ParsePush();
                        push.setData(data);
                        push.send();

                        /*// prepare intent which is triggered if the
                        // notification is selected
                        Intent intent = new Intent(_context, NotificationActivity.class);
                        intent.putExtra("busStopNo", busStopNo);
                        intent.putExtra("busServiceNo", title);
                        intent.putExtra("busDeviceID", "leezx");
                        intent.putExtra("userID", "testuser");
                        PendingIntent pIntent = PendingIntent.getActivity(_context.getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        Notification notification = new Notification.Builder(_context)
                                .setSmallIcon(R.mipmap.ic_directions_bus_black_48dp)
                                .setContentTitle("Book-A-Bus")
                                .setContentText("Someone flagged!")
                                .addAction(R.mipmap.ic_done_white_48dp,
                                        "Acknowledge", pIntent)
                                .build();
                        // hide the notification after its selected
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;

                        ((NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);*/
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }

        }
        else{
            ParseQuery query = ParseQuery.getQuery("Flag");
            query.whereEqualTo("busStopNo", busStopNo);
            query.whereEqualTo("busServiceNo", title);
            query.whereEqualTo("busDeviceID", "leezx");
            query.whereEqualTo("userID", "testuser");
            try{
                List<ParseObject> objectArrList = query.find();
                if(objectArrList.size() > 0){
                    for(int i = 0;i < objectArrList.size();i++){
                        objectArrList.get(i).delete();
                    }
                    return true;
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}