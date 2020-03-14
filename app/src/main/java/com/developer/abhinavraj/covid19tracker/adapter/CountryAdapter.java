package com.developer.abhinavraj.covid19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.abhinavraj.covid19tracker.R;
import com.developer.abhinavraj.covid19tracker.model.Country;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<Country> countryList;


    public CountryAdapter(List<Country> countries){
        this.countryList = countries;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View countryView = inflater.inflate(R.layout.country_item, parent, false);

        // Return a new holder instance
        return new ViewHolder(countryView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = countryList.get(position);

        // Set item views based on your views and data model
        TextView countryName = holder.countryName;
        TextView totalCases = holder.totalCases;
        countryName.setText(country.getCountryName());
        totalCases.setText(String.valueOf(country.getTotalCases()));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView countryName, totalCases;

        public ViewHolder(View view) {
            super(view);
            countryName = (TextView) view.findViewById(R.id.country_name);
            totalCases = (TextView) view.findViewById(R.id.total_cases);
        }
    }
}
