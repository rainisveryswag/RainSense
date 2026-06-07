package ensa.ma.sensors;

import android.hardware.Sensor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import ensa.ma.sensors.fragments.ActivityRecognitionFragment;
import ensa.ma.sensors.fragments.CompassFragment;
import ensa.ma.sensors.fragments.MotionSensorFragment;
import ensa.ma.sensors.fragments.SensorGraphFragment;
import ensa.ma.sensors.fragments.SensorsListFragment;
import ensa.ma.sensors.fragments.StepCounterFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Sensors App", Snackbar.LENGTH_LONG).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_share, R.id.nav_list,
                R.id.nav_temp, R.id.nav_humd, R.id.nav_compass)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_list) {
                openFragment(new SensorsListFragment());
            } else if (id == R.id.nav_temp) {
                openFragment(SensorGraphFragment.newInstance(
                        Sensor.TYPE_AMBIENT_TEMPERATURE, "Température ambiante", "FIRST_VALUE"));
            } else if (id == R.id.nav_humd) {
                openFragment(SensorGraphFragment.newInstance(
                        Sensor.TYPE_RELATIVE_HUMIDITY, "Humidité relative", "FIRST_VALUE"));
            } else if (id == R.id.nav_proximity) {
                openFragment(SensorGraphFragment.newInstance(
                        Sensor.TYPE_PROXIMITY, "Capteur de proximité", "FIRST_VALUE"));
            } else if (id == R.id.nav_magnetic) {
                openFragment(SensorGraphFragment.newInstance(
                        Sensor.TYPE_MAGNETIC_FIELD, "Champ magnétique", "MAGNITUDE"));
            } else if (id == R.id.nav_accelerometer) {
                openFragment(MotionSensorFragment.newInstance(
                        Sensor.TYPE_ACCELEROMETER, "Accéléromètre : x, y, z"));
            } else if (id == R.id.nav_gravity) {
                openFragment(MotionSensorFragment.newInstance(
                        Sensor.TYPE_GRAVITY, "Gravité : x, y, z"));
            } else if (id == R.id.nav_gyroscope) {
                openFragment(MotionSensorFragment.newInstance(
                        Sensor.TYPE_GYROSCOPE, "Gyroscope : rad/s"));
            } else if (id == R.id.nav_steps) {
                openFragment(new StepCounterFragment());
            } else if (id == R.id.nav_compass) {
                openFragment(new CompassFragment());
            } else if (id == R.id.nav_activity) {
                openFragment(new ActivityRecognitionFragment());
            }

            drawer.closeDrawers();
            return true;
        });
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
