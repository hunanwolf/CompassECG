package com.test720.auxiliary.Utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


/**
 * Created by lenovo on 2015/11/2.
 */
public class PersonBarBaseActivity extends AppCompatActivity implements View.OnClickListener{
    public Activity mContext;
    private PersonToolBarHelper mToolBarHelper;
    public Toolbar toolbar;
    private String TAG = "BarBaseActivity";
  //  private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取当前显示activity的名字
        TAG = this.getLocalClassName();
        mContext = this;
        //把当前activity加入堆栈中
        ActivityUtil.getAppManager().addActivity(this);
        //实例化提示框
      //  pDialog =new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
    }
    @Override
    public void setContentView(int layoutResID) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        //禁止输入法以来就弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mToolBarHelper = new PersonToolBarHelper(this, layoutResID,mContext);
        toolbar = mToolBarHelper.getToolBar();
        toolbar.setTitle("");
        setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        //setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }
    //bar中间的标题名称
    public void setTitleString(String resid) {
        mToolBarHelper.setTitle(resid);
    }
    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }
    //    //结束当前请求
//    public void cancelRequests(boolean mayInterruptIfRunning) {
//        asyncHttpClient.cancelRequests(mContext, mayInterruptIfRunning);
//    }
//    //结束所有请求
//    public void cancelAllRequests(boolean mayInterruptIfRunning)
//    {
//        asyncHttpClient.cancelAllRequests(mayInterruptIfRunning);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    //Post请求 带1秒延迟+提示框的Post请求(这种类型的是避免网速过快提示框过快的消失)
    public void Post(final String url, final RequestParams params, final int what, final String content) {
        L.e(TAG+" 请求url"+url+"   请求参数如下:",params.toString());
        CountDownTimer countDownTimer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
//                ShowDialong(content.equals("")?"Loading":content);
                asyncHttpClient.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        DismissDialong();
                        ShowToast("网络错误");
                        L.e(TAG+"  请求异常",throwable+"");
                        GetFail();
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.e(TAG+"  请求数据",responseString);
//                        DismissDialong();
                        JSONObject jsonObject = JSON.parseObject(responseString);
                        Getsuccess(jsonObject, what);
                    }
                });
            }
        };
        countDownTimer.start();
    }
    //Post请求  不带延迟不带提示框
    public void Post(final String url, final RequestParams params, final int what) {
        L.e(TAG+" 请求url"+url+"   请求参数如下:",params.toString());
        asyncHttpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                DismissDialong();
                ShowToast("网络错误");
                L.e(TAG + "  请求异常", throwable + "");
                GetFail();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e(TAG + "  请求数据", responseString);
             //   DismissDialong();
                JSONObject jsonObject = JSON.parseObject(responseString);
                Getsuccess(jsonObject, what);
            }
        });
    }
    /**
     * 用户调节系统的字体大小APP内部不变
     * */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }
    /**
     * 请求URL有异常会调用该方法
     * */
    public void GetFail()
    {

    }
    /**
     * 请求URL成功会调用该方法
     * */
    public void Getsuccess(JSONObject jsonObject,int what)
    {

    }
//    public void ShowDialong(String text) {
//        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.blue_btn_bg_color));
//        pDialog.setTitleText(text);
//        pDialog.setCancelable(false);
//        pDialog.show();
//    }
//    public void DismissDialong() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
    /**
     * Tost消息提醒
     * */
    public void ShowToast(String text)
    {
        T.showLong(mContext, text);
    }


    @Override
    public void onClick(View v) {

    }
}
