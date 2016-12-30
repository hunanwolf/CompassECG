package com.test720.auxiliary.Utils;

import android.content.Context;

import com.test720.auxiliary.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 弹出提示
 */
public class DialogUtil {
    public static SweetAlertDialog pDialog;
    private DialogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    //弹出一个加载框
    public static void ShowLoading(Context mContext)
    {
        pDialog =new  SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.blue_btn_bg_color));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    /**
    *  @param mContext  上下文本对象
    *  @param content 提示框提示内容
    *  @param onSweetClickListener 取消监听事件不用逻辑操作可直接传null
    *  @param onSweetClickListener1 确定监听事件不用逻辑操作可直接传null
    * */
    public static void ShowPromptBox(Context mContext,String content,SweetAlertDialog.OnSweetClickListener onSweetClickListener,SweetAlertDialog.OnSweetClickListener onSweetClickListener1)
    {
        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
        .setTitleText("提示")
        .setContentText(content)
        .setCancelText("取消")
        .setConfirmText("确定")
         .showCancelButton(true)
         .setCancelClickListener(onSweetClickListener)
        .setConfirmClickListener(onSweetClickListener1)
        .show();
    }
    /**
     * 弹出一个框提示一句话
     * */
    public static void ShowPrompt(Context mContext,String title,String content)
    {
        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .showCancelButton(true)
                .show();
    }



    public static void Dismiss()
    {
        if(pDialog!=null)
        {
            pDialog.dismiss();
        }
    }






}
