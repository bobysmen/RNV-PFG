package com.example.rnv_pfg.ui.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.rnv_pfg.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        setupToolbar();
        setupBottomNav();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.appointmentsDaily, R.id.formAppointmentsPerDay, R.id.patients).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    private void setupBottomNav() {
        bottomNav = ActivityCompat.requireViewById(this, R.id.bottom_navigation);
        bottomNav.setVisibility(View.GONE);
        NavigationUI.setupWithNavController(bottomNav, navController);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnuAppointmentDaily:
                    Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.appointmentsDaily);
                    break;
                case R.id.mnuAppointmentDate:
                    Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.formAppointmentsPerDay);
                    break;
                case R.id.mnuPatient:
                    Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.patients);
                    break;
                case R.id.mnuAddPatient:
                    Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.formAddPatient);
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
