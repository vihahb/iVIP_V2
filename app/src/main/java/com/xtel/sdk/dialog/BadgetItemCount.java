package com.xtel.sdk.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.xtel.ivipu.R;

/**
 * Created by vihahb on 1/13/2017.
 */

public class BadgetItemCount extends Drawable {

    private float mTextSize;
    private Paint mBadgetPaint;
    private Paint mTextPaint;
    private Rect mTextRect = new Rect();

    private String mCount = "";
    private boolean mWillDraw = false;

    public BadgetItemCount(Context context) {

        mTextSize = context.getResources().getDimensionPixelSize(R.dimen.font_13);

        mBadgetPaint = new Paint();
        mBadgetPaint.setColor(Color.RED);
        mBadgetPaint.setAntiAlias(true);
        mBadgetPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mWillDraw)
            return;

        Rect bounds = getBounds();
        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;


        //Position the badge in the top - right quadrant of the icon
        float radius;
//        = ((Math.min(width, height) / 2) - 1) / 2;
        if (Integer.parseInt(this.mCount) < 10) {
            radius = Math.min(width, height) / 4.0f + 2.5F;
        } else {
            radius = Math.min(width, height) / 4.0f + 4.5F;
        }

        float centerX = width - radius;
        float centerY = radius + 2;

        //Draw badge circle
        canvas.drawCircle(centerX, centerY, radius, mBadgetPaint);

        // Draw badge count text inside the circle.

        mTextPaint.getTextBounds(mCount, 0, mCount.length(), mTextRect);
        float textHeight = mTextRect.bottom - mTextRect.top;
        float textY = centerY + (textHeight / 2f);
        canvas.drawText(mCount, centerX, textY, mTextPaint);
    }

    /*
    Sets the count (i.e notifications) to display.
     */
    public void setCount(int count) {
        mCount = Integer.toString(count);

        //Only draw a badge if there are notifications.
        mWillDraw = count > 0;
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
