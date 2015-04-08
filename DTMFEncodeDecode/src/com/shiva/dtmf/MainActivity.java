package com.shiva.dtmf;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.shiva.dtmf.fragments.DecodeFragment;
import com.shiva.dtmf.fragments.EncodeFragment;
import com.shiva.dtmf.utils.decode.Controller;
import com.shiva.dtmf.utils.decode.History;

public class MainActivity extends SherlockFragmentActivity implements
		TabListener {

	private enum TabTitle {
		ENCODE, DECODE
	}

	private ActionBar actionBar;
	

	public Controller controller;
    private DecodeFragment decodeFragment=new DecodeFragment();
    private EncodeFragment encodeFragment=new EncodeFragment();
	

	private History history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.addTab(actionBar.newTab().setText("Decode")
				.setTag(TabTitle.DECODE).setTabListener(MainActivity.this));
		actionBar.addTab(actionBar.newTab().setText("Encode")
				.setTag(TabTitle.ENCODE).setTabListener(MainActivity.this));

		controller = new Controller(this);

		

		history = new History(this);
		history.load();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(encodeFragment!=null&&encodeFragment.isVisible())
		{
			setContentView(R.layout.activity_main);
			getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_content, encodeFragment).commit();
			encodeFragment.inValidateKeyboardView();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			break;

		default:
			break;
		}

		return true;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		switch ((TabTitle) tab.getTag()) {
		case ENCODE:
			Toast.makeText(this, "ENCODE selected", Toast.LENGTH_LONG).show();
			ft.replace(R.id.activity_main_content,encodeFragment);
			break;
		case DECODE:
			Toast.makeText(this, "DECODE selected", Toast.LENGTH_LONG).show();
			ft.replace(R.id.activity_main_content, decodeFragment);
			break;

		default:
			break;
		}

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	public void start() {
		decodeFragment.start();
	}

	public void stop() {
		decodeFragment.stop(history);
	}

	public int getAudioSource() {
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		if (telephonyManager.getCallState() != TelephonyManager.PHONE_TYPE_NONE)
			return MediaRecorder.AudioSource.VOICE_DOWNLINK;

		return MediaRecorder.AudioSource.MIC;
	}

	public void clearText() {
		decodeFragment.clearText(history);
	}

	public void addText(Character c) {
		decodeFragment.addText(c);
	}

	public void setText(String text) {
		decodeFragment.setText(text);
	}

	public void setEnabled(boolean enabled) {
		decodeFragment.setEnabled(enabled);
	}

	public void setAciveKey(char key) {
		// numKeyboard.setActive(key);
	}

	private void showHistory() {

	}

	private void sendRecognizeredText() {
	}

	private void showAbout() {
		// AlertDialog about = new AlertDialog.Builder(this).create();
		//
		// about.setTitle(getString(R.string.app_name)+" ("+getVersion()+")");
		// about.setIcon(R.drawable.icon);
		// about.setMessage(getString(R.string.about_text));
		// about.show();
	}

	private String getVersion() {
		PackageManager manager = getPackageManager();
		PackageInfo info = null;

		try {
			info = manager.getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.v("NameNotFoundException", "getVersion() NameNotFoundException");
		}
		return info.versionName;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		decodeFragment.destroyMe(history);
	}

	@Override
	protected void onPause() {
		if (controller.isStarted())
			controller.changeState();
		super.onPause();
	}

}
