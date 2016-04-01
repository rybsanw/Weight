package com.ryb.weight.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ryb.weight.R;

public class TitleLayout extends LinearLayout
{
	TextView title = null;
	Button title_button = null;
	public TitleLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.title, this);
		
		title = (TextView) findViewById(R.id.title_text);
		title_button = (Button) findViewById(R.id.title_button);
		
		title.setText("Weight Log");
		/*title_button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				//Toast.makeText(getContext(), "You click the edit button", Toast.LENGTH_SHORT);
				//title.setText("******");
				
				
			}
		});*/
	}
}
