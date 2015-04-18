package com.example.book_a_bus.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.book_a_bus.R;
import com.example.book_a_bus.objectmodel.BusArrivalInfo;
import com.example.book_a_bus.objectmodel.BusArrivalTimeInfo;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Lee Zhuo Xun on 18-Apr-15.
 */
public class MainPageFragment extends Fragment {

    String lat = "";
    String lon = "";
    ArrayList<BusArrivalInfo> busArrivalInfoList = new ArrayList<BusArrivalInfo>();
    ArrayList<String> busStopNoList = new ArrayList<String>();
    ArrayList<ArrayList<BusArrivalTimeInfo>> allBusStop_busList = new ArrayList<ArrayList<BusArrivalTimeInfo>>();
    ArrayList<BusArrivalTimeInfo> busList = new ArrayList<BusArrivalTimeInfo>();

    String[] values;
    Parcelable wrapped;

    public MainPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);
        final ListView lvBusStops = (ListView) rootView.findViewById(R.id.list_bus_stops);
        allBusStop_busList.clear();
        busStopNoList.clear();
        busArrivalInfoList.clear();
        if(lvBusStops.getCount() > 0)
            lvBusStops.removeAllViews();

        if (getArguments() != null) {
            lat = getArguments().getString("lat");
            lon = getArguments().getString("lon");
            wrapped = getArguments().getParcelable("busArrList");
            busArrivalInfoList = Parcels.unwrap(wrapped);

            for(int i = 0; i < busArrivalInfoList.size(); i ++)
            {
                allBusStop_busList.add(busArrivalInfoList.get(i).getBusArrivalTimeList());
                //busList = busArrivalInfoList.get(i).getBusArrivalTimeList();

                busStopNoList.add(busArrivalInfoList.get(i).getBusStopDesc() + "\n" + busArrivalInfoList.get(i).getBusStopNo());
            }

            values = new String[] { "Android List View",
                    "Adapter implementation",
                    "Simple List View In Android",
                    "Create List View Android",
                    "Android Example",
                    "List View Source Code",
                    "List View Array Adapter",
                    "Android Example List View"
            };

        }





        // Defined Array values to show in ListView


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, busStopNoList);
        lvBusStops.invalidateViews();
        adapter.notifyDataSetChanged();
        lvBusStops.setAdapter(null);
        lvBusStops.setAdapter(adapter);

        // ListView Item Click Listener
        lvBusStops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) lvBusStops.getItemAtPosition(position);

                Intent i = new Intent(MainPageFragment.this.getActivity(), DisplayBusesActivity.class);
                i.putExtra("bus_stop", busStopNoList.get(itemPosition));
                Parcelable wrapped = Parcels.wrap( allBusStop_busList);
                i.putExtra("bus_service_no", wrapped);
                i.putExtra("position", itemPosition);
                i.putExtra("lat",lat);
                i.putExtra("lon",lon);
                MainPageFragment.this.getActivity().startActivity(i);
            }
        });
        return rootView;
    }
}
