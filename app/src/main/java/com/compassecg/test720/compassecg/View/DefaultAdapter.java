package com.compassecg.test720.compassecg.View;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by xing on 11/13/15.
 */
public abstract class DefaultAdapter<T> implements NineGridView.NineGridAdapter{
    protected List<T> data ;
    protected Context context ;

    public DefaultAdapter(Context context, List<T> t) {
        this.context = context ;
        this.data = t ;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size() ;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    public ImageView generialDefaultImageView() {
        ImageView imageView = new ImageView(context) ;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams params = generialDefaultLayoutParams() ;
        imageView.setLayoutParams(params);

        return imageView ;
    }

    protected ViewGroup.LayoutParams generialDefaultLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT) ;
    }
}
