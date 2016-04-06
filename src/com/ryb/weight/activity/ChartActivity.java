package com.ryb.weight.activity;

import java.util.Arrays;
import java.util.List;

import com.ryb.weight.R;
import com.ryb.weight.util.MySharedPreferences;
import com.ryb.weight.view.MyView;

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
	
	private MyView chartView = null;
	
	private String targetWeight,currentWeight;
	
	private String startWeight = null;
	
	private String currentDate,currentDateYearMonth;
	
	private List<String> weightList = Arrays.asList("", "", "80.0",
			"80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0",
			"80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0",
			"80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0",
			"80.0", "80.0", "80.0", "80.0");

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chart_layout);
		
		chartView = (MyView) findViewById(R.id.chart);

		mySharedPreferences = MySharedPreferences.getInstance(ChartActivity.this);
		targetWeight = mySharedPreferences.getString("goalValue");
		
		currentDate = mySharedPreferences.getString("current_date");
		startWeight = mySharedPreferences.getString("startWeight");
		
		String[] allDate = currentDate.split("/");
		
		currentDateYearMonth = allDate[0] + "/" +allDate[1];
		setWeightList(currentDateYearMonth);

		titleText = (TextView) findViewById(R.id.title_text);
		titleButton = (Button) findViewById(R.id.title_button);

		
		titleText.setText("图表");
		titleButton.setVisibility(TRIM_MEMORY_UI_HIDDEN);
		
		chartView.setTargetWeightValue(Float.parseFloat(targetWeight));
		chartView.setStartWeightValue(Float.parseFloat(startWeight));
		chartView.setWeightList(weightList);
	}
	
	public void setWeightList(String currentDateYearMonth)
	{
		for(int i = 1;i <= 31;i ++)
		{
			String temp = currentDateYearMonth + "/" + i;
			weightList.set(i - 1, mySharedPreferences.getString(temp));
		}
	}
}
