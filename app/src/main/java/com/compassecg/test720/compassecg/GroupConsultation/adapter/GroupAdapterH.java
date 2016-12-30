package com.compassecg.test720.compassecg.GroupConsultation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.compassecg.test720.compassecg.R;

/**
 * 地址adapter
 * Created by Administrator on 2016/8/3 0003.
 */
public class GroupAdapterH extends BaseAdapter {
    private Context context;

    public GroupAdapterH(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context,R.layout.item_for_group,null);

            holder.addressTv = (TextView) convertView.findViewById(R.id.tv1);
            holder.numTv = (TextView) convertView.findViewById(R.id.tv2);
            holder.imgIv = (ImageView) convertView.findViewById(R.id.img2);


            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

         return convertView;

    }

    class ViewHolder{

        public TextView addressTv,numTv;
        public ImageView imgIv;
    }
}
