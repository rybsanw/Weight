package com.ryb.weight.activity;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ryb.weight.R;
import com.ryb.weight.util.MySharedPreferences;
import com.ryb.weight.view.MyView;

public class ChartActivity extends Activity
{
	private TextView titleText = null;
	private Button titleButton = null;

	private TextView dateText = null;

	private MySharedPreferences mySharedPreferences = null;

	private MyView chartView = null;

	private String targetWeight, currentWeight;

	private String startWeight = null;

	private String currentDate, currentDateYearMonth;

	private List<String> weightList = Arrays.asList("", "", "80.0", "80.0",
			"80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0",
			"80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0",
			"80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0",
			"80.0", "80.0", "80.0");

	private DisplayMetrics dm;
	
	private float graphWidth;
	private float graphHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chart_layout);

		dm = getResources().getDisplayMetrics();
		graphWidth = dm.widthPixels;
		graphHeight = dm.heightPixels;

		chartView = (MyView) findViewById(R.id.chart);

		mySharedPreferences = MySharedPreferences
				.getInstance(ChartActivity.this);
		targetWeight = mySharedPreferences.getString("goalValue");

		currentDate = mySharedPreferences.getString("current_date");
		startWeight = mySharedPreferences.getString("startWeight");

		String[] allDate = currentDate.split("/");

		currentDateYearMonth = allDate[0] + "/" + allDate[1];
		setWeightList(currentDateYearMonth);

		titleText = (TextView) findViewById(R.id.title_text);
		titleButton = (Button) findViewById(R.id.title_button);
		dateText = (TextView) findViewById(R.id.dateText);

		dateText.setText(currentDate);
		titleText.setText("图表");
		titleButton.setVisibility(TRIM_MEMORY_UI_HIDDEN);

		chartView.setOriginPointX(50);
		chartView.setOriginPointY(50);
		chartView.setGraphWidth(graphWidth - 100);
		chartView.setGraphHeight(7*graphHeight/10);
		chartView.setTargetWeightValue(Float.parseFloat(targetWeight));
		chartView.setStartWeightValue(Float.parseFloat(startWeight));
		chartView.setWeightList(weightList);
	}

	public void setWeightList(String currentDateYearMonth)
	{
		for (int i = 1; i <= 31; i++)
		{
			String temp = currentDateYearMonth + "/" + i;
			weightList.set(i - 1, mySharedPreferences.getString(temp));
		}
	}
}
