package com.xtel.ivipu.view.widget;

import android.support.design.widget.AppBarLayout;

/**
 * Created by vivhp on 1/24/2017.
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateEXPANDED();
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
//                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateIDLE();
            }
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onStateEXPANDED();

    public abstract void onStateIDLE();

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }
}