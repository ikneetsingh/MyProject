package com.example.guitest;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

public class HowToUse extends Activity {

	Typeface tfTitle, tfSteps, tfDes;

	TextView head, step1, step2, step3, step4, note1, note2, des1, des2, des3,
			des4, des5, des6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_how_to_use);

		tfTitle = Typeface.createFromAsset(getAssets(), "fonts/credits.ttf");

		tfSteps = Typeface.createFromAsset(getAssets(), "fonts/subtitles.ttf");

		tfDes = Typeface.createFromAsset(getAssets(), "fonts/description.ttf");

		head = (TextView) findViewById(R.id.head);

		step1 = (TextView) findViewById(R.id.step1);
		step2 = (TextView) findViewById(R.id.step2);
		step3 = (TextView) findViewById(R.id.step3);
		step4 = (TextView) findViewById(R.id.step4);

		note1 = (TextView) findViewById(R.id.note1);
		note2 = (TextView) findViewById(R.id.note2);

		des1 = (TextView) findViewById(R.id.des1);
		des2 = (TextView) findViewById(R.id.des2);
		des3 = (TextView) findViewById(R.id.des3);
		des4 = (TextView) findViewById(R.id.des4);
		des5 = (TextView) findViewById(R.id.des5);
		des6 = (TextView) findViewById(R.id.des6);

		note1 = (TextView) findViewById(R.id.note1);
		note2 = (TextView) findViewById(R.id.note2);

		head.setTypeface(tfTitle);

		step1.setTypeface(tfSteps);
		step2.setTypeface(tfSteps);
		step3.setTypeface(tfSteps);
		step4.setTypeface(tfSteps);

		des1.setTypeface(tfDes);
		des2.setTypeface(tfDes);
		des3.setTypeface(tfDes);
		des4.setTypeface(tfDes);
		des5.setTypeface(tfDes);
		des6.setTypeface(tfDes);
		
		note1.setTypeface(tfSteps);
		note2.setTypeface(tfSteps);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.translation_left_to_right, 0);
	}

}
