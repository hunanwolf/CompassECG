package com.compassecg.test720.compassecg.tooclass.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.tooclass.controller.EaseUI;
import com.compassecg.test720.compassecg.tooclass.domain.EaseUser;
import com.compassecg.test720.compassecg.unitl.Connector;


public class EaseUserUtils {
    
    static EaseUI.EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * 根据username获取相应user
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	EaseUser user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
            try {
                String jpg = Connector.hedaer +username;
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(jpg).into(imageView);
            } catch (Exception e) {
                //正常的string路径
                String jpg = Connector.hedaer +username;
                Glide.with(context).load(jpg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.touxiang).into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.touxiang).into(imageView);
        }
    }
    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
        if(textView != null){
        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNick() != null){
        		textView.setText(user.getNick());
        	}else{
        		textView.setText(username);
        	}
        }
    }
    
}
