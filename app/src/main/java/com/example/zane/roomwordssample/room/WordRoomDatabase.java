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
		}
		return DATABASE_INSTANCE;
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
