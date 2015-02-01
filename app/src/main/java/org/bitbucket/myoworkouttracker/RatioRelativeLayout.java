package org.bitbucket.myoworkouttracker;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.content.Context;

/**
 * Used to create a width-dependent container in an unknown width environment.
 * Useful for displaying full width images in listviews, for instance.
 * 
 * @author Elijah Mirecki
 */
public class RatioRelativeLayout extends RelativeLayout {
	private float heightRatio;
	private int maxHeight;
	/**
	 * Layout file contstructor - Specify the heightRatio attribute for the scale of the height compared to the width (percentage)
	 */
	public RatioRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		heightRatio = attrs.getAttributeFloatValue(null, "heightRatio", 1.0f);
		maxHeight = -1;
	}
	/**
	 * Set's the max height for this view - Useful for landscape mode.
	 */
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// We have to manually resize the child views to match the parent.
		for (int i = 0; i < getChildCount(); i++) {
			final View child = getChildAt(i);
			final LayoutParams params = (RelativeLayout.LayoutParams)child.getLayoutParams();
			
			if (params.height == LayoutParams.MATCH_PARENT) {
				params.height = bottom - top;
			}
		}
		super.onLayout(changed, left, top, right, bottom);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = (int)Math.ceil(this.heightRatio * (float)width);
		if (maxHeight != -1 && height > maxHeight) height = maxHeight;
		setMeasuredDimension(width, height);
	}
}

