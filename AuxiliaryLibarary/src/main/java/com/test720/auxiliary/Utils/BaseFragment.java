package com.test720.auxiliary.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.test720.auxiliary.R;

import cz.msebera.android.httpclient.Header;


/**
 * Created by lenovo on 2015/11/2.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    Activity mContext;
    public View rootView = null;
    private String TAG = "BaseFragment";
    //    private SweetAlertDialog pDialog;
    public ProgressDialog dialog;
    Context mContextl;
    View view;
    TextView dialogText;
    public Dialog progressBar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TAG = getActivity().getLocalClassName();
        mContext = getActivity();
        //实例化提示框
        // pDialog =new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        dialog = new ProgressDialog(mContext);

        initViews();
    }

    public void initViews() {

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
            return (E) getView().findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("FindViewById-----log", "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }

    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    //Post请求
    public void Post(String url, RequestParams params, final int what) {
//        if (progressBar.isShowing()) {
         showLoading();
//        }

        if (!NetUtil.isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "网络断开！", Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }
        asyncHttpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.e("网络错误", "fragment 信息获取失败"+what);
//                Toast.makeText(getContext(), "网络超时，请重试！", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(mContext, "网络超时，请重试！", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(getContext(), "网络断开！", Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }
        asyncHttpClient.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("网络错误", "fragment 信息获取失败");
                Toast.makeText(getContext(), "网络超时，请重试！", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "网络断开！", Toast.LENGTH_SHORT).show();
            return;
        }
        asyncHttpClient.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("网络错误", "fragment 信息获取失败");
                Toast.makeText(getContext(), "网络超时，请重试！", Toast.LENGTH_SHORT).show();

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

    public void showLoading() {

        ProgressDialogView();
    }

    public void cleanLoading() {
        dialog.dismiss();

    }


    public void ProgressDialogView() {
        // mDismissListener = null;

        view = LayoutInflater.from(getContext()).inflate(
                R.layout.progress_bar_style, null);
        dialogText = (TextView) view.findViewById(R.id.progress_dialog_text);

        progressBar = new Dialog(getContext(), R.style.messagebox_style);
        progressBar.setContentView(view);
        progressBar.setCanceledOnTouchOutside(false);
        show();

    }

    public void show() {
//        if (progressBar.isShowing()) {
//
//        } else {
        progressBar.show();
//        }
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
