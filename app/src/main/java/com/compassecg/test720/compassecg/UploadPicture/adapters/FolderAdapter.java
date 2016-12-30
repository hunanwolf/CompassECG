package com.compassecg.test720.compassecg.UploadPicture.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.unitl.LocalImageHelper;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by admin on 2016/5/12.
 */
public class FolderAdapter extends BaseAdapter {
    Map<String, List<LocalImageHelper.LocalFile>> folders;
    Context context;
    ArrayList<String> folderNames;

    public FolderAdapter(Map<String, List<LocalImageHelper.LocalFile>> folders, Context context, ArrayList<String> folderNames) {
        this.folders = folders;
        this.context = context;
        this.folderNames = folderNames;
    }

    @Override
    public int getCount() {
        return folders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_albumfoler, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String name = folderNames.get(i);
        List<LocalImageHelper.LocalFile> files = folders.get(name);
        viewHolder.textView.setText(name + "(" + files.size() + ")");
        if (files.size() > 0) {
            Glide.with(context).load(files.get(0).getThumbnailUri()).into(viewHolder.imageView);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
