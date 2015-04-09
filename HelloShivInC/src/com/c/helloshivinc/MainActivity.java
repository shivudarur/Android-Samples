package com.c.helloshivinc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	static {
		System.loadLibrary("ndk_demo");
	}

	/**
	 * Adds two integers, returning their sum
	 */
	public native int add(int v1, int v2);

	/**
	 * Returns Hello World string
	 */
	public native String hello();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String helloText = hello();

		// Update the UI
		TextView outText = (TextView) findViewById(R.id.textOut);
		outText.setText(helloText);

		// Setup the UI
		Button buttonCalc = (Button) findViewById(R.id.buttonCalc);

		buttonCalc.setOnClickListener(new OnClickListener() {
			TextView result = (TextView) findViewById(R.id.result);
			EditText value1 = (EditText) findViewById(R.id.value1);
			EditText value2 = (EditText) findViewById(R.id.value2);

			public void onClick(View v) {
				int v1, v2, res = -1;
				v1 = Integer.parseInt(value1.getText().toString());
				v2 = Integer.parseInt(value2.getText().toString());

				res = add(v1, v2);
				result.setText(new Integer(res).toString());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
