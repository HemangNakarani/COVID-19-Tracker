package com.developer.abhinavraj.covid19tracker.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.abhinavraj.covid19tracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndividualFragment extends Fragment {


    public IndividualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual, container, false);
    }

}
