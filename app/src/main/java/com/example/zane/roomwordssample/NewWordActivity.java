package com.example.zane.roomwordssample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

	public static final String EXTRA_REPLY = "extra_reply";
	public static final String UPDATE_WORD = "update_word";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_word);

		final EditText et = findViewById(R.id.et_word_add);
		Button btnSave = findViewById(R.id.btn_save);
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				if (et.getText().toString().isEmpty()){
					setResult(RESULT_CANCELED, intent);
				} else {
					intent.putExtra(EXTRA_REPLY, et.getText().toString());
					setResult(RESULT_OK, intent);
				}
				finish();
			}
		});

	}
}
