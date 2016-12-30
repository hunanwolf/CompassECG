package com.compassecg.test720.compassecg.GroupConsultation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.compassecg.test720.compassecg.R;
import com.test720.auxiliary.Utils.NoBarBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseGroupActivityH extends NoBarBaseActivity {

    private ListView lv_content;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group_activity_h);
        initView();
        setAdapter();
        setListenner();
    }

    private void setAdapter() {

        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        for(int i=0;i<5;i++){
            Map map=new HashMap<String,Object>();
            map.put("content","全国组");
            list.add(map);
        }
        String[] wolf={"content"};
        int[] ji={R.id.tv_content};
        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.item_choose_group,wolf,ji);
        lv_content.setAdapter(adapter);
    }

    private void setListenner() {
        iv_back.setOnClickListener(this);
    }

    private void initView() {
        lv_content=getView(R.id.lv_content);
        iv_back=getView(R.id.iv_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
