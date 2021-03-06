package com.ryb.weight.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class MyView extends View
{

	DisplayMetrics dm = getResources().getDisplayMetrics();

	float graphWidth = dm.widthPixels - 200;
	float graphHeight = dm.heightPixels - 200;

	float originPointX = 100;
	float originPointY = 50;

	private float startWeightValue = (float) 84.4;
	private float targetWeightValue = (float) 75.0;

	private int outLineNum = 15;

	private List<String> weightList = Arrays.asList("", "", "80.0", "80.0",
			"80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0",
			"80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0",
			"80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0", "80.0",
			"80.0", "80.0", "80.0");

	public MyView(Context context)
	{
		super(context);
	}

	public MyView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public MyView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		/*
		 * float canvasWidth = (float)canvas.getWidth(); float canvasHeight =
		 * (float)canvas.getHeight();
		 * 
		 * float originPointX = 100; float originPointY = 50; float graphWidth =
		 * canvasWidth - 200; float graphHeight = canvasHeight - 200; float
		 * defaultWeightValue = (float) 84.4;
		 */

		canvas.drawColor(Color.WHITE);

		drawXY(canvas);
		drawOutLine(startWeightValue, originPointX, originPointY, graphHeight,
				canvas);
		drawDateText(originPointX, originPointY + graphHeight, graphWidth,
				canvas);

		/*
		 * drawValuePoint(10,(float)84.4,canvas);
		 * drawValuePoint(11,(float)83.7,canvas);
		 * drawValuePoint(12,(float)82.8,canvas);
		 */

		drawChart(weightList, canvas);
	}

	public float getGraphWidth()
	{
		return graphWidth;
	}

	public void setGraphWidth(float graphWidth)
	{
		this.graphWidth = graphWidth;
	}

	public float getGraphHeight()
	{
		return graphHeight;
	}

	public void setGraphHeight(float graphHeight)
	{
		this.graphHeight = graphHeight;
	}

	public float getOriginPointX()
	{
		return originPointX;
	}

	public void setOriginPointX(float originPointX)
	{
		this.originPointX = originPointX;
	}

	public float getOriginPointY()
	{
		return originPointY;
	}

	public void setOriginPointY(float originPointY)
	{
		this.originPointY = originPointY;
	}

	public List<String> getWeightList()
	{
		return weightList;
	}

	public void setWeightList(List<String> weightList)
	{
		this.weightList = weightList;
		postInvalidate();
	}

	private void drawChart(List<String> weightList, Canvas canvas)
	{
		for (int i = 0; i < weightList.size(); i++)
		{
			if (!TextUtils.isEmpty(weightList.get(i)))
			{
				drawValuePoint(i + 1, Float.parseFloat(weightList.get(i)),
						canvas);
			}

		}
		drawChartLine(weightList, canvas);
	}

	private void drawChartLine(List<String> weightList, Canvas canvas)
	{
		int startDay = 0, endDay = 0;
		float startWeight = 0, endWeight = 0;

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(2);
		paint.setColor(Color.BLACK);// 蓝色 

		for (int i = 1; i < 31; i++)
		{
			if (!TextUtils.isEmpty(weightList.get(i))
					&& !TextUtils.isEmpty(weightList.get(i - 1)))
			{
				startDay = i + 1;
				endDay = i;
				startWeight = Float.parseFloat(weightList.get(i));
				endWeight = Float.parseFloat(weightList.get(i - 1));

				canvas.drawLine(transformXCoordinate(startDay),
						transformYCoordinate(startWeight),
						transformXCoordinate(endDay),
						transformYCoordinate(endWeight), paint);// 绘制目标线
			}

		}

	}

	public float getStartWeightValue()
	{
		return startWeightValue;
	}

	public void setStartWeightValue(float startWeightValue)
	{
		this.startWeightValue = startWeightValue;
	}

	public float getTargetWeightValue()
	{
		return targetWeightValue;
	}

	public void setTargetWeightValue(float targetWeightValue)
	{
		this.targetWeightValue = targetWeightValue;
	}

	/**
	 * 绘出XY坐标
	 * 
	 * @param canvas
	 */
	private void drawXY(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(3);

		// 第一次绘制坐标轴  
		paint.setColor(0xff0000ff);// 蓝色  
		canvas.drawLine(originPointX, originPointY + graphHeight, originPointX
				+ graphWidth, originPointY + graphHeight, paint);// 绘制x轴  
		canvas.drawLine(originPointX, originPointY, originPointX, originPointY
				+ graphHeight, paint);// 绘制y轴
	}

	/**
	 * 绘出体重的分割线
	 * 
	 * @param inputValue
	 *            用户自定义的起始体重重量
	 * @param startX
	 *            以屏幕左上角为起点坐标的Y轴的x坐标
	 * @param startY
	 *            以屏幕左上角为起点坐标的Y轴的y坐标
	 * @param lengthY
	 *            Y轴的长度
	 * @param canvas
	 */
	@SuppressWarnings("null")
	private void drawOutLine(float inputValue, float startX, float startY,
			float lengthY, Canvas canvas)
	{
		float translateValue = lengthY / outLineNum;// 两线之间的间隔
		float translateY = startY + translateValue;
		float translateX = startX - 40;
		float averageTextValue = (startWeightValue - targetWeightValue)
				/ (outLineNum - 6);// 体重的平均跳变

		float textValue[] = new float[outLineNum];

		for (int i = 0; i < outLineNum; i++)
		{
			textValue[i] = keepOneDecimal(inputValue + (2 - i)
					* averageTextValue);
		}

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(1);
		paint.setColor(0xaa00aa00);// 蓝色 

		Paint paintText = new Paint();
		paintText.setTextSize(18);
		for (int i = 0; i < outLineNum; i++)
		{
			canvas.save();
			canvas.translate(translateX, translateY);// 将画笔向下移动  
			canvas.drawText(String.valueOf(textValue[i]), 0, 0, paintText);
			canvas.restore();
			paint.setColor(0xaa00aa00);// 蓝色 
			canvas.drawLine(startX, translateY, originPointX + graphWidth,
					translateY, paint);// 绘制间隔线
			if (i == outLineNum - 4)
			{
				Paint paintTarget = new Paint();
				paintTarget.setStyle(Paint.Style.STROKE);
				paintTarget.setStrokeCap(Paint.Cap.ROUND);
				paintTarget.setStrokeWidth(3);
				paintTarget.setColor(Color.RED);// 红色
				canvas.drawLine(startX, translateY, originPointX + graphWidth,
						translateY, paintTarget);// 绘制目标线
			}
			translateY += translateValue;
		}
	}

	/**
	 * 绘出日期的text
	 * 
	 * @param startX
	 *            相对于屏幕左上角X轴的x坐标
	 * @param startY
	 *            相对于屏幕左上角X轴的y坐标
	 * @param lengthX
	 *            X轴的长度
	 * @param canvas
	 */
	private void drawDateText(float startX, float startY, float lengthX,
			Canvas canvas)
	{
		int dateInterval = 4;
		int intervalNum = 31 / dateInterval;

		float translateY = startY + 31;

		int textValue[] = new int[intervalNum];

		for (int i = 0; i < intervalNum; i++)
		{
			textValue[i] = dateInterval + i * dateInterval;
		}

		Paint paintText = new Paint();
		paintText.setTextSize(18);

		for (int i = 0; i < intervalNum; i++)
		{
			canvas.save();
			canvas.translate(transformXCoordinate(textValue[i]), translateY);// 将画笔向下移动  
			canvas.drawText(String.valueOf(textValue[i]), 0, 0, paintText);
			canvas.restore();
		}
	}

	/**
	 * 返回输入时间的x坐标
	 * 
	 * @param day
	 *            ：输入的为1-31的某一天
	 * @return x:相对于屏幕左上角的x坐标
	 */
	private float transformXCoordinate(int day)
	{
		/*
		 * float graphWidth = dm.widthPixels - 200; float graphHeight =
		 * dm.heightPixels - 200; float originPointX = 100; float originPointY =
		 * 50;
		 */

		float x = 0;
		float daySpace = graphWidth / 31;

		x = originPointX + day * daySpace;

		return x;
	}

	/**
	 * 返回输入体重的Y坐标
	 * 
	 * @param weight
	 *            :输入的为体重（应在最高体重和最低体重之间）
	 * @return y：相对于屏幕左上角的y坐标
	 */
	private float transformYCoordinate(float weight)
	{
		/*
		 * float graphWidth = dm.widthPixels - 200; float graphHeight =
		 * dm.heightPixels - 200; float originPointX = 100; float originPointY =
		 * 50; //float defaultWeightValue = (float) 80.4;
		 * 
		 * float startWeightValue = (float) 84.4; float targetWeightValue =
		 * (float) 75; int outLineNum = 15;
		 */
		float averageTextValue = (startWeightValue - targetWeightValue)
				/ (outLineNum - 6);
		float topWeight = startWeightValue + 3 * averageTextValue;
		float bottomWeight = targetWeightValue - 3 * averageTextValue;

		float weightSpace = graphHeight / (topWeight - bottomWeight);

		float y = originPointY + (topWeight - weight) * weightSpace;

		return y;
	}

	/**
	 * 
	 * @param day
	 *            :输入的第几天（在1-31）
	 * @param weightValue
	 *            :输入的体重（在最高和最低之间）
	 * @param canvas
	 *            :
	 */
	private void drawValuePoint(int day, float weightValue, Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(3);

		paint.setColor(Color.BLACK);
		paint.setStrokeWidth((float) 10.0);
		canvas.drawPoint(transformXCoordinate(day),
				transformYCoordinate(weightValue), paint);
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

}
