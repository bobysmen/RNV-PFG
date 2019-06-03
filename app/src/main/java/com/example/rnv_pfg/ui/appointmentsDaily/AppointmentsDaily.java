package com.example.rnv_pfg.ui.appointmentsDaily;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.ui.mainActivity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentsDaily extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointments_daily, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.showBottomNav(getActivity());
    }
}
