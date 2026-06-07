package ensa.ma.sensors.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.LinkedList;
import java.util.Queue;

public class ActivityRecognitionFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView resultView;

    private final float[] gravity = new float[3];
    private final Queue<Float> movementWindow = new LinkedList<>();
    private static final int WINDOW_SIZE = 30;
    private static final float ALPHA = 0.8f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent,
                             @Nullable Bundle savedInstanceState) {

        resultView = new TextView(requireContext());
        resultView.setTextSize(22);
        resultView.setPadding(24, 24, 24, 24);

        sensorManager = (SensorManager)
                requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return resultView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        } else {
            resultView.setText("Accéléromètre indisponible.");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * x;
        gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * y;
        gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * z;

        float linearX = x - gravity[0];
        float linearY = y - gravity[1];
        float linearZ = z - gravity[2];

        float movement = (float) Math.sqrt(linearX * linearX + linearY * linearY + linearZ * linearZ);

        if (movementWindow.size() >= WINDOW_SIZE) movementWindow.poll();
        movementWindow.add(movement);

        String activity = classifyActivity(x, y, z);
        resultView.setText("X : " + x + "\nY : " + y + "\nZ : " + z
                + "\n\nMouvement : " + movement
                + "\n\nActivité détectée : " + activity);
    }

    private String classifyActivity(float x, float y, float z) {
        if (movementWindow.size() < WINDOW_SIZE) return "Calibration...";

        float mean = 0f;
        float max = 0f;
        for (float value : movementWindow) {
            mean += value;
            max = Math.max(max, value);
        }
        mean = mean / movementWindow.size();

        float variance = 0f;
        for (float value : movementWindow) {
            variance += (value - mean) * (value - mean);
        }
        variance = variance / movementWindow.size();
        float standardDeviation = (float) Math.sqrt(variance);

        if (max > 10f) return "Saut";
        if (standardDeviation > 1.2f) return "Marche";
        if (Math.abs(z) > 8f) return "Stable / téléphone à plat";
        if (Math.abs(y) > 7f || Math.abs(x) > 7f) return "Assis ou debout";
        return "Position stable";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
