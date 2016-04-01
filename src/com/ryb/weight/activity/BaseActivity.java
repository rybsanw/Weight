package com.ryb.weight.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity
{

	private static final String TAG = "BaseActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.d(TAG, "***onCreate : " + getClass().getSimpleName());
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		Log.d(TAG, "***onStart : " + getClass().getSimpleName());
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		Log.d(TAG, "***onResume : " + getClass().getSimpleName());
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		Log.d(TAG, "***onPause : " + getClass().getSimpleName());
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		Log.d(TAG, "***onStop : " + getClass().getSimpleName());
	}
	
	@Override
	protected void onRestart()
	{
		super.onRestart();
		Log.d(TAG, "***onRestart : " + getClass().getSimpleName());
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Log.d(TAG, "***onDestroy : " + getClass().getSimpleName());
	}

}
