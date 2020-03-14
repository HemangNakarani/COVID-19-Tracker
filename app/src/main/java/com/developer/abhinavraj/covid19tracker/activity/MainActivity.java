package com.developer.abhinavraj.covid19tracker.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.developer.abhinavraj.covid19tracker.fragments.IndividualFragment;
import com.developer.abhinavraj.covid19tracker.fragments.UpdatesFragment;
import com.developer.abhinavraj.covid19tracker.fragments.WorldCasesFragment;
import com.developer.abhinavraj.covid19tracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(new WorldCasesFragment());
                            return true;
                        case R.id.navigation_sms:
                            openFragment(new IndividualFragment());
                            return true;
                        case R.id.navigation_notifications:
                            openFragment(new UpdatesFragment());
                            return true;
                    }
                    return false;
                }
            };

}
