## CalendarSelector
===
### 仿淘宝预订日历
![sample_pic](https://github.com/lvning/CalendarSelector/blob/master/device.png)

##### 功能描述：
根据从今天起的可选择天数跳转到日历选择列表页面，选择好预订日后在*onActivityResult*方法中得到年月日（格式为“**年#月#日**”）。

##### 使用：

1 第一步跳转日历列表选择器


        Intent i = new  Intent(MainActivity.this, CalendarSelectorActivity.class);
        // 可选天数
		int days = 35;
		i.putExtra(CalendarSelectorActivity.DAYS_OF_SELECT, days);
		// 预订日期（可选）
		String order = "2014#11#11";
		i.putExtra(CalendarSelectorActivity.ORDER_DAY, order);
		startActivityForResult(i, 1);

2 第二步在*onActivityResult*方法中接收返回数据

	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			String orderInfo = data.getStringExtra(CalendarSelectorActivity.ORDER_DAY);
			orderEt.setText(orderInfo);
		}
	}