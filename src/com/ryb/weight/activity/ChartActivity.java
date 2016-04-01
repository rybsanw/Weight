package com.ryb.weight.activity;

import com.ryb.weight.R;
import com.ryb.weight.util.MySharedPreferences;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChartActivity extends Activity
{
	private TextView titleText = null;
	private Button titleButton = null;

	private MySharedPreferences mySharedPreferences = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chart_layout);

		mySharedPreferences = MySharedPreferences.getInstance(ChartActivity.this);

		titleText = (TextView) findViewById(R.id.title_text);
		titleButton = (Button) findViewById(R.id.title_button);

		titleText.setText("图表");
		titleButton.setVisibility(TRIM_MEMORY_UI_HIDDEN);
	}
}
