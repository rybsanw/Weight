package com.ryb.weight.activity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ryb.weight.R;
import com.ryb.weight.model.SettingDialog;
import com.ryb.weight.util.MySharedPreferences;
import com.ryb.weight.view.BodyMassIndex;
import com.ryb.weight.view.RoundProgressBar;

public class MainActivity extends BaseActivity
{
	public TextView title = null;
	public TextView myTextYear = null;
	public TextView myTextMonth = null;
	public TextView myTextWeight = null;
	public TextView myTextKg = null;
	public static String date = "error";

	public Button title_button = null;
	public Button dialog_button = null;
	private Button chart_button = null;

	RoundProgressBar roundProgressBar = null;
	public BodyMassIndex bodyMassIndex = null;
	public SettingDialog settingDialog = null;

	int progress,progressTemp;
	
	private DisplayMetrics dm;
	
	private float width;
	private float height;
	
	private MySharedPreferences mySharedPreferences = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mySharedPreferences = MySharedPreferences.getInstance(MainActivity.this);
		if(TextUtils.isEmpty(mySharedPreferences.getString("isFirstStart")))
		{
			mySharedPreferences.putString("isFirstStart", "false");
			Intent intent = new Intent(MainActivity.this,SettingActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		
		
		
		setContentView(R.layout.activity_main);
		title = (TextView) findViewById(R.id.title_text);
		title_button = (Button) findViewById(R.id.title_button);
		chart_button = (Button) findViewById(R.id.chart_button); 
		
		myTextYear = (TextView) findViewById(R.id.date_year);
		myTextMonth = (TextView) findViewById(R.id.date_month);
		myTextWeight = (TextView) findViewById(R.id.weight);
		myTextKg = (TextView) findViewById(R.id.kg);
		roundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar2);
		bodyMassIndex = (BodyMassIndex) findViewById(R.id.bodyMassIndex);
		
		/*mySharedPreferences.putString("startWeight", "82.2");
		mySharedPreferences.putString("goalValue", "75.0");
		mySharedPreferences.putString("heightValue", "175");
		
		mySharedPreferences.putString("2016/4/1","82.6");
		mySharedPreferences.putString("2016/4/2","82.0");
		mySharedPreferences.putString("2016/4/3","83.5");
		mySharedPreferences.putString("2016/4/4","82.8");*/
		mySharedPreferences.putString("2016/4/4","82.8");
		
		
		dm = getResources().getDisplayMetrics();
		width = dm.widthPixels;
		height = dm.heightPixels;
		settingDialog = new SettingDialog(this,R.style.dialog);//创建Dialog并设置样式主题
		Window win = settingDialog.getWindow();
		LayoutParams params = new LayoutParams();
		params.x = (int) (width / 2 - 150);//设置x坐标
		params.y = (int) (- height / 2 - 120);//设置y坐标
		win.setAttributes(params);
		settingDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
		//settingDialog.show();
		
		//dialog_button = (Button) settingDialog.findViewById(R.id.setting_dialog);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d", Locale.CHINA);
		date = sdf.format(new Date());

		mySharedPreferences.putString("current_date",date);
		myTextYear.setText(date.subSequence(0, 4));
		myTextMonth.setText(date.subSequence(5, date.length()));

		myTextWeight.setOnClickListener(new MyOnClickListener());
		title_button.setOnClickListener(new MyOnClickListener());
		//dialog_button.setOnClickListener(new MyOnClickListener());
		chart_button.setOnClickListener(new MyOnClickListener());
		roundProgressBar.setTextTop("To the goal");
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		if(TextUtils.isEmpty(mySharedPreferences.getString(date)))
		{
			myTextWeight.setText("enter weight");
		}
		else
		{
			myTextWeight.setText(mySharedPreferences.getString("currentWeight"));
		}
		
		
		
		showRoundProgressBar();
		showBMI();
	}
	
	public void showBMI()
	{
		String weight = "0";
		if(TextUtils.isEmpty(mySharedPreferences.getString("currentWeight")))
		{
			weight = "0";
		}
		else
		{
			weight = mySharedPreferences.getString("currentWeight");
		}
		
		
		String height = mySharedPreferences.getString("heightValue");
		float bMI = Float.parseFloat(weight) * 10000/(Float.parseFloat(height) * Float.parseFloat(height));
		bodyMassIndex.setBMI(keepOneDecimal(bMI));
	}
	
	public void showRoundProgressBar()
	{
		String weight = "0";
		String goal = mySharedPreferences.getString("goalValue");
		if(TextUtils.isEmpty(mySharedPreferences.getString("currentWeight")))
		{
			weight = "0";
			roundProgressBar.setTextBottom("-- Kg");
			progress = 0;
		}
		else
		{
			weight = mySharedPreferences.getString("currentWeight");
			roundProgressBar.setTextBottom(String.valueOf(keepOneDecimal((Float.parseFloat(weight) - Float.parseFloat(goal)))) + " Kg");
			progress = (int) (100 * Float.parseFloat(goal)/Float.parseFloat(weight));
		}
		
		
		roundProgressBar.setProgress(progress);
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (progressTemp <= progress)
				{
					progressTemp += 2;
					
					roundProgressBar.setProgress(progressTemp);
					try
					{
						Thread.sleep(10);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				progressTemp = 0;
			}
		}).start();
	}

	/**
	 * 四舍五入的方法保留float数据小数点后两位数据
	 */
	public float keepOneDecimal(double d)
	{
		float ret;
		int scale = 1;// 设置位数
		int roundingMode = 4;// 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
		BigDecimal bd = new BigDecimal((double) d);
		bd = bd.setScale(scale, roundingMode);
		ret = bd.floatValue();
		return ret;
	}
	
	class MyOnClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.weight:
				Toast.makeText(MainActivity.this, "input your weight",
						Toast.LENGTH_SHORT).show();

				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.dialog_weight,
						(ViewGroup) findViewById(R.id.dialog_weight));
				final EditText edValue_weight = (EditText) layout
						.findViewById(R.id.etvalue_weight);
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("输入体重: kg")
						.setView(layout)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface arg0,
											int arg1)
									{
										if(TextUtils.isEmpty(edValue_weight.getText().toString()))
										{
											myTextWeight.setText("enter weight");
										}
										else
										{
											myTextWeight.setText(edValue_weight.getText().toString());
										}
										mySharedPreferences.putString("currentWeight",edValue_weight.getText().toString());
										mySharedPreferences.putString(date,edValue_weight.getText().toString());
										
										showRoundProgressBar();
										showBMI();
									}
								}).setNegativeButton("取消", null).show();
				break;
			case R.id.title_button:
				title.setText("touch the edit");
				settingDialog.show();
				/*
				 * dialog_button 的findViewById必须要在其view show出来之后才能进行及绑定监听器
				 */
				dialog_button = (Button) settingDialog.findViewById(R.id.setting_dialog);
				//dialog_button = (Button) findViewById(R.id.setting_dialog);
				dialog_button.setOnClickListener(new MyOnClickListener());
				break;
			case R.id.setting_dialog:
				title.setText("touch the dialog");
				Intent intent = new Intent(MainActivity.this,SettingActivity.class);
				startActivity(intent);
				settingDialog.dismiss();
				break;
			case R.id.chart_button:
				title.setText("touch the chart_button");
				Intent intent2 = new Intent(MainActivity.this,ChartActivity.class);
				startActivity(intent2);
				break;
			}
		}

	}
}
