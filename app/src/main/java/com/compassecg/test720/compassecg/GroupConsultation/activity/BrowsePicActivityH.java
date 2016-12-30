package com.compassecg.test720.compassecg.GroupConsultation.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.compassecg.test720.compassecg.GroupConsultation.adapter.GroupKindsAdapterH;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.View.MyListView;
import com.orhanobut.logger.Logger;
import com.test720.auxiliary.Utils.NoBarBaseActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BrowsePicActivityH extends NoBarBaseActivity {

    private ViewPager viewPager;
    private TextView tv_page;
    private List<View> viewList;
    private View view1;
    private String path;
    private String position;
    List<String> list=new ArrayList<String>();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_pic_h);
        Intent intent=getIntent();
        position=intent.getStringExtra("position");
        list= (List<String>) intent.getSerializableExtra("path");
        //Log.i("WOLF",list.size()+"");
        Logger.d(list);
        initView();
        setAdapter();
        setListenner();
    }

    private void setListenner() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_page.setText((position+1)+"/"+list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setAdapter() {

        //设置viewPager数据
        LayoutInflater inflater = getLayoutInflater();
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        for (int i = 0; i < list.size(); i++) {
            view1 = inflater.inflate(R.layout.item_viewpager_pic_h, null);
            imageView = (ImageView) view1.findViewById(R.id.iv1);
            //String path_pic = null;
            Glide.with(this).load(list.get(i)).into(imageView);
            viewList.add(view1);
        }

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Integer.parseInt(position));
        tv_page.setText((Integer.parseInt(position)+1)+"/"+list.size());
    }

    private void initView() {
        viewPager=getView(R.id.viewPager);
        tv_page=getView(R.id.tv1);
    }
}
