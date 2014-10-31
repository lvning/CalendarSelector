package com.yiqivr.calendarselector.lib;

import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiqivr.calendarselector.R;


/**
 * @author lvning
 * @version create time:2014-10-29_上午10:05:21
 * @Description 日历列表适配器
 */
public class CalendarListAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private int daysOfSelect;
	private String orderDay;
	private OnCalendarOrderListener listener;

	public CalendarListAdapter(Context context, int daysOfSelect, String orderDay) {
		this.context = context;
		this.daysOfSelect = daysOfSelect;
		this.orderDay = orderDay;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return CalendarUtils.throughMonth(Calendar.getInstance(), daysOfSelect) + 1;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
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
			v = mInflater.inflate(R.layout.calendar, arg2, false);
			holder = new ViewHolder();
			holder.yearAndMonth = (TextView) v.findViewById(R.id.tv_year_month);
			holder.calendarGrid = (NoScrollGridView) v.findViewById(R.id.gv_calendar_layout);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		final Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, arg0);
		holder.yearAndMonth.setText(c.get(Calendar.YEAR) + context.getString(R.string.year)
				+ (c.get(Calendar.MONTH) + 1) + context.getString(R.string.month));

		CalendarAdapter cAdapter = null;
		if (arg0 == 0) {
			cAdapter = new CalendarAdapter(context, c, daysOfSelect, orderDay);
		} else {
			int d = daysOfSelect - CalendarUtils.currentMonthRemainDays() - CalendarUtils.getFlowMonthDays(arg0 - 1);
			cAdapter = new CalendarAdapter(context, c, d, orderDay);
		}
		holder.calendarGrid.setAdapter(cAdapter);

		holder.calendarGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Calendar cl = (Calendar) c.clone();
				cl.set(Calendar.DAY_OF_MONTH, 1);
				int day = position + 2 - cl.get(Calendar.DAY_OF_WEEK);
				TextView dayTv = (TextView) view.findViewById(R.id.tv_calendar_item);
				if (day <= 0 || !dayTv.isEnabled())
					return;
				String orderInfo = c.get(Calendar.YEAR) + "#" + (c.get(Calendar.MONTH) + 1) + "#" + day;
				cl.clear();
				cl = null;
				if (listener != null)
					listener.onOrder(orderInfo);
			}
		});

		return v;
	}

	static class ViewHolder {
		TextView yearAndMonth;
		NoScrollGridView calendarGrid;
	}

	public void setOnCalendarOrderListener(OnCalendarOrderListener listener) {
		this.listener = listener;
	}

	public interface OnCalendarOrderListener {
		void onOrder(String orderInfo);
	}

}
