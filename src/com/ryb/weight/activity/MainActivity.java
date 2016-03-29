package com.ryb.weight.activity;

import com.ryb.weight.R;
import com.ryb.weight.view.MyView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
		//setContentView(R.layout.activity_main);
		
	}
}
