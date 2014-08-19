package com.example.guitest;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManageContacts extends Activity {

	SharedPreferences shpf;

	SharedPreferences.Editor se;

	EditText con1, con2, con3;

	String defaultCon1, defaultCon2, defaultCon3;

	String edCon1, edCon2, edCon3;

	Button saveContacts;

	int i = 0, j = 0, k = 0;

	Typeface tf, tfButton;

	@Override
	public void onBackPressed() {

		finish();
		overridePendingTransition(R.anim.translation_right_to_left, 0);

		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_contacts);

		tf = Typeface.createFromAsset(getAssets(), "fonts/skip_leg_day.ttf");

		tfButton = Typeface.createFromAsset(getAssets(),
				"fonts/self_destruct_button.ttf");

		con1 = (EditText) findViewById(R.id.editText1);
		con2 = (EditText) findViewById(R.id.editText2);
		con3 = (EditText) findViewById(R.id.editText3);

		con1.setTypeface(tf);
		con2.setTypeface(tf);
		con3.setTypeface(tf);

		shpf = getSharedPreferences("CONTACTS", MODE_PRIVATE);

		se = shpf.edit();

		defaultCon1 = shpf.getString("CONTACT1", "");
		defaultCon2 = shpf.getString("CONTACT2", "");
		defaultCon3 = shpf.getString("CONTACT3", "");

		con1.setText(defaultCon1.toString());
		con2.setText(defaultCon2.toString());
		con3.setText(defaultCon3.toString());

		saveContacts = (Button) findViewById(R.id.button1);

		saveContacts.setTypeface(tfButton);

		con1.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus == true) {
					if (con1.getText().toString()
							.equals("Enter a valid number")) {
						con1.setTextColor(Color.BLACK);
						con1.setText("");
					}
				}

			}
		});
		con2.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus == true) {
					if (con2.getText().toString()
							.equals("Enter a valid number")) {
						con2.setTextColor(Color.BLACK);
						con2.setText("");
					}
				}

			}
		});
		con3.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus == true) {
					if (con3.getText().toString()
							.equals("Enter a valid number")) {
						con3.setTextColor(Color.BLACK);
						con3.setText("");
					}
				}

			}
		});

		saveContacts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				edCon1 = con1.getText().toString();
				edCon2 = con2.getText().toString();
				edCon3 = con3.getText().toString();

				if (edCon1.length() != 10) {
					con1.setTextColor(Color.RED);
					con1.setText("Enter a valid number");
				} else {
					se.putString("CONTACT1", edCon1);
					i = 1;
				}
				if (edCon2.length() != 10) {
					con2.setTextColor(Color.RED);
					con2.setText("Enter a valid number");
				} else {
					se.putString("CONTACT2", edCon2);
					j = 1;
				}
				if (edCon3.length() != 10) {
					con3.setTextColor(Color.RED);
					con3.setText("Enter a valid number");
				} else {
					se.putString("CONTACT3", edCon3);
					k = 1;
				}

				if (i == 1 && j == 1 && k == 1) {
					se.commit();
					Toast.makeText(getBaseContext(), "Contacts Saved",
							Toast.LENGTH_SHORT).show();
				}

				else {
					Toast.makeText(getBaseContext(), "Enter Valid Contact",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

	}
}
