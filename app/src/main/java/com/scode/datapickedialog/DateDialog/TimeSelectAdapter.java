package com.scode.datapickedialog.DateDialog;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.scode.datapickedialog.R;

import java.util.ArrayList;
import java.util.List;

import static com.scode.datapickedialog.DateDialog.TimeSelectDialog.SHOW_ALL;
import static com.scode.datapickedialog.DateDialog.TimeSelectDialog.SHOW_ONLY_DATE;
import static com.scode.datapickedialog.DateDialog.TimeSelectDialog.SHOW_ONLY_TIME;


/**
 * Created by ZWX on 2018/10/26.
 */
//时间选择
public class TimeSelectAdapter extends PagerAdapter {
    String[] times = new String[]{};//长度为2 保存【年月日】及【时分】
    int[] resIds = {R.layout.item_time_select_datepicker, R.layout.item_time_select_timepicker};
    List<View> views = new ArrayList<>();
    Context context;
    private ViewPager viewPager;
    private String showType;//展示类型

    public String[] getTimes() {
        return times;
    }

    public TimeSelectAdapter(Context context, ViewPager viewPager, String[] times, String showType) {
        this.times = times;
        this.showType = showType;
        this.context = context;
        this.viewPager = viewPager;
        initSelectType();
    }


    private void initSelectType() {
        times[0] = TimeUtil.getNowTimeyyMMdd();
        times[1] = TimeUtil.getNowTimehhmm();
        switch (showType) {
            case SHOW_ALL:
                views.add(LayoutInflater.from(context).inflate(resIds[0], null));
                views.add(LayoutInflater.from(context).inflate(resIds[1], null));
                break;
            case SHOW_ONLY_DATE:
//                times = new String[]{TimeUtil.getNowTimeyyMMdd()};
                views.add(LayoutInflater.from(context).inflate(resIds[0], null));
                break;
            case SHOW_ONLY_TIME:
//                times = new String[]{TimeUtil.getNowTimehhmm()};
                views.add(LayoutInflater.from(context).inflate(resIds[1], null));
                break;
        }
    }

    @Override
    public int getCount() {
        if (showType.equals(SHOW_ALL)){
            return times.length;
        }
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        switch (position) {
            case 0:
                if(showType.equals(SHOW_ONLY_TIME)){
                    //datePicker
                    TimePicker timePicker = views.get(0).findViewById(R.id.view_time_picker);
                    timePicker.setIs24HourView(true);
                    timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                        @Override
                        public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                            String time = TimeUtil.AutoAddZero(i) + ":" + TimeUtil.AutoAddZero(i1) ;
                            if (times[1].equals(time)) return;
                            times[1] = time;
                            notifyDataSetChanged();
                        }
                    });

                }else{
                    //datePicker
                    DatePicker picker = views.get(0).findViewById(R.id.view_date_picker);
                    hideDatePickerHeader(picker);
                    String[] date = times[0].split("-");
                    picker.init(Integer.parseInt(date[0]), Integer.parseInt(date[1])-1, Integer.parseInt(date[2]), new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                            if (times.length == 2) {
                                times[0] = i + "-" + (i1+1) + "-" + i2 + "";
                                if (showType.equals(SHOW_ALL)){
                                    viewPager.setCurrentItem(1);
                                }
                                notifyDataSetChanged();
                            }
                        }
                    });
                }
                break;
            case 1:
                //datePicker
                TimePicker timePicker = views.get(1).findViewById(R.id.view_time_picker);
                timePicker.setIs24HourView(true);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                        String time = TimeUtil.AutoAddZero(i) + ":" + TimeUtil.AutoAddZero(i1) ;
                        if (times[1].equals(time)) return;
                        times[1] = time;
                        notifyDataSetChanged();
                    }
                });
                break;
        }
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (showType.equals(SHOW_ONLY_TIME)){
            return times[1];
        }
        return times[position];
    }

    //用于隐藏日期选择头部
    private void hideDatePickerHeader(DatePicker datePicker) {
        ViewGroup rootView = (ViewGroup) datePicker.getChildAt(0);
        if (rootView == null) {
            return;
        }
        View headerView = rootView.getChildAt(0);
        if (headerView == null) {
            return;
        } //5.0+
        int headerId = context.getResources().getIdentifier("day_picker_selector_layout", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParamsRoot = rootView.getLayoutParams();
            layoutParamsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            rootView.setLayoutParams(layoutParamsRoot);
            ViewGroup animator = (ViewGroup) rootView.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(layoutParamsAnimator);
            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(layoutParamsChild);
            return;
        } //6.0+
        headerId = context.getResources().getIdentifier("date_picker_header", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
        }
    }


}
