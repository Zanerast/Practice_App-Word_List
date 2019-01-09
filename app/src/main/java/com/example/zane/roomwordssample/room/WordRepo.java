package com.example.zane.roomwordssample.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepo {

	private WordDao wordDao;
	private LiveData<List<WordEntity>> allWordsList;

	WordRepo(Application application){
		WordRoomDatabase database = WordRoomDatabase.getDatabase(application);
		wordDao = database.wordDao();
		allWordsList = wordDao.getAllWords();
	}

	LiveData<List<WordEntity>> getAllWordsList(){
		return this.allWordsList;
	}

	void insert(final WordEntity wordEntity){
		Exeggutor.getExeggutor().diskIo().execute(new Runnable() {
			@Override
			public void run() {
				wordDao.insert(wordEntity);
			}
		});
	}

	void deleteAll(){
		Exeggutor.getExeggutor().diskIo().execute(new Runnable() {
			@Override
			public void run() {
				wordDao.deleteAll();
			}
		});
	}

	void deleteWord(final WordEntity wordEntity){
		Exeggutor.getExeggutor().diskIo().execute(new Runnable() {
			@Override
			public void run() {
				wordDao.deleteWord(wordEntity);
			}
		});
	}

}
