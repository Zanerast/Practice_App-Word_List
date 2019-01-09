package com.example.zane.roomwordssample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zane.roomwordssample.room.WordEntity;
import com.example.zane.roomwordssample.room.WordViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

	public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

	private WordViewModel viewModel;
	WordListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupRV();

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
				startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
			}
		});

		viewModel = ViewModelProviders.of(this).get(WordViewModel.class);
		viewModel.getAllWordsList().observe(this, new Observer<List<WordEntity>>() {
			@Override
			public void onChanged(@Nullable List<WordEntity> wordEntities) {
				adapter.setWordList(wordEntities);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_clear_data) {
			Toast.makeText(this, "Clearing Data!", Toast.LENGTH_SHORT).show();

			viewModel.deleteAll();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
			WordEntity word = new WordEntity(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
			viewModel.insert(word);
		} else {
			Toast.makeText(
							this,
							R.string.empty_not_saved,
							Toast.LENGTH_SHORT).show();
		}
	}

	private void setupRV(){
		RecyclerView rv = findViewById(R.id.rv_words);
		rv.setLayoutManager(
						new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		adapter = new WordListAdapter();
		rv.setAdapter(adapter);

		ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
						0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
			@Override
			public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
				return false;
			}

			@Override
			public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
				int position = viewHolder.getAdapterPosition();
				WordEntity wordEntity = adapter.getWordAtPosition(position);
				Toast.makeText(MainActivity.this, "Deleting Word: " + wordEntity.getWord(), Toast.LENGTH_SHORT).show();

				viewModel.deleteWord(wordEntity);
			}
		});

		touchHelper.attachToRecyclerView(rv);
	}
}
