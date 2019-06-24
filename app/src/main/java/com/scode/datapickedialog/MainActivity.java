package com.scode.datapickedialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.scode.datapickedialog.DateDialog.TimeSelectDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView tv_date_time, tv_date, tv_time;
    TimeSelectDialog datetimeDialog, dateDialog, timeDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_date_time = findViewById(R.id.tv_date_time);
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);


        datetimeDialog = new TimeSelectDialog(this, new TimeSelectDialog.CallBack() {
            @Override
            public void selectTime(String time) {
//                    dialog.setMinTime(times[0] + "-" + times[1] + "-" + times[2] + " " + "00:00:00"); //该方法设置选择最小时间
                //该时间的数组
//                    String[] strings = new String[5];
//                    strings[0] = calendar.get(Calendar.YEAR) + "";
//                    strings[1] = (calendar.get(Calendar.MONTH) + 1) + "";
//                    strings[2] = (calendar.get(Calendar.DAY_OF_MONTH)) + "";
//                    strings[3] = AutoAddZero((calendar.get(Calendar.HOUR_OF_DAY))) + "";
//                    strings[4] = AutoAddZero((calendar.get(Calendar.MINUTE))) + "";
                tv_date_time.setText(time);
            }
        });

        dateDialog = new TimeSelectDialog(this, new TimeSelectDialog.CallBack1() {
            @Override
            public void selectTime(String[] times) {
                if (times != null && times.length > 3) {
//                    dialog.setMaxTime(times[0] + "-" + times[1] + "-" + times[2] + " " + "00:00:00"); //该方法设置选择最大时间
                    tv_date.setText(times[0] + "-" + times[1] + "-" + times[2]);
                } else {
                    Toast.makeText(MainActivity.this, "设置的时间有误", Toast.LENGTH_SHORT).show();
                }
            }
        }).setShowType(TimeSelectDialog.SHOW_ONLY_DATE);//设置只选择日期


        timeDialog = new TimeSelectDialog(this, new TimeSelectDialog.CallBack1() {
            @Override
            public void selectTime(String[] times) {
                if (times != null && times.length > 3) {
//                    dialog.setMaxTime(times[0] + "-" + times[1] + "-" + times[2] + " " + "00:00:00"); //该方法设置选择最大时间
                    tv_time.setText( times[3] + ":" + times[4]);
                } else {
                    Toast.makeText(MainActivity.this, "设置的时间有误", Toast.LENGTH_SHORT).show();
                }
            }
        }).setShowType(TimeSelectDialog.SHOW_ONLY_TIME);//设置只选择时间


        tv_date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datetimeDialog.show();
            }
        });


        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show();
            }
        });
    }
}
