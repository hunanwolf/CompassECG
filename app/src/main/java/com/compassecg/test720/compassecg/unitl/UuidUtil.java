package com.compassecg.test720.compassecg.unitl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.compassecg.test720.compassecg.APP;
import com.compassecg.test720.compassecg.bean.UserList;
import com.test720.auxiliary.Utils.T;

import java.util.ArrayList;
import java.util.List;

import static com.compassecg.test720.compassecg.APP.headerUrl;


/**
 * Created by admin on 2016/l3/30.
 */
public class UuidUtil {


    private Context mContext;

    /**
     * 保存账号密码
     */
    public static void saveLoginInfo(Context context) {
        // 获取SharedPreferences对象
        SharedPreferences sharedPre = context.getSharedPreferences("config",
                context.MODE_PRIVATE);
        // 获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        // 设置参数
        editor.putString("username", APP.username);
        editor.putString("password", APP.password);
        editor.putString("uuid", APP.uuid);
        editor.putString("header", APP.header);
        editor.putString("header", APP.headerUrl);
//        editor.putString("name", MyApplication.name);
//       editor.putString("header", MyApplication.header);
//        editor.putString("photol", MyApplication.photol);
        Log.e("=======","p"+ APP.header);
        // 提交
        editor.commit();
    }

    public static void AddList(Context context, String name, String id) {
        // 获取SharedPreferences对象
        SharedPreferences sharedPre = context.getSharedPreferences("configList",
                context.MODE_PRIVATE);
        List<UserList> users = GetList(context);
        if (users.size() > 0) {
            int c = 0;
            for (int i = 0; i < users.size(); i++) {
                if (name.equals(users.get(i).getName())) {
                    c++;
                    break;
                }
            }
            if (c == 0) {
                UserList user = new UserList();
                user.setName(name);
                user.setId(id);
                users.add(user);
                // 获取Editor对象
                SharedPreferences.Editor editor = sharedPre.edit();
                // 设置参数
                editor.putString("list", JSON.toJSONString(users));
                // 提交
                editor.commit();
            } else {
                T.showShort(context, "已经存在选项");
            }
        } else {
            UserList user = new UserList();
            user.setName(name);
            user.setId(id);
            users.add(user);
            // 获取Editor对象
            SharedPreferences.Editor editor = sharedPre.edit();
            // 设置参数
            editor.putString("list", JSON.toJSONString(users));
            // 提交
            editor.commit();
        }


    }

    public static List<UserList> GetList(Context context) {
        // 获取SharedPreferences对象
        SharedPreferences sharedPre = context.getSharedPreferences("configList",
                context.MODE_PRIVATE);
        List<UserList> userLists = new ArrayList<>();
        if (!"".equals(sharedPre.getString("list", ""))) {
            JSONArray objects = JSON.parseArray(sharedPre.getString("list", ""));
            userLists.addAll(JSONArray.parseArray(objects.toJSONString(), UserList.class));
        }

        return userLists;
    }

    public static void SetList(Context context, String name) {
        List<UserList> userLists = GetList(context);
        for (int i = 0; i < userLists.size(); i++) {
            if (userLists.get(i).getName().equals(name)) {
                userLists.remove(i);
                break;
            }
        }
        // 获取SharedPreferences对象
        SharedPreferences sharedPre = context.getSharedPreferences("configList",
                context.MODE_PRIVATE);
        // 获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        // 设置参数
        editor.putString("list", JSON.toJSONString(userLists));
        // 提交
        editor.commit();
    }



    /**
     * 得到账号密码
     */
    public static void getLoginInfo(Context context) {
        // 获取SharedPreferences对象
        SharedPreferences sharedPre = context.getSharedPreferences("config",
                context.MODE_PRIVATE);
        APP.username = sharedPre.getString("username", "");

        APP.password = sharedPre.getString("password", "");
        APP.uuid = sharedPre.getString("uuid", "");
        APP.header = sharedPre.getString("header", "");
      APP.  headerUrl = sharedPre.getString("headerUrl", "");
//        MyApplication.header=sharedPre.getString("header", "");
//        MyApplication.photol=sharedPre.getString("photol", "");
    }

    public static boolean checkLoginState() {
        if (!APP.uuid.equals("")) {
            return true;
        }

        return false;

    }

    public static String getUuid() {
        return APP.uuid;

    }

    public static void logout(Context context) {
        APP.username = "";
        APP.password = "";
        APP.uuid = "";
        APP.header = "";
           APP.     headerUrl="";
//        MyApplication.header="";
//        MyApplication.photol="";

        SharedPreferences sharedPre = context.getSharedPreferences("config",
                context.MODE_PRIVATE);
        // 获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        // 设置参数

        editor.putString("username", "");
        editor.putString("password", "");
        editor.putString("uuid", "");
        editor.putString("name", "");
        editor.putString("header", "");
        editor.putString("photol", "");
        editor.putString("headerUrl", "");
        // 提交
        editor.commit();

    }


}
