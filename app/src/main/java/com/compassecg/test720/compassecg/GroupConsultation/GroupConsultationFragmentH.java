package com.compassecg.test720.compassecg.GroupConsultation;

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

public class GroupConsultationFragmentH extends BaseFragment {


    public static GroupConsultationFragmentH maninfrag=null;
    private MyListView lv_group;
    private GroupAdapterH adapter;
    private RelativeLayout rl_quanguo;
    private RelativeLayout rl_city;
    private ImageView iv_create;
    private ImageView iv_search;

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
        rl_quanguo.setOnClickListener(this);
        rl_city.setOnClickListener(this);
        iv_create.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), GroupActivityH.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        adapter=new GroupAdapterH(getActivity());
        lv_group.setAdapter(adapter);
        lv_group.setFocusable(false);
    }

    private void initView(LayoutInflater inflater) {
        rootView=inflater.inflate(R.layout.fragment_consultation, null);
        //lv_group=getView(R.id.lv_group);
        lv_group=(MyListView)rootView.findViewById(R.id.lv_group);
        rl_quanguo=(RelativeLayout)rootView.findViewById(R.id.rl_quanguo);
        rl_city=(RelativeLayout)rootView.findViewById(R.id.rl_city);
        iv_create=(ImageView)rootView.findViewById(R.id.iv_create);
        iv_search=(ImageView)rootView.findViewById(R.id.iv_search);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_quanguo:
                Intent intent=new Intent(getActivity(), GroupActivityH.class);
                startActivity(intent);
                break;
            case R.id.rl_city:
                Intent intent2=new Intent(getActivity(), GroupActivityH.class);
                startActivity(intent2);
                break;
            case R.id.iv_create:
                Intent intent3=new Intent(getActivity(), CreateGroupActivityH.class);
                startActivity(intent3);
                break;
            case R.id.iv_search:
                Intent intent4=new Intent(getActivity(), SerachActivityH.class);
                startActivity(intent4);
                break;
        }
    }
}
