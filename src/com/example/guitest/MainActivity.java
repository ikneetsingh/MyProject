package com.example.guitest;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

	SensorManager sensorMgr;

	Sensor sensor;

	ImageView power, contacts, credits, howToUse;

	boolean state = true;

	int x;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_main_layout);

		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

		sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		credits = (ImageView) findViewById(R.id.imageCredits);

		power = (ImageView) findViewById(R.id.imagePower);

		contacts = (ImageView) findViewById(R.id.imageContacts);

		howToUse = (ImageView) findViewById(R.id.imageHowToUse);

		power.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (state == false) {
					power.setImageResource(R.drawable.offbutton);
					Intent trace = new Intent(getBaseContext(),
							LocationAndSMSService.class);
					stopService(trace);
					unreg();
					state = true;
				} else if (state == true) {
					power.setImageResource(R.drawable.onbutton);
					reg();
					if (x == 1) {
						Intent trace = new Intent(getBaseContext(),
								LocationAndSMSService.class);
						startService(trace);
					}
					state = false;
				}

			}
		});

	}

	public void reg() {
		sensorMgr.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void unreg() {
		sensorMgr.unregisterListener(this, sensor);
	}

	public void switchToContacts(View v) {
		Intent i = new Intent(getBaseContext(), ManageContacts.class);
		startActivity(i);
		overridePendingTransition(R.anim.translation_left_to_right, 0);
	}

	public void switchToCredits(View v) {
		Intent i = new Intent(getBaseContext(), Credits.class);
		startActivity(i);
		overridePendingTransition(R.anim.translation_right_to_left, 0);
	}

	public void switchToSettings(View v) {
		Intent i = new Intent(getBaseContext(), Settings.class);
		startActivity(i);
		overridePendingTransition(R.anim.translation_left_to_right, 0);
	}

	public void switchToHowToUse(View v) {

		Intent i = new Intent(getBaseContext(), HowToUse.class);
		startActivity(i);
		overridePendingTransition(R.anim.translation_right_to_left, 0);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		float[] values = event.values;
		if (values[0] >= 16 || values[1] >= 16 || values[2] >= 16) {
			x = 1;
		}

	}
}
