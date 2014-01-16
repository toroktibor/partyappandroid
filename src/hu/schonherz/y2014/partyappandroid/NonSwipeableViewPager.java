package hu.schonherz.y2014.partyappandroid;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonSwipeableViewPager extends ViewPager {

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public NonSwipeableViewPager(Context context) {
	super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
	return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
	return false;
    }

}
