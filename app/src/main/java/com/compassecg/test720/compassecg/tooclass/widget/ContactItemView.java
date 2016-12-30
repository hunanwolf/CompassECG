package com.compassecg.test720.compassecg.tooclass.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compassecg.test720.compassecg.R;


public class ContactItemView extends LinearLayout {

    private TextView unreadMsgView;
    ImageView avatar;

    public ContactItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ContactItemView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ContactItemView);
        String name = ta.getString(R.styleable.ContactItemView_contactItemName);
        Drawable image = ta.getDrawable(R.styleable.ContactItemView_contactItemImage);
        ta.recycle();

        LayoutInflater.from(context).inflate(R.layout.em_widget_contact_item, this);
        avatar = (ImageView) findViewById(R.id.avatar);
        unreadMsgView = (TextView) findViewById(R.id.unread_msg_number);
        TextView nameView = (TextView) findViewById(R.id.name);
        if (image != null) {
            avatar.setImageDrawable(image);
        }
        nameView.setText(name);
    }

    public void setUnreadCount(int unreadCount) {
        unreadMsgView.setText(String.valueOf(unreadCount));
    }
    public void setlieftimg(){
        avatar.setImageDrawable(getResources().getDrawable(R.drawable.em_new_friends_icon));
    }
    public void setnewfimg(){
        avatar.setImageDrawable(getResources().getDrawable(R.drawable.em_new_friends));
//        avatar.setBackgroundDrawable(getResources().getDrawable(R.drawable.em_new_friends));
    }
    public void showUnreadMsgView() {
        unreadMsgView.setVisibility(View.VISIBLE);
    }

    public void hideUnreadMsgView() {
        unreadMsgView.setVisibility(View.INVISIBLE);
    }

}
