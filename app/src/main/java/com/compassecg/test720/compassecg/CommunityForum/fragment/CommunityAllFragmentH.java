package com.compassecg.test720.compassecg.CommunityForum.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.compassecg.test720.compassecg.CommunityForum.activity.PostDetailsActivityH;
import com.compassecg.test720.compassecg.CommunityForum.adapter.CommunityAdapterH;
import com.compassecg.test720.compassecg.GroupConsultation.activity.CreateGroupActivityH;
import com.compassecg.test720.compassecg.GroupConsultation.activity.GroupActivityH;
import com.compassecg.test720.compassecg.GroupConsultation.activity.SerachActivityH;
import com.compassecg.test720.compassecg.GroupConsultation.adapter.GroupAdapterH;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.View.MyListView;
import com.test720.auxiliary.Utils.BaseFragment;

/**
 * Created by hp on 2016/12/6.
 */

public class CommunityAllFragmentH extends BaseFragment {


    public static CommunityAllFragmentH maninfrag=null;
    private MyListView lv_group;
    private CommunityAdapterH adapter;
    private RelativeLayout rl_quanguo;
    private RelativeLayout rl_city;
    private ImageView iv_create;
    private ImageView iv_search;
    private MyListView listView;

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
        setAdapter();
        setListener();
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), PostDetailsActivityH.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        adapter=new CommunityAdapterH(getContext());
        listView.setAdapter(adapter);
        listView.setFocusable(false);
    }

    private void initView(LayoutInflater inflater) {
        rootView=inflater.inflate(R.layout.fragment_community_all, null);
        //lv_group=getView(R.id.lv_group);
        listView=(MyListView)rootView.findViewById(R.id.listView);


    }

}
