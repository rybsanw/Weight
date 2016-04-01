package com.ryb.weight.model;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.ryb.weight.R;

public class SettingDialog extends AlertDialog
{
	public SettingDialog(Context context, int theme)
	{
		super(context, theme);
	}

	public SettingDialog(Context context)
	{
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_dialog);
	}

}
