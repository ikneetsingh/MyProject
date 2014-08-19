package com.example.guitest;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Settings extends Activity {

	Typeface tfB;

	CheckBox showSend, showDel;

	SharedPreferences sp;

	SharedPreferences.Editor sped;

	Button save;

	boolean sendState = true, delState = true;

	@Override
	public void onBackPressed() {

		finish();
		overridePendingTransition(R.anim.translation_right_to_left, 0);
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		save = (Button) findViewById(R.id.saveSet);

		tfB = Typeface.createFromAsset(getAssets(),
				"fonts/self_destruct_button.ttf");

		save.setTypeface(tfB);

		showSend = (CheckBox) findViewById(R.id.showMessageSend);
		showDel = (CheckBox) findViewById(R.id.showMessageDel);

		sp = getSharedPreferences("SETTINGS", MODE_PRIVATE);
		sped = sp.edit();
		
		showSend.setChecked(sp.getBoolean("SMS_SEND", false));
		showDel.setChecked(sp.getBoolean("SMS_DEL", false));

		boolean initialSend = showSend.isChecked();
		boolean initialDel = showDel.isChecked();

		sped.putBoolean("SMS_SEND", initialSend);
		sped.putBoolean("SMS_DEL", initialDel);

		showSend.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked == true) {
					sped.putBoolean("SMS_SEND", true);
				} else if (isChecked == false) {
					sped.putBoolean("SMS_SEND", false);
				}

			}
		});

		showDel.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked == true) {
					sped.putBoolean("SMS_DEL", true);
				} else if (isChecked == false) {
					sped.putBoolean("SMS_DEL", false);
				}

			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				sped.commit();
				Toast.makeText(getBaseContext(), "Settings Saved",
						Toast.LENGTH_SHORT).show();

			}
		});

	}
}
