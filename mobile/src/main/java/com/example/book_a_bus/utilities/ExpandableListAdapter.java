package com.example.book_a_bus.utilities;

/**
 * Created by Rui on 18/4/2015.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
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
import com.parse.ParseQuery;

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

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData, GoogleApiClient mGoogleApiClient,
                                 String actionBarTitle) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.mGoogleApiClient = mGoogleApiClient;
        this.actionBarTitle = actionBarTitle;
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
                    try {
                        testObject.save();
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
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }

        Notification notification = new Notification.Builder(_context)
                .setSmallIcon(R.mipmap.ic_directions_bus_black_48dp)
                .setContentTitle("Book-A-Bus")
                .setContentText("Someone flagged!")
                .addAction(R.mipmap.ic_done_white_48dp,
                        "Acknowledge", null)
                .build();

        ((NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);

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