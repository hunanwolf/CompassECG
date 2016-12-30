package com.compassecg.test720.compassecg.unitl;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.compassecg.test720.compassecg.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.test720.auxiliary.Utils.NetUtil;
import com.test720.auxiliary.Utils.T;
import com.test720.auxiliary.Utils.ToolBarHelper;

import cz.msebera.android.httpclient.Header;


/**
 * Created by lenovo on 2015/11/l2.
 */
public abstract class BarBaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Activity mContext;
    private ToolBarHelper mToolBarHelper;
    public Toolbar toolbar;
    private String TAG = "BarBaseActivity";
    //    private SweetAlertDialog pDialog;
    public ProgressDialog dialog;


    Context mContextl;
    View view;
    TextView dialogText;
    public Dialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取当前显示activity的名字
        TAG = this.getLocalClassName();
        mContext = this;
        //把当前activity加入堆栈中
        com.test720.auxiliary.Utils.ActivityUtil.getAppManager().addActivity(this);
//        //实例化提示框
//        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
    }

    @Override
    public void setContentView(int layoutResID) {
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);//竖屏
        mToolBarHelper = new ToolBarHelper(this, layoutResID, mContext);
        toolbar = mToolBarHelper.getToolBar();
        toolbar.setTitle("");
        setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
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
        com.test720.auxiliary.Utils.ActivityUtil.getAppManager().finishActivity(this);
    }

    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    //Post请求
    public void Post(String url, RequestParams params, final int what) {
        showLoading();
        if (!NetUtil.isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "网络断开！", Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }
        asyncHttpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("网络错误", "fragment 信息获取失败");
                Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("===baseFragment请求数据", responseString);
                JSONObject jsonObject = JSON.parseObject(responseString);
                Getsuccess(jsonObject, what);
                dismiss();
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
        showLoading();
        if (!NetUtil.isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "网络断开！", Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }
        asyncHttpClient.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("网络错误", "fragment 信息获取失败");
                Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("===baseFragment请求数据", responseString);
                JSONObject jsonObject = JSON.parseObject(responseString);
                Getsuccess(jsonObject, what);
                dismiss();
            }
        });
    }

    //get请求
    public void Getl(String url, RequestParams params, final int what) {
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


    public void showLoading() {

        ProgressDialogView();
//        if (dialog == null){
//            dialog = new ProgressDialog(this);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setMessage("Loading");
//        }
//        dialog.show();
    }

    public void cleanLoading() {
        dialog.dismiss();
    }


    public void ProgressDialogView() {
        // mDismissListener = null;

        view = LayoutInflater.from(mContext).inflate(
                R.layout.progress_bar_style, null);
        dialogText = (TextView) view.findViewById(R.id.progress_dialog_text);

        progressBar = new Dialog(mContext, R.style.messagebox_style);
        progressBar.setContentView(view);
        progressBar.setCanceledOnTouchOutside(false);
        show();
    }


    /*
     * public ProgressDialogView setOnDissmissListener(DismissListener
     * listener){ mDismissListener = listener; return this; } public interface
     * DismissListener { void onDismiss(DialogInterface dialog); }
     */

    public void show() {
        if (progressBar.isShowing()) {

        } else {
            progressBar.show();
        }
    }

    public boolean isShowing() {
        return progressBar.isShowing();

    }

    public void dismiss() {
//        if (progressBar.isShowing()) {
        progressBar.dismiss();
//        }
    }

}
