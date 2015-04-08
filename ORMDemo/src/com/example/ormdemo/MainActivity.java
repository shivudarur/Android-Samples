package com.example.ormdemo;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.model.Note;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		insertAndDisplay();
	}

	private void insertAndDisplay() {
		try {
			getHelper().getNoteDao().create(new Note("Note5"));
			getHelper().getNoteDao().create(new Note("Note6"));
			getHelper().getNoteDao().create(new Note("Note7"));
			getHelper().getNoteDao().create(new Note("Note8"));
			List<Note> notes = getHelper().getNoteDao().queryForAll();
			Log.v("<<<<<<<<<<<>>>>>>>>>", notes.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
