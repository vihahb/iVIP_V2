package com.xtel.ivipu.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.xtel.ivipu.R;


/**
 * Created by thangnm on 7/8/16.
 */
public class LinearLayoutAnimationSlideBottom extends LinearLayout {
    private Animation inAnimation;
    private Animation outAnimation;

    public LinearLayoutAnimationSlideBottom(Context context) {
        super(context);
        initAnimation(context);
    }

    public LinearLayoutAnimationSlideBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimation(context);
    }

    public LinearLayoutAnimationSlideBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimation(context);
    }

    private void initAnimation(Context context) {
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.view_show_translate);

        outAnimation = AnimationUtils.loadAnimation(context, R.anim.view_hide_translate);
    }

    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            if (visibility == VISIBLE) {
                super.setVisibility(visibility);
                if (inAnimation != null) startAnimation(inAnimation);
            } else if ((visibility == INVISIBLE) || (visibility == GONE)) {
                if (outAnimation != null) startAnimation(outAnimation);
                super.setVisibility(visibility);
            }
        }

    }
}
