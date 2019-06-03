package com.example.rnv_pfg.ui.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.rnv_pfg.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNav();
    }

    private void setupBottomNav() {
        bottomNav = ActivityCompat.requireViewById(this, R.id.bottom_navigation);
        bottomNav.setVisibility(View.GONE);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnuAppointmentDaily:
                    Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.appointmentsDaily);
                    break;
                case R.id.mnuAppointmentDate:
                    // ...
                    break;
                case R.id.mnuPatient:
                    Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.patient);
                    break;
            }

            return false;
        });
    }

    public static void showBottomNav(Activity activity){
        BottomNavigationView bottomNav = ActivityCompat.requireViewById(activity, R.id.bottom_navigation);
        bottomNav.setVisibility(View.VISIBLE);
    }
}
