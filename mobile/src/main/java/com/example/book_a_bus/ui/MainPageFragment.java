package com.example.book_a_bus.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.book_a_bus.R;

/**
 * Created by Lee Zhuo Xun on 18-Apr-15.
 */
public class MainPageFragment extends Fragment {

    public MainPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);
        final ListView lvBusStops = (ListView) rootView.findViewById(R.id.list_bus_stops);

        // Defined Array values to show in ListView
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

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

                // Show Alert
                Toast.makeText(MainPageFragment.this.getActivity(),"Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG).show();

                Intent i = new Intent(MainPageFragment.this.getActivity(), DisplayBusesActivity.class);
                i.putExtra("bus_stop", itemValue);
                MainPageFragment.this.getActivity().startActivity(i);
            }
        });
        return rootView;
    }
}
