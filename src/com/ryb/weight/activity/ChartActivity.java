package com.ryb.weight.activity;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
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
	private Button leftButton,rightButton;

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
	
	private static int totalMonth;

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
		leftButton = (Button) findViewById(R.id.leftButton);
		rightButton = (Button) findViewById(R.id.rightButton);
		
		leftButton.setOnClickListener(new MyButtonClickListener());
		rightButton.setOnClickListener(new MyButtonClickListener());
		

		mySharedPreferences = MySharedPreferences
				.getInstance(ChartActivity.this);
		targetWeight = mySharedPreferences.getString("goalValue");

		currentDate = mySharedPreferences.getString("current_date");
		startWeight = mySharedPreferences.getString("startWeight");

		String[] allDate = currentDate.split("/");

		currentDateYearMonth = allDate[0] + "/" + allDate[1];
		setWeightList(currentDateYearMonth);
		totalMonth = monthStringToInt(currentDateYearMonth);

		titleText = (TextView) findViewById(R.id.title_text);
		titleButton = (Button) findViewById(R.id.title_button);
		dateText = (TextView) findViewById(R.id.dateText);

		dateText.setText(currentDateYearMonth);
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
	
	class MyButtonClickListener implements OnClickListener
	{

		@Override
		public void onClick(View arg0)
		{
			switch(arg0.getId())
			{
			case R.id.leftButton:
				totalMonth --;
				
				break;
			case R.id.rightButton:
				totalMonth ++;
				
				break;
			}
			
			currentDateYearMonth = monthIntToString(totalMonth);
			dateText.setText(currentDateYearMonth);
			setWeightList(currentDateYearMonth);
			chartView.setWeightList(weightList);
		}
		
	}
	
	/**
	 * 
	 * @param currentDateYearMonth "2016/5"
	 * @return 2016*12 + 5
	 */
	private int monthStringToInt(String currentDateYearMonth)
	{
		int ret = 0;
		String[] allDate;
		allDate = currentDateYearMonth.split("/");
		ret = Integer.parseInt(allDate[0]) * 12 + Integer.parseInt(allDate[1]);
		return ret;
	}
	
	/**
	 * 
	 * @param totalMonth 2016*12 + 5
	 * @return 2016/05
	 */
	private String monthIntToString(int totalMonth)
	{
		String ret = null;
		if(totalMonth % 12 == 0)
		{
			ret = String.valueOf(totalMonth / 12 - 1) + "/" + "12";
		}
		else
		{
			ret = String.valueOf(totalMonth / 12) + "/" + String.valueOf(totalMonth % 12);
		}
		
		
		return ret;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
