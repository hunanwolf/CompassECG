package com.compassecg.test720.compassecg.CommunityForum.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.compassecg.test720.compassecg.CommunityForum.fragment.CommunityAllFragmentH;
import com.compassecg.test720.compassecg.GroupConsultation.activity.BrowsePicActivityH;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.View.NetworkImageAdapter;
import com.compassecg.test720.compassecg.View.NineGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 地址adapter
 * Created by Administrator on 2016/8/3 0003.
 */
public class CommunityAdapterH extends BaseAdapter {
    private Context context;
    List<String> list=new ArrayList<String>();
    public CommunityAdapterH(Context context) {
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
            convertView=View.inflate(context,R.layout.item_for_community,null);

            holder.imgIv = (ImageView) convertView.findViewById(R.id.iv1);
            holder.nameTv = (TextView) convertView.findViewById(R.id.tv1);
            holder.contentTv = (TextView) convertView.findViewById(R.id.tv2);
            holder.timeTv = (TextView) convertView.findViewById(R.id.tv3);
            holder.numTv = (TextView) convertView.findViewById(R.id.tv4);
            holder.content2Tv = (TextView) convertView.findViewById(R.id.tv5);
            holder.mNineGridView = (NineGridView) convertView.findViewById(R.id.mNineGridView);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        //GroupPicAdapter adapter = new GroupPicAdapter(context);
        list.clear();
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        NetworkImageAdapter adapter = new NetworkImageAdapter(context, list);
        holder.mNineGridView.setAdapter(adapter);
        holder.mNineGridView.setOnImageClickListener(new Nicgriadview(position));
         return convertView;

    }

    public class Nicgriadview implements NineGridView.OnImageClickListener {
        private int posn;

        public Nicgriadview(int postion) {
            this.posn = postion;
        }

        @Override
        public void onImageCilcked(int position, View view) {
            /*current_posion = position;
            setViewPager(list3.get(posn).getPicture());*/
            Intent intent=new Intent(context, BrowsePicActivityH.class);
            intent.putExtra("path", (Serializable) list);
            intent.putExtra("position",String.valueOf(position));
            context.startActivity(intent);
        }

    }

    class ViewHolder{

        public TextView nameTv,contentTv,content2Tv,timeTv,numTv;
        public ImageView imgIv;
        public NineGridView mNineGridView;
    }

}
