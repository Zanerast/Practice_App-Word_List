package com.example.zane.roomwordssample.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table")
public class WordEntity {

	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "word_column")
	private String word;

	public WordEntity(String word){
		this.word = word;
	}

	public String getWord() {
		return this.word;
	}
}
