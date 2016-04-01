package com.ryb.weight.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPreferences
{
	private static SharedPreferences prefs = null;
	private static SharedPreferences.Editor editor = null;
	private static MySharedPreferences mySharedPreferences = null;
	public static MySharedPreferences getInstance(Context context)
	{
		if(mySharedPreferences == null)
		{
			mySharedPreferences = new MySharedPreferences(context);
		}
		return mySharedPreferences;
	}
	private MySharedPreferences(Context context)
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		editor = prefs.edit();
	}
	
	public String getString(String str)
	{
		String ret = "error";
		
		ret = prefs.getString(str, "");
		return ret;
	}
	
	public void putString(String key,String value)
	{
		editor.putString(key, value);
		editor.commit();
	}
}
