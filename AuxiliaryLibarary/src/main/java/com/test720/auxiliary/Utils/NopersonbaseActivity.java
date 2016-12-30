package com.test720.auxiliary.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by anim on 2016/9/8.
 */

public class NopersonbaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Activity mContext;
   // private SweetAlertDialog pDialog;
    private ProgressDialog dialog;

    private String TAG = "NoBarBaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取当前显示activity的名字
        TAG = this.getLocalClassName();
        mContext = this;
        //把当前activity加入堆栈中
        ActivityUtil.getAppManager().addActivity(this);
        //实例化提示框
      //  pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        //禁用键盘已进入activity弹起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);//竖屏
        super.setContentView(layoutResID);

    }

    /**
     * 通用findViewById,减少重复的类型转换
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("FindViewById-----log", "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        ActivityUtil.getAppManager().finishActivity(this);
    }

    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    //Post请求
    public void Post(String url, RequestParams params, final int what) {
        if (!NetUtil.isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "网络断开！", Toast.LENGTH_SHORT).show();

            return;
        }
        asyncHttpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("网络错误", "fragment 信息获取失败");
                Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("===baseFragment请求数据", responseString);
                JSONObject jsonObject = JSON.parseObject(responseString);
                Getsuccess(jsonObject, what);

            }
        });
    }
    //Post请求
    public void Postl(String url, RequestParams params, final int what) {
        if (!NetUtil.isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "网络断开！", Toast.LENGTH_SHORT).show();

            return;
        }
        asyncHttpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("网络错误", "fragment 信息获取失败");
                Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("===baseFragment请求数据", responseString);
                JSONObject jsonObject = JSON.parseObject(responseString);
                Getsuccess(jsonObject, what);

            }
        });
    }
    //get请求
    public void Get(String url, RequestParams params, final int what) {
        if (!NetUtil.isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "网络断开！", Toast.LENGTH_SHORT).show();

            return;
        }
        asyncHttpClient.get(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("网络错误", "fragment 信息获取失败");
                Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("===baseFragment请求数据", responseString);
                JSONObject jsonObject = JSON.parseObject(responseString);
                Getsuccess(jsonObject, what);

            }
        });
    }

    /**
     * 用户调节系统的字体大小APP内部不变
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 请求URL有异常会调用该方法
     */
    public void GetFail() {

    }

    /**
     * 请求URL成功会调用该方法
     */
    public void Getsuccess(JSONObject jsonObject, int what) {

    }

//    public void ShowDialong(String text) {
//        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.blue_btn_bg_color));
//        pDialog.setTitleText(text);
//        pDialog.setCancelable(false);
//        pDialog.show();
//    }
//
//    public void DismissDialong() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }

    /**
     * Tost消息提醒
     */
    public void ShowToast(String text) {
        T.showLong(mContext, text);
    }


    @Override
    public void onClick(View v) {

    }

    //显示Loading  dialog

    public void showLoading() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Loading");
        }
        dialog.show();
    }

    public void cleanLoading() {
        dialog.dismiss();
    }

}