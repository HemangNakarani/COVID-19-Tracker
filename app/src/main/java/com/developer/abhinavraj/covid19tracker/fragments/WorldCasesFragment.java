package com.developer.abhinavraj.covid19tracker.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.abhinavraj.covid19tracker.R;
import com.developer.abhinavraj.covid19tracker.adapter.CountryAdapter;
import com.developer.abhinavraj.covid19tracker.model.Country;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldCasesFragment extends Fragment {


    List<Country> mCountryList;
    CountryAdapter mAdapter;
    public WorldCasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_world_cases, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.countries);
        mCountryList = new ArrayList<>();

        mAdapter = new CountryAdapter(mCountryList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        new ExtractData().execute();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    private class ExtractData extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            try {

                byte[] out = "{\"data\":\"all\"}".getBytes(StandardCharsets.UTF_8);
                int length = out.length;

                URL obj = new URL("https://174y6n1sy5.execute-api.ap-south-1.amazonaws.com/prod");
                HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
                postConnection.setFixedLengthStreamingMode(length);
                postConnection.setRequestMethod("POST");
                postConnection.setDoOutput(true);
                OutputStream os = postConnection.getOutputStream();
                os.write(out);
                os.flush();
                os.close();
                int responseCode = postConnection.getResponseCode();
                System.out.println("POST Response Code :  " + responseCode);
                System.out.println("POST Response Message : " + postConnection.getResponseMessage());
                if (responseCode == 200) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            postConnection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    // print result
                    System.out.println(response.toString());
                    JSONObject jObject = new JSONObject(response.toString());
                    String j = jObject.get("body").toString();
                    JSONArray jsonArray = new JSONArray(j);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        String countryName = jObj.getString("country_name");
                        int activeCases = Integer.parseInt(jObj.getString("active_cases").replaceAll(",",""));
                        int totalCases = Integer.parseInt(jObj.getString("total_cases").replaceAll(",",""));
                        int totalRecovered = Integer.parseInt(jObj.getString("total_recovered").replaceAll(",",""));
                        int newCases = Integer.parseInt(jObj.getString("new_cases").replaceAll(",",""));
                        int totalDeaths = Integer.parseInt(jObj.getString("total_deaths").replaceAll(",",""));
                        int seriouslyCritical = Integer.parseInt(jObj.getString("serious_critical").replaceAll(",",""));
                        int newDeaths = Integer.parseInt(jObj.getString("new_deaths").replaceAll(",",""));
                        String totalCasesPerMillion = (jObj.getString("total_cases_per_1M"));

                         mCountryList.add(new Country(countryName,totalCases,totalRecovered,activeCases,
                                totalDeaths,newCases,newDeaths,seriouslyCritical,totalCasesPerMillion));

                    }
                    Collections.sort(mCountryList);
                    Log.d("jObject", String.valueOf(jsonArray.length()));
                } else {
                    System.out.println("POST NOT WORKED");
                }

            } catch (Exception ex) {
                Log.e("Async", ex.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter.notifyDataSetChanged();
        }

    }

}
