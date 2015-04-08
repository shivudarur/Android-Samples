package com.example.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Note {
	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	String title;

	public Note() {
		super();
	}

	public Note(String title) {
		super();
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + "]";
	}

	
}
