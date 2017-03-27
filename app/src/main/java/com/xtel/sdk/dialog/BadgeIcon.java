package com.xtel.sdk.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.xtel.ivipu.R;

/**
 * Created by vihahb on 1/16/2017.
 */

@SuppressWarnings("WrongConstant")
public class BadgeIcon extends Drawable {

    private Paint mBadgePaint;
    private String mCount = "";
    private Paint mTextPaint;
    private float mTextSize;
    private Rect mTxtRect = new Rect();
    private boolean mWillDraw = false;

    public BadgeIcon(Context paramsContext) {
        this.mTextSize = paramsContext.getResources().getDimension(R.dimen.font_14);
        this.mBadgePaint = new Paint();
        this.mBadgePaint.setColor(paramsContext.getResources().getColor(R.color.badge_color));
        this.mBadgePaint.setAntiAlias(true);
        this.mBadgePaint.setStyle(Paint.Style.FILL);
        this.mTextPaint = new Paint();
        this.mTextPaint.setColor(-1);
        this.mTextPaint.setTypeface(Typeface.DEFAULT);
        this.mTextPaint.setTextSize(mTextSize);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setCount(int paramsInt) {
        this.mCount = Integer.toString(paramsInt);
        if (paramsInt > 0) {
            this.mWillDraw = true;
            invalidateSelf();
        }
    }

    @Override
    public void draw(Canvas paramsCanvas) {
        if (!this.mWillDraw) {
            return;
        }
        Rect localRect = getBounds();
        float width = localRect.right - localRect.left;
        float height = localRect.bottom - localRect.top;
        float circleRadius;
        circleRadius = Math.min(width, height) / 4.0f + 2.5f;
        if (Integer.parseInt(this.mCount) < 10) {
            circleRadius = Math.min(width, height) / 4.0f + 2.5f;
        } else {
            circleRadius = Math.min(width, height) / 4.0f + 4.5f;
        }
        float circleX = width - circleRadius + 6.2f;
        float circleY = circleRadius - 9.5F;
        paramsCanvas.drawCircle(circleX, circleY, circleRadius, this.mBadgePaint);
        this.mTextPaint.getTextBounds(this.mCount, 0, this.mCount.length(), this.mTxtRect);
        float textY = circleY + (this.mTxtRect.bottom - this.mTxtRect.top) / 2.0f;
        float textX = circleX;
        if (Integer.parseInt(this.mCount) >= 10) {
            textX = textX - 1.0f;
            textY = textY - 1.0f;
        }
        paramsCanvas.drawText(this.mCount, textX, textY, this.mTextPaint);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
