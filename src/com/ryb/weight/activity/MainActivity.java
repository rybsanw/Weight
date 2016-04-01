package com.ryb.weight.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.ryb.weight.view.BodyMassIndex;
import com.ryb.weight.view.RoundProgressBar;

public class MainActivity extends Activity
{
	public TextView title = null;
	public TextView myTextYear = null;
	public TextView myTextMonth = null;
	public TextView myTextWeight = null;
	public TextView myTextKg = null;
	public String date = "error";

	public Button title_button = null;
	public Button dialog_button = null;

	RoundProgressBar roundProgressBar = null;
	public BodyMassIndex bodyMassIndex = null;
	public SettingDialog settingDialog = null;

	int progress;
	float progress1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		title = (TextView) findViewById(R.id.title_text);
		title_button = (Button) findViewById(R.id.title_button);
		
		
		
		myTextYear = (TextView) findViewById(R.id.date_year);
		myTextMonth = (TextView) findViewById(R.id.date_month);
		myTextWeight = (TextView) findViewById(R.id.weight);
		myTextKg = (TextView) findViewById(R.id.kg);
		roundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar2);
		bodyMassIndex = (BodyMassIndex) findViewById(R.id.bodyMassIndex);
		
		settingDialog = new SettingDialog(this,R.style.dialog);//创建Dialog并设置样式主题
		Window win = settingDialog.getWindow();
		LayoutParams params = new LayoutParams();
		params.x = 250;//设置x坐标
		params.y = -300;//设置y坐标
		win.setAttributes(params);
		settingDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
		//settingDialog.show();
		
		//dialog_button = (Button) settingDialog.findViewById(R.id.setting_dialog);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d", Locale.CHINA);
		date = sdf.format(new Date());

		myTextYear.setText(date.subSequence(0, 4));
		myTextMonth.setText(date.subSequence(5, date.length()));

		myTextWeight.setOnClickListener(new MyOnClickListener());
		title_button.setOnClickListener(new MyOnClickListener());
		//dialog_button.setOnClickListener(new MyOnClickListener());
		roundProgressBar.setTextTop("To the goal");
		
		
		/*dialog_button.setOnClickListener(new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				// TODO Auto-generated method stub
				
			}
		});*/
		
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (progress <= 100)
				{
					progress += 3;

					roundProgressBar.setProgress(progress);
					try
					{
						Thread.sleep(100);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}

			}
		}).start();
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (progress1 <= 50)
				{
					progress1 += 0.5;

					bodyMassIndex.setBMI(progress1);
					try
					{
						Thread.sleep(200);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}

			}
		}).start();

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
						.setTitle("输入体重")
						.setView(layout)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface arg0,
											int arg1)
									{
										myTextWeight.setText(edValue_weight
												.getText().toString());
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
				break;
			}
		}

	}
}
