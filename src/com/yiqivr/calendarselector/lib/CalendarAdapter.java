package com.yiqivr.calendarselector.lib;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiqivr.calendarselector.R;

/**
 * @author lvning
 * @version create time:2014-10-28_下午5:37:27
 * @Description 日历表格适配器
 */
public class CalendarAdapter extends BaseAdapter {

	private ArrayList<Day> days;
	private LayoutInflater mInflater;
	private Calendar c;
	private Context context;
	private String orderDay;

	public CalendarAdapter(Context context, Calendar c, int passDays, String orderDay) {
		this.c = c;
		this.context = context;
		this.orderDay = orderDay;
		days = CalendarUtils.getDaysOfMonth(this.c, passDays, orderDay);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return days.size();
	}

	@Override
	public Day getItem(int arg0) {
		return days.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View v = arg1;
		ViewHolder holder = null;
		if (v == null) {
			v = mInflater.inflate(R.layout.calendar_item, null);
			holder = new ViewHolder();
			holder.tv = (TextView) v.findViewById(R.id.tv_calendar_item);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		Day d = getItem(arg0);
		switch (d.getType()) {
		case TODAY:
			setOrderThreeDayStyle(holder.tv, d.isOrdered(), context.getString(R.string.today));
			break;
		case TOMORROW:
			setOrderThreeDayStyle(holder.tv, d.isOrdered(), context.getString(R.string.tomorrow));
			break;
		case T_D_A_T:
			setOrderThreeDayStyle(holder.tv, d.isOrdered(), context.getString(R.string.t_d_a_t));
			break;
		case ENABLE:
			holder.tv.setText(d.isOrdered() ? d.getName() + context.getString(R.string.order_day) : d.getName());
			holder.tv.setEnabled(true);
			holder.tv.setTextColor(d.isOrdered() ? Color.WHITE : context.getResources().getColor(
					R.color.calendar_enable_color));
			holder.tv.setBackgroundResource(d.isOrdered() ? R.drawable.calendar_order_item_bg
					: R.drawable.normal_calendar_order_item_bg);
			holder.tv.setTextSize(d.isOrdered() ? context.getResources().getDimension(
					R.dimen.calendar_item_order_day_size) : context.getResources().getDimension(
					R.dimen.calendar_item_nonorder_day_size));
			break;
		case NOT_ENABLE:
			holder.tv.setText(d.getName());
			holder.tv.setEnabled(false);
			holder.tv.setTextColor(context.getResources().getColor(R.color.calendar_disable_color));
			holder.tv.setBackgroundColor(Color.WHITE);
			holder.tv.setTextSize(context.getResources().getDimension(R.dimen.calendar_item_nonorder_day_size));
			break;
		}

		return v;
	}

	private void setOrderThreeDayStyle(TextView tv, boolean ordered, String dayStr) {
		tv.setText(ordered ? dayStr + context.getString(R.string.order_day) : dayStr);
		tv.setEnabled(true);
		tv.setTextColor(ordered ? Color.WHITE : context.getResources().getColor(R.color.calendar_threeday_color));
		tv.setBackgroundResource(ordered ? R.drawable.calendar_order_item_bg : R.drawable.normal_calendar_order_item_bg);
		tv.setTextSize(ordered ? context.getResources().getDimension(R.dimen.calendar_item_order_day_size) : context
				.getResources().getDimension(R.dimen.calendar_item_nonorder_day_size));
	}

	static class ViewHolder {
		TextView tv;
	}

	public void previous() {
		if (c.get(Calendar.MONTH) == c.getActualMinimum(Calendar.MONTH)) {
			c.set((c.get(Calendar.YEAR) - 1), c.getActualMaximum(Calendar.MONTH), 1);
		} else {
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
		}
		days = CalendarUtils.getDaysOfMonth(c, 0, orderDay);
		notifyDataSetChanged();
	}

	public void next() {
		if (c.get(Calendar.MONTH) == c.getActualMaximum(Calendar.MONTH)) {
			c.set((c.get(Calendar.YEAR) + 1), c.getActualMinimum(Calendar.MONTH), 1);
		} else {
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
		}
		days = CalendarUtils.getDaysOfMonth(c, 0, orderDay);
		notifyDataSetChanged();
	}

}
