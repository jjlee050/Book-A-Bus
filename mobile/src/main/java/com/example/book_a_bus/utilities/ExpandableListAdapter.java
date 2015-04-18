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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.book_a_bus.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private GoogleApiClient mGoogleApiClient;


    public static final String NOTIFICATION_PATH = "/notification";
    public static final String NOTIFICATION_TIMESTAMP = "timestamp";
    public static final String NOTIFICATION_TITLE = "title";
    public static final String NOTIFICATION_CONTENT = "content";

    public static final String ACTION_DISMISS = "com.example.book_a_bus.utilities.DISMISS";

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData, GoogleApiClient mGoogleApiClient) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.mGoogleApiClient = mGoogleApiClient;
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
        ToggleButton toggleBtn = (ToggleButton) convertView.findViewById(R.id.flag_toggle_btn);
        toggleBtn.setTextOff("Off");
        toggleBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sendNotification((String)getGroup(groupPosition), (String)getChild(groupPosition,0));
                return false;
            }
        });

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(String title, String content) {
        Notification notification = new Notification.Builder(_context)
                .setSmallIcon(R.mipmap.ic_directions_bus_black_48dp)
                .setContentTitle("Book-A-Bus: Bus No. " + title)
                .setContentText("There is a passenger at the next bus stop.")
                .addAction(R.mipmap.ic_done_white_48dp,
                        "Acknowledge", null)
                .build();

        ((NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);
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