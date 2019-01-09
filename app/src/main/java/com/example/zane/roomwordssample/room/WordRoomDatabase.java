package com.example.zane.roomwordssample.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {WordEntity.class}, version = 1, exportSchema = false)
abstract class WordRoomDatabase extends RoomDatabase {

	abstract WordDao wordDao();

	private static WordRoomDatabase DATABASE_INSTANCE;

	static WordRoomDatabase getDatabase(final Context context) {
		if (DATABASE_INSTANCE == null) {
			synchronized (WordRoomDatabase.class) {
				DATABASE_INSTANCE = Room.databaseBuilder
								(context.getApplicationContext(), WordRoomDatabase.class, "word_database")
								.fallbackToDestructiveMigration()
								// .addCallback(roomCallback)
								.build();
			}
			getAnyWord();
		}
		return DATABASE_INSTANCE;
	}

	private static void getAnyWord(){
		final String[] initWordsList = {"Default", "Word", "List"};
		Exeggutor.getExeggutor().diskIo().execute(new Runnable() {
			@Override
			public void run() {
				if (DATABASE_INSTANCE.wordDao().getAnyWord().length < 1){
					for (String word : initWordsList) {
						WordEntity wordEntity = new WordEntity(word);
						DATABASE_INSTANCE.wordDao().insert(wordEntity);
					}
				}
			}
		});
	}

	// Start the app with a clean database every time.
	// Not needed if you only populate the database
	// when it is first created
	private static RoomDatabase.Callback roomCallback =
					new Callback() {
						@Override
						public void onOpen(@NonNull SupportSQLiteDatabase db) {
							super.onOpen(db);
							Exeggutor.getExeggutor().diskIo().execute(new Runnable() {
								@Override
								public void run() {
									DATABASE_INSTANCE.wordDao().deleteAll();
								}
							});
						}
					};

}
