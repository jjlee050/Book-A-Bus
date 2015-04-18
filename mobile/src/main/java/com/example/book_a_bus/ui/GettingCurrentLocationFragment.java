package com.example.book_a_bus.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_a_bus.R;

/**
 * Created by Lee Zhuo Xun on 18-Apr-15.
 */
public class GettingCurrentLocationFragment extends Fragment {

    public GettingCurrentLocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_getting_current_location, container, false);
        return rootView;
    }
}
