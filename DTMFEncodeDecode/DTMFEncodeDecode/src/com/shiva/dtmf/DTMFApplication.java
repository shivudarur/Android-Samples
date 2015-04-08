package com.shiva.dtmf;

import android.app.Application;

import com.testflightapp.lib.TestFlight;

public class DTMFApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// Initialize TestFlight with your app token.
		TestFlight.takeOff(this, "01527c9b-8f31-4b4c-905c-88019baf80f7");
		// ...
	}
}
