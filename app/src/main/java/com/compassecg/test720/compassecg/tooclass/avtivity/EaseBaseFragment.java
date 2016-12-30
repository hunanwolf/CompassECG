package com.compassecg.test720.compassecg.tooclass.avtivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.tooclass.widget.EaseTitleBar;
import com.easemob.util.EMLog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.test720.auxiliary.Utils.NetUtil;

import cz.msebera.android.httpclient.Header;

import static com.umeng.socialize.Config.dialog;


public abstract class EaseBaseFragment extends Fragment {
    private static final String TAG = EaseBaseFragment.class.getSimpleName();
    protected EaseTitleBar titleBar;
    protected InputMethodManager inputMethodManager;
    Activity mContext;
    public Dialog progressBar;
    View view;
    TextView dialogText;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        titleBar = (EaseTitleBar) getView().findViewById(R.id.title_bar);
        mContext = getActivity();
        initView();
        setUpView();
    }

    /**
     * 显示标题栏
     */
    public void showTitleBar() {
        if (titleBar != null) {
            titleBar.setVisibility(View.VISIBLE);
        } else {
            EMLog.e(TAG, "cant find titlebar");
        }
    }

    /**
     * 隐藏标题栏
     */
    public void hideTitleBar() {
        if (titleBar != null) {
            titleBar.setVisibility(View.GONE);
        } else {
            EMLog.e(TAG, "cant find titlebar");
        }
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 设置属性，监听等
     */
    protected abstract void setUpView();

    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    //Post请求
    public void Post(String url, RequestParams params, final int what) {

        asyncHttpClient.post(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("网络错误", "fragment 信息获取失败");
//              Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();

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
    public void Getl(String url, RequestParams params, final int what) {
        if (!NetUtil.isNetworkConnected(mContext)) {
            Toast.makeText(getContext(), "网络断开！", Toast.LENGTH_SHORT).show();
            return;
        }
        asyncHttpClient.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("网络错误", "fragment 信息获取失败");
                Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("===baseFragment请求数据lll", responseString);
                JSONObject jsonObject = JSON.parseObject(responseString);
                Getsuccess(jsonObject, what);

            }
        });
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


    public void showLoading() {

        ProgressDialogView();
    }

    public void cleanLoading() {
        dialog.dismiss();

    }


    public void ProgressDialogView() {
        // mDismissListener = null;

        view = LayoutInflater.from(getContext()).inflate(
                com.test720.auxiliary.R.layout.progress_bar_style, null);
        dialogText = (TextView) view.findViewById(com.test720.auxiliary.R.id.progress_dialog_text);

        progressBar = new Dialog(getContext(), com.test720.auxiliary.R.style.messagebox_style);
        progressBar.setContentView(view);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

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
