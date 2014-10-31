package com.yiqivr.calendarselector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yiqivr.calendarselector.lib.CalendarSelectorActivity;

public class MainActivity extends Activity {
	private EditText orderEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void jump(View v) {
		EditText et = (EditText) findViewById(R.id.et_input);
		orderEt = (EditText) findViewById(R.id.et_order);
		int days = Integer.valueOf(et.getText().toString());
		String order = orderEt.getText().toString();
		Intent i = new Intent(MainActivity.this, CalendarSelectorActivity.class);
		i.putExtra(CalendarSelectorActivity.DAYS_OF_SELECT, days);
		i.putExtra(CalendarSelectorActivity.ORDER_DAY, order);
		startActivityForResult(i, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			String orderInfo = data.getStringExtra(CalendarSelectorActivity.ORDER_DAY);
			orderEt.setText(orderInfo);
		}
	}

}
