package com.compassecg.test720.compassecg.GroupConsultation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.compassecg.test720.compassecg.GroupConsultation.adapter.GroupKindsAdapterH;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.View.MyListView;
import com.test720.auxiliary.Utils.NoBarBaseActivity;

public class GroupActivityH extends NoBarBaseActivity {

    private MyListView listView;
    private GroupKindsAdapterH adapter;
    private ImageView iv_back;
    private ImageView iv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        initView();
        setAdapter();
        setListenner();
    }

    private void setListenner() {
        iv_back.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(GroupActivityH.this,ConsultationDetailsActivityH.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        adapter=new GroupKindsAdapterH(this);
        listView.setAdapter(adapter);
        listView.setFocusable(false);
    }

    private void initView() {
        listView=getView(R.id.listView);
        iv_back=getView(R.id.iv_back);
        iv_add=getView(R.id.iv_add);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
            finish();
            break;
            case R.id.iv_add:
            Intent intent=new Intent(GroupActivityH.this,AddConsultationActivityH.class);
                startActivity(intent);
            break;
        }
    }
}
