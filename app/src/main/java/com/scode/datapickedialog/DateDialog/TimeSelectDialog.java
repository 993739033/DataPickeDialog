package com.scode.datapickedialog.DateDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.scode.datapickedialog.R;

import java.lang.reflect.Field;
import java.text.ParseException;

/**
 * Created by ZWX on 2018/10/26.
 */

public class TimeSelectDialog extends Dialog {
    ViewPager vp_container;
    TabLayout view_tablayout;
    TextView btn_cancel;
    TextView btn_enter;

    String[] times = new String[]{"8888-88-88", "88:88"};
    //"8888-88-88 88:88:88"
    String minTime = "";//设置选择最小时间
    String maxTime = "";//设置选择最大时间

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String[] getTimes() {
        return times;
    }

    private Context context;
    private String selectTime;//选中的时间

    private CallBack callBack;
    private CallBack1 callBack1;//返回时间数组集合


    public static final String SHOW_ALL = "show_all";//展示所有日期时间控件
    public static final String SHOW_ONLY_DATE = "show_only_date";//只展示日期控件
    public static final String SHOW_ONLY_TIME = "show_only_time";//只展示时间控件

    private String showType = SHOW_ALL;

    public TimeSelectDialog setShowType(String showType) {
        this.showType = showType;
        return this;
    }

    public interface CallBack {
        void selectTime(String time);
    }

    public interface CallBack1 {
        void selectTime(String[] times);
    }


    public TimeSelectDialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
    }

    public TimeSelectDialog(@NonNull Context context, CallBack callBack) {
        this(context);
        this.callBack = callBack;
    }

    public TimeSelectDialog(@NonNull Context context, CallBack1 callBack1) {
        this(context);
        this.callBack1 = callBack1;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_time);
        vp_container = this.findViewById(R.id.vp_container);
        view_tablayout = this.findViewById(R.id.view_tablayout);
        btn_cancel = this.findViewById(R.id.btn_cancel);
        btn_enter = this.findViewById(R.id.btn_enter);

        times[0] = TimeUtil.getNowTimeyyMMdd();
        times[1] = TimeUtil.getNowTimehhmm();
        vp_container.setAdapter(new TimeSelectAdapter(context, vp_container, times,showType));
        view_tablayout.setupWithViewPager(vp_container);
        vp_container.setOffscreenPageLimit(2);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                times = ((TimeSelectAdapter) vp_container.getAdapter()).getTimes();
                if (!checkTime(times[0] + " " + times[1] + ":00")) {
                    if (callBack != null) {
                        callBack.selectTime("");//时间格式: yyyy-MM-dd HH:mm:ss
                    }

                    if (callBack1 != null) {
                        callBack1.selectTime(new String[]{});
                    }

                } else {
                    if (callBack != null) {
                        callBack.selectTime(times[0] + " " + times[1] + ":00");//时间格式: yyyy-MM-dd HH:mm:ss
                    }

                    if (callBack1 != null) {
                        try {
                            String[] t = TimeUtil.getArraysFromTime(times[0] + " " + times[1] + ":00");
                            callBack1.selectTime(t);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                dismiss();
            }
        });
    }

    public boolean checkTime(String selectTime) {
        try {
            long nowTime = TimeUtil.getSecondFromTime(selectTime);
            if (!TextUtils.isEmpty(minTime)) {
                long min = TimeUtil.getSecondFromTime(minTime);
                if (nowTime < min) {
                    return false;
                }
            }
            if (!TextUtils.isEmpty(maxTime)) {
                long max = TimeUtil.getSecondFromTime(maxTime);
                if (nowTime > max) {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    //通过反射获取tab的view进行操作
    public View getTabView(TabLayout.Tab tab) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = tab.getClass();
        Field field = clazz.getDeclaredField("mView");
        if(field != null){
            field.setAccessible(true);
            View fieldVal = (View) field.get(tab);//求得字段的值
            return fieldVal;
        }
        return null;
    }

}
