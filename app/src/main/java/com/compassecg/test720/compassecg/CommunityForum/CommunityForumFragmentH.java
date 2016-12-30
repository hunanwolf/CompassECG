package com.compassecg.test720.compassecg.CommunityForum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.compassecg.test720.compassecg.CommunityForum.activity.AddAnswer2ActivityH;
import com.compassecg.test720.compassecg.CommunityForum.adapter.MarkPagerAdapterH;
import com.compassecg.test720.compassecg.CommunityForum.fragment.CommunityAllFragmentH;
import com.compassecg.test720.compassecg.GroupConsultation.GroupConsultationFragmentH;
import com.compassecg.test720.compassecg.GroupConsultation.activity.CreateGroupActivityH;
import com.compassecg.test720.compassecg.GroupConsultation.activity.SerachActivityH;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.View.CustomViewPager;
import com.test720.auxiliary.Utils.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/12/6.
 */

public class CommunityForumFragmentH extends BaseFragment {


    public static CommunityForumFragmentH maninfrag=null;
    private ImageView iv_create;
    private ImageView iv_search;

    private List<Fragment> pagerContent = new ArrayList<>();
    private TabLayout tabs;

    private CustomViewPager pager;
    private MarkPagerAdapterH pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView(inflater);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setAdapter();
        setListener();
    }

    private void setListener() {
        iv_create.setOnClickListener(this);
        iv_search.setOnClickListener(this);

    }

    private void setAdapter() {

    }

    private void initView(LayoutInflater inflater) {
        rootView=inflater.inflate(R.layout.fragment_community_forum, null);
        iv_create=(ImageView)rootView.findViewById(R.id.iv_create);
        iv_search=(ImageView)rootView.findViewById(R.id.iv_search);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_create:
                Intent intent3=new Intent(getActivity(), AddAnswer2ActivityH.class);
                startActivity(intent3);
                break;
            case R.id.iv_search:
                Intent intent4=new Intent(getActivity(), SerachActivityH.class);
                startActivity(intent4);
                break;
        }
    }

    public void initView() {

        tabs=getView(R.id.tabs);
        pager=getView(R.id.pager);
        pagerContent.add(new CommunityAllFragmentH());
        pagerContent.add(new GroupConsultationFragmentH());
        pagerContent.add(new GroupConsultationFragmentH());
        pagerContent.add(new GroupConsultationFragmentH());
        pagerContent.add(new GroupConsultationFragmentH());

//        tabs.setTabMode(TabLayout.MODE_FIXED);
        //1.MODE_SCROLLABLE模式
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
//        //2.MODE_FIXED模式
//        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.addTab(tabs.newTab().setText("全部"));
        tabs.addTab(tabs.newTab().setText("ECG"));
        tabs.addTab(tabs.newTab().setText("DCG"));
        tabs.addTab(tabs.newTab().setText("ABPM"));
        tabs.addTab(tabs.newTab().setText("其他"));
        pagerAdapter = new MarkPagerAdapterH(getChildFragmentManager(), pagerContent);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);
        pager.setPagingEnabled(false);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                tabs.getTabAt(position).select();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        pager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

    }

}
