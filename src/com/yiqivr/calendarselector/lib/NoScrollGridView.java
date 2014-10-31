package com.yiqivr.calendarselector.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author lvning
 * @version create time:2014-10-29_下午2:32:46
 * @Description TODO
 */
public class NoScrollGridView extends GridView {

	public NoScrollGridView(Context context) {
		super(context);

	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
