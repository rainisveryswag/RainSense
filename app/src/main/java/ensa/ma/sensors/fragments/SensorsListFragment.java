package ensa.ma.sensors.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ensa.ma.sensors.utils.SensorFormatter;

import java.util.List;

public class SensorsListFragment extends Fragment {

    private SensorManager sensorManager;
    private LinearLayout container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent,
                             @Nullable Bundle savedInstanceState) {

        ScrollView scrollView = new ScrollView(requireContext());

        container = new LinearLayout(requireContext());
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(24, 24, 24, 24);

        scrollView.addView(container);

        sensorManager = (SensorManager)
                requireActivity().getSystemService(Context.SENSOR_SERVICE);

        displaySensors();

        return scrollView;
    }

    private void displaySensors() {
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : sensors) {
            TextView textView = new TextView(requireContext());
            textView.setText(SensorFormatter.format(sensor));
            textView.setTextSize(14);
            textView.setPadding(16, 16, 16, 16);
            container.addView(textView);

            View separator = new View(requireContext());
            separator.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 2));
            separator.setBackgroundColor(0xFFE0E0E0);
            container.addView(separator);
        }
    }
}
