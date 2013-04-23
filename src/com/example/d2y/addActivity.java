package com.example.d2y;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

public class addActivity extends Activity {
	final iuOpenHelper iuHelper = new iuOpenHelper(this, iuOpenHelper.DB_TABLE,
			null, 1);
	final idusActivityHelper idusHelper = new idusActivityHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addactivity);
		Button add_back = (Button) findViewById(R.id.add_back);
		Button add_ok = (Button) findViewById(R.id.add_ok);

		Calendar cal = Calendar.getInstance();

		// 定义DatePicker
		DatePicker dp = (DatePicker) findViewById(R.id.datepicker);
		int year = cal.get(Calendar.YEAR);
		int monthOfYear = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		dp.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				flushDate(year, monthOfYear, dayOfMonth);
			}
		});

		// 定义TimePicker
		TimePicker tp = (TimePicker) findViewById(R.id.timepicker);
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute) {
				flushTime(hour, minute);
			}
		});

		// 定义控件变量
		final EditText add_item = (EditText) findViewById(R.id.add_item02);
		final EditText add_tag = (EditText) findViewById(R.id.add_tag02);
		final TextView add_date = (TextView) findViewById(R.id.add_date02);
		final TextView add_time = (TextView) findViewById(R.id.add_time02);

		// 添加add_date的点击事件
		add_date.setClickable(true);
		add_date.setFocusable(true);
		add_date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		// 获取当前日期
		DateFormat date = DateFormat.getDateInstance();
		add_date.setText(date.format(new Date()));

		// 获取当前时间
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		add_time.setText(time.format(new Date()));

		add_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				setResult(RESULT_CANCELED, i);
				finish();
			}
		});
		add_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] addActivityArray = new String[4];
				addActivityArray[0] = add_item.getText().toString();
				addActivityArray[1] = add_tag.getText().toString();
				addActivityArray[2] = add_date.getText().toString();
				addActivityArray[3] = add_date.getText().toString();
				if (addActivityArray[0].equals("")) {
					Toast.makeText(addActivity.this, "对不起，请将活动填写完整",
							Toast.LENGTH_LONG).show();
				} else {
					idusHelper.insertActivity(iuHelper, addActivityArray);
					Intent i = new Intent();
					setResult(RESULT_OK, i);
					finish();
				}
			}
		});
	}

	// 刷新add_date所显示的内容
	public void flushDate(int year, int monthOfYear, int dayOfMonth) {
		final TextView add_date = (TextView) findViewById(R.id.add_date02);
		add_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
	}

	// 刷新add_time所显示的内容
	public void flushTime(int hour, int minute) {
		final TextView add_time = (TextView) findViewById(R.id.add_time02);
		add_time.setText(hour + ":" + minute);
	}
}