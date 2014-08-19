package com.example.guitest;

import java.io.IOException;
import java.util.List;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

public class LocationAndSMSService extends Service {

	LocationManager locMgr;

	LocationListener locLis;

	double latitude, longitude;

	String address = "";

	BroadcastReceiver brSent, brDel;

	PendingIntent piSent, piDel;

	IntentFilter infSent, infDel;

	SmsManager smsMgr;

	SharedPreferences sp, spS;

	String no1, no2, no3;

	Sensor sensor;

	SensorManager sensorMgr;

	int sensorVar;

	public void onDestroy() {

		unregisterReceiver(brDel);
		unregisterReceiver(brSent);
		locMgr.removeUpdates(locLis);

		Toast.makeText(getBaseContext(), "Service stopped", Toast.LENGTH_LONG)
				.show();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		sp = getSharedPreferences("CONTACTS", MODE_PRIVATE);

		spS = getSharedPreferences("SETTINGS", MODE_PRIVATE);

		no1 = sp.getString("CONTACT1", "").toString();
		no2 = sp.getString("CONTACT2", "").toString();
		no3 = sp.getString("CONTACT3", "").toString();

		smsMgr = SmsManager.getDefault();

		locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);

		Toast.makeText(getBaseContext(), "Service started", Toast.LENGTH_LONG)
				.show();

		locLis = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

				switch (status) {
				case LocationProvider.AVAILABLE:
					Toast.makeText(getBaseContext(), provider + " available",
							Toast.LENGTH_SHORT).show();
					break;

				case LocationProvider.OUT_OF_SERVICE:
					Toast.makeText(getBaseContext(),
							provider + " out of service", Toast.LENGTH_SHORT)
							.show();
					break;

				case LocationProvider.TEMPORARILY_UNAVAILABLE:
					Toast.makeText(getBaseContext(),
							provider + " temporarily unavailable",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}

			}

			@Override
			public void onProviderEnabled(String provider) {
				Toast.makeText(getBaseContext(), provider + " enabled",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onProviderDisabled(String provider) {
				Toast.makeText(getBaseContext(), provider + " disabled",
						Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onLocationChanged(Location location) {

				if (location == null) {

					Intent call = new Intent(Intent.ACTION_CALL);
					call.setData(Uri.parse("tel:8860714701"));
					call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(call);

				}

				else {
					Toast.makeText(getBaseContext(), "location changed",
							Toast.LENGTH_LONG).show();

					latitude = location.getLatitude();
					longitude = location.getLongitude();

					Toast.makeText(getBaseContext(), latitude + "" + longitude,
							Toast.LENGTH_SHORT).show();
					Geocoder gc = new Geocoder(getBaseContext());
					try {

						List<Address> addresslist = gc.getFromLocation(
								latitude, longitude, 1);

						for (Address ad : addresslist) {
							for (int i = 0; i < ad.getMaxAddressLineIndex(); i++) {
								address += ad.getAddressLine(i);
							}

						}
					}

					catch (IOException e) {
						Toast.makeText(getBaseContext(),
								" no internet service available ",
								Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}

					smsMgr.sendTextMessage(no1, null,
							"I am in Danger.Follow my location :" + address,
							piSent, piDel);

					smsMgr.sendTextMessage(no2, null,
							"I am in Danger.Follow my location :" + address,
							piSent, piDel);

					smsMgr.sendTextMessage(no3, null,
							"I am in Danger.Follow my location :" + address,
							piSent, piDel);

					location = null;
				}

			}

		};

		address = "";

		locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 0,
				locLis);

		piSent = PendingIntent.getBroadcast(getBaseContext(), 1, new Intent(
				"SMS_SENT"), 0);

		piDel = PendingIntent.getBroadcast(getBaseContext(), 2, new Intent(
				"SMS_DEL"), 0);

		brSent = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				switch (getResultCode()) {
				case Activity.RESULT_OK:
					if (spS.getBoolean("SMS_SEND", false) == true) {
						Toast.makeText(getBaseContext(),
								"SMS sent successfully", Toast.LENGTH_SHORT)
								.show();
					}
					break;

				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(),
							"No service for this device", Toast.LENGTH_SHORT)
							.show();
					break;
				default:
					break;
				}

			}
		};

		brDel = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				switch (getResultCode()) {
				case Activity.RESULT_OK:
					if (spS.getBoolean("SMS_DEL", false) == true) {
						Toast.makeText(getBaseContext(), "SMS delievered",
								Toast.LENGTH_SHORT).show();
					}
					break;

				default:
					break;
				}

			}
		};

		infSent = new IntentFilter("SMS_SENT");
		infDel = new IntentFilter("SMS_DEL");

		registerReceiver(brSent, infSent);
		registerReceiver(brDel, infDel);

		return START_STICKY;
	}

}
