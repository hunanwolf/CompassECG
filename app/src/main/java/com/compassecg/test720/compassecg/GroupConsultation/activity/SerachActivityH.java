package com.compassecg.test720.compassecg.GroupConsultation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.compassecg.test720.compassecg.R;
import com.test720.auxiliary.Utils.NoBarBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerachActivityH extends NoBarBaseActivity {

    private RelativeLayout rl_search;
    private ListView lv_search;
    private TextView tv_serach;
    private ImageView iv_back;
    private LinearLayout ll_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);
        initView();
        setAdapter();
        setListenner();
    }

    private void setAdapter() {
        List< Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        for(int i=0;i<5;i++){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("content","暗号");
            list.add(map);
        }
        String[] str={"content"};
        int[] in={R.id.tv_content};
        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.item_search_group,str,in);
        lv_search.setAdapter(adapter);
    }

    private void setListenner() {
        ll_search.setOnClickListener(this);
        tv_serach.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    private void initView() {
        rl_search=getView(R.id.rl_search);
        lv_search=getView(R.id.lv_search);
        tv_serach=getView(R.id.tv_serach);
        ll_search=getView(R.id.ll_search);
        iv_back=getView(R.id.iv_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_search:
                if(rl_search.getVisibility()==View.GONE){
                    rl_search.setVisibility(View.VISIBLE);
                }else {
                    rl_search.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_serach:
                //搜索
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
