package com.test720.auxiliary.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

public class CustomNumberPicker extends NumberPicker {

	public CustomNumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void addView(View child){
		super.addView(child);
		updateView(child);    
	}
	@Override
	public void addView(View child, int index,android.view.ViewGroup.LayoutParams params)
	{        
		super.addView(child, index, params);
		updateView(child);
	}
	@Override
	 public void addView(View child, android.view.ViewGroup.LayoutParams params) {
		 super.addView(child, params);
		 updateView(child);
	}
	public void updateView(View view){
		if (view instanceof EditText){
			((EditText) view).setTextSize(16);
			((EditText) view).setTextColor(Color.BLACK);
		}
	}

	/**
	 * 设置分割线的颜色值
	 * @param numberPicker
	 */
	@SuppressWarnings("unused")
	public void setNumberPickerDividerColor(NumberPicker numberPicker) {
		NumberPicker picker = numberPicker;
		Field[] pickerFields = NumberPicker.class.getDeclaredFields();
		for (Field pf : pickerFields) {
			if (pf.getName().equals("mSelectionDivider")) {
				pf.setAccessible(true);
				try {
					pf.set(picker, new ColorDrawable(Color.RED));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Resources.NotFoundException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}