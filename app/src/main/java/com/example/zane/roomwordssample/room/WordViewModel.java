package com.example.zane.roomwordssample.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

	private WordRepo wordRepo;
	private LiveData<List<WordEntity>> allWordsList;

	public WordViewModel(@NonNull Application application) {
		super(application);

		wordRepo = new WordRepo(application);
		allWordsList = wordRepo.getAllWordsList();
	}

	public LiveData<List<WordEntity>> getAllWordsList(){
		return allWordsList;
	}

	public void insert(WordEntity wordEntity){
		wordRepo.insert(wordEntity);
	}
}
