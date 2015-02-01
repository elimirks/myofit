package org.bitbucket.myoworkouttracker;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.ColorFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

// Altered from http://stackoverflow.com/questions/15962745/

public class SemiCircleDrawable extends Drawable {
	private Paint rimPaint;
	private Paint rimBackgroundPaint;
	private Paint centerPaint;
	private RectF rectF;
	private int color;
	private int angle;

	public SemiCircleDrawable(int rimColor, int rimBackgroundColor,
			int centerColor, int angle) {
		this.color = color;
		this.angle = angle;
		rimPaint = new Paint();
		rimPaint.setColor(rimColor);
		rimPaint.setStyle(Paint.Style.FILL);
		rectF = new RectF();
		
		rimBackgroundPaint = new Paint();
		rimBackgroundPaint.setColor(rimBackgroundColor);
		rimBackgroundPaint.setStyle(Paint.Style.FILL);

		centerPaint = new Paint();
		centerPaint.setColor(centerColor);
		centerPaint.setStyle(Paint.Style.FILL);
	}

	public void setAngle(int angle) {
		this.angle = angle;
		invalidateSelf();
	}

	public void setRimColor(int rimColor) {
		rimPaint.setColor(rimColor);
		invalidateSelf();
	}

	public void setCenterColor(int centerColor) {
		centerPaint.setColor(centerColor);
		invalidateSelf();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		Rect bounds = getBounds();
		rectF.set(bounds);

		// Draw the arc clockwise, starting at the top.
		canvas.drawArc(rectF, 0, 360, true, rimBackgroundPaint);
		canvas.drawArc(rectF, -90, angle, true, rimPaint);

		// "Erase" a section in the center.
		int inset = 30; // TODO make this in dp, not px.
		rectF.inset(inset, inset);
		canvas.drawArc(rectF, 0, 360, true, centerPaint);
	}

	@Override
	public void setAlpha(int alpha) {
		// Has no effect
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// Has no effect
	}

	@Override
	public int getOpacity() {
		// Not Implemented
		return 0;
	}
}

