package com.ryb.weight.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class BodyMassIndex extends View
{
	/**
	 * 画笔对象的引用
	 */
	private Paint paint;
	private float bmiX;
	private float bmiValue;
	
	DisplayMetrics dm = getResources().getDisplayMetrics();
	
	float graphWidth = dm.widthPixels;
	
	float space = 20;
	float rectYPosition = 20;
	float rectHeight = 100;
	float rectYEndPosition = rectYPosition + rectHeight;
	float canvasWidth = (float)graphWidth - 2 * space;
	
	float strOneCentre = space + canvasWidth / 5;
	float strTwoCentre = strOneCentre + canvasWidth * 3/ 10;
	float strThreeCentre = strTwoCentre + canvasWidth / 5;
	float strFourCentre = strThreeCentre + canvasWidth / 5;

	private int textSize = 20;
	
	public BodyMassIndex(Context context)
	{
		super(context);
	}
	public BodyMassIndex(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}
	public BodyMassIndex(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		String strOne = "偏瘦 < 19";
		String strTwo = "正常 19 - 30";
		String strThree = "偏胖 30 - 39";
		String strFour = "超重 > 40";
		
		float strOneWidth = paint.measureText(strOne);
		float strTwoWidth = paint.measureText(strTwo);
		float strThreeWidth = paint.measureText(strThree);
		float strFourWidth = paint.measureText(strFour);
		
		paint.setStyle(Paint.Style.STROKE); // 设置空心
		paint.setAntiAlias(true); // 消除锯齿
		paint.setTextSize(textSize);

		String str = "BMI";
		float textWidth = paint.measureText(str);
		
		canvas.drawText(str, space, textWidth, paint); // 画出BMI字体
		
		canvas.translate(0, 20);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLUE);
		canvas.drawRect(space, rectYPosition, space + canvasWidth * 2 /5, rectYEndPosition, paint);
		paint.setColor(Color.GREEN);
		canvas.drawRect(space + canvasWidth * 2 /5, rectYPosition, space + canvasWidth * 3 /5, rectYEndPosition, paint);
		
		paint.setColor(Color.CYAN);
		canvas.drawRect(space + canvasWidth * 3 /5, rectYPosition, space + canvasWidth * 4 /5, rectYEndPosition, paint);
		
		paint.setColor(Color.RED);
		canvas.drawRect(space + canvasWidth * 4 /5, rectYPosition, space + canvasWidth * 5 /5, rectYEndPosition, paint);
		
		paint.setColor(Color.BLACK);
		canvas.drawText(strOne, strOneCentre - strOneWidth / 2, 20 + rectHeight/2, paint); 
		canvas.drawText(strTwo, strTwoCentre - strTwoWidth / 2 - 10, 20 + rectHeight/2, paint);
		canvas.drawText(strThree, strThreeCentre - strThreeWidth / 2 - 10, 20 + rectHeight/2, paint);
		canvas.drawText(strFour, strFourCentre - strFourWidth / 2 - 10, 20 + rectHeight/2, paint);
		
		//canvas.drawLine(100, 20, 100, 20 + rectHeight, paint);
		
		canvas.drawLine(bmiX, 20, bmiX, 20 + rectHeight, paint);
		canvas.drawText(String.valueOf(bmiValue), bmiX, 50 + rectHeight, paint);
		
	}

	public int getTextSize()
	{
		return textSize;
	}
	public void setTextSize(int textSize)
	{
		this.textSize = textSize;
	}
	
	public synchronized void setBMI(float value)
	{
		this.bmiValue = value;
		
		if(value > 0 && value <= 19)
		{
			bmiX = space + (value * canvasWidth * 2)/(19 * 5);
		}
		else if(value > 19 && value <= 30)
		{
			bmiX = space + 2 * canvasWidth/5 + ((value - 19) * canvasWidth)/(5 * 11);
		}
		else if(value > 30 && value <= 39)
		{
			bmiX = space + 3 * canvasWidth/5 + ((value - 30) * canvasWidth)/(5 * 9);
		}
		else
		{
			bmiX = space + 4 * canvasWidth/5 + ((value - 39) * canvasWidth)/(5 * 9);
		}
		
		postInvalidate();
	}

}
