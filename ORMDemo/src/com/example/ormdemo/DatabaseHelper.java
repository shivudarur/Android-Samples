package com.example.ormdemo;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.example.model.Note;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private Context context;
	private static String DATABASE_NAME = "Notes";
	private static int DATABASE_VERSION = 1;
	private Dao<Note, Integer> noteDao;

	public Dao<Note, Integer> getNoteDao() {
		if (noteDao == null)
			try {
				noteDao = getDao(Note.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return noteDao;
	}

	public DatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		this.context = context;
	}

	public DatabaseHelper(Context context) {
		this(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Note.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource, int arg2, int arg3) {
		try {
			TableUtils.dropTable(connectionSource, Note.class, true);
			onCreate(sqLiteDatabase, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
