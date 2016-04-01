package com.ryb.weight.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ryb.weight.R;
import com.ryb.weight.util.MySharedPreferences;

public class SettingActivity extends BaseActivity
{
	private TextView titleText = null;
	private Button titleButton = null;

	private EditText goalValue = null;
	private EditText heightValue = null;

	private MySharedPreferences mySharedPreferences = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_layout);

		mySharedPreferences = MySharedPreferences
				.getInstance(SettingActivity.this);

		titleText = (TextView) findViewById(R.id.title_text);
		titleButton = (Button) findViewById(R.id.title_button);
		goalValue = (EditText) findViewById(R.id.goalValue);
		heightValue = (EditText) findViewById(R.id.heightValue);

		titleText.setText("设置");
		titleButton.setVisibility(TRIM_MEMORY_UI_HIDDEN);

		goalValue.setText(mySharedPreferences.getString("goalValue"));
		heightValue.setText(mySharedPreferences.getString("heightValue"));

		goalValue.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				mySharedPreferences.putString("goalValue", goalValue.getText().toString());

			}
		});

		heightValue.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{

			}

			@Override
			public void afterTextChanged(Editable arg0)
			{
				mySharedPreferences.putString("heightValue", heightValue.getText().toString());
			}
		});

	}

}
