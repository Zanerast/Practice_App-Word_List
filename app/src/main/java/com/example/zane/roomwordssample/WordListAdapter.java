package com.example.zane.roomwordssample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zane.roomwordssample.room.WordEntity;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

	private List<WordEntity> wordList;

	WordListAdapter() {}

	@NonNull
	@Override
	public WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View viewHolder = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv, viewGroup, false);
		return new WordViewHolder(viewHolder);
	}

	@Override
	public void onBindViewHolder(@NonNull WordViewHolder wordViewHolder, int i) {
		wordViewHolder.bind();
	}

	@Override
	public int getItemCount() {
		if (wordList != null)
			return wordList.size();
		return 0;
	}

	public void setWordList(List<WordEntity> wordList) {
		this.wordList = wordList;
		notifyDataSetChanged();
	}

	/**
	 * Inner Class to create view holders
	 */
	class WordViewHolder extends RecyclerView.ViewHolder {

		private TextView tvWord;

		WordViewHolder(@NonNull View itemView) {
			super(itemView);

			tvWord = itemView.findViewById(R.id.tv_rv_item);
		}

		void bind() {
			tvWord.setText(wordList.get(getAdapterPosition()).getWord());
		}
	}
}
