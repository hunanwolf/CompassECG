package com.compassecg.test720.compassecg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.compassecg.test720.compassecg.CommunityForum.CommunityForumFragmentH;
import com.compassecg.test720.compassecg.CommunityForum.activity.AddAnswer2ActivityH;
import com.compassecg.test720.compassecg.GroupConsultation.GroupConsultationFragmentH;
import com.compassecg.test720.compassecg.GroupConsultation.activity.AddConsultationActivityH;
import com.compassecg.test720.compassecg.Home.MainFragment;
import com.compassecg.test720.compassecg.LoginActivity.LoginActivity;
import com.compassecg.test720.compassecg.Navigation.NavigationActivityHw;
import com.compassecg.test720.compassecg.unitl.UuidUtil;
import com.test720.auxiliary.Utils.L;
import com.test720.auxiliary.Utils.NoBarBaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends NoBarBaseActivity {
    FragmentManager manager;
    Fragment fragmentShow = null;
    public List<Fragment> fragmentList;
    public ViewGroup tabHost;
    public static MainActivity test_a = null;
    private AlertDialog logoutDialog;
    private AlertDialog picDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test_a=this;
        initView();
    }


    //初始化控件
    public void initView() {

        tabHost = (ViewGroup) findViewById(R.id.main_tabhost);
        for (int i = 0; i < tabHost.getChildCount(); i++) {
            tabHost.getChildAt(i).setOnClickListener(new TabHostClickListener(i));
        }
        fragmentList = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new GroupConsultationFragmentH());
        fragmentList.add(new Fragment());
//        conversationListFragment = new ConversationListFragment();
//        EaseConversationListFragment = new EaseConversationListFragment();
        fragmentList.add(new CommunityForumFragmentH());
        fragmentList.add(new Fragment());
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.content_frame, fragmentList.get(0)).commit();
        fragmentShow = fragmentList.get(0);
        tabHost.getChildAt(0).setSelected(true);
    }


    /**
     * tabhost选中切换
     */
    class TabHostClickListener implements View.OnClickListener {
        int indexl;

        TabHostClickListener(int index) {
            this.indexl = index;
        }


        @Override

        public void onClick(View v) {//tabhost选中切换
            tabHost.getChildAt(0).setSelected(indexl == 0);
            tabHost.getChildAt(1).setSelected(indexl == 1);
            tabHost.getChildAt(2).setSelected(indexl == 2);
            tabHost.getChildAt(3).setSelected(indexl == 3);
            tabHost.getChildAt(4).setSelected(indexl == 4);


            //如果是发布
            if (indexl == 2) {
               /* if (UuidUtil.checkLoginState()) {
//                    startActivity(new Intent(mContext, NavigationActivity.class));
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                } else
                    LoginDialog();*/
                showfabuDialog();
                //startActivity(new Intent(mContext, NavigationActivityHw.class));
                //从下往上展示
                //overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                return;
            }
            if (indexl == 1) {
                /*if (!UuidUtil.checkLoginState()) {
                    LoginDialog();
                    return;
                } else {
                    Long tsLong = System.currentTimeMillis() / 1000;
                    int ts = Integer.parseInt(tsLong.toString());
                    //consultation  咨询
                    L.e("consultation1", APP.consultation1 + "");
                    if (ts >= APP.consultation1) {
//                        LoginDialog2();
                        return;
                    }

                }*/
            }
            if (indexl == 3) {

                /*if (!UuidUtil.checkLoginState()) {
                    LoginDialog();
                    return;
                } else {
                    Long tsLong = System.currentTimeMillis() / 1000;
                    int ts = Integer.parseInt(tsLong.toString());
                    //chat 畅聊
                    L.e("cha1", APP.chat1 + "");
                    if (ts >= APP.chat1) {
                     //   LoginDialog2();
                        return;
                    }
                }*/
            }
            switchContent(fragmentShow, fragmentList.get(indexl));
        }
    }


    /**
     * 判断是否被add过
     * add过  隐藏当前的fragment，add下一个到Activity中
     * 否则   隐藏当前的fragment，显示下一个
     */
    public void switchContent(Fragment from, Fragment to) {
        if (fragmentShow != to) {
            fragmentShow = to;
            FragmentTransaction transaction = manager.beginTransaction();
//            // 标准动画
//            transaction
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//             transaction
//             .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

//             transaction
//             .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            L.e(to.isAdded() + "");
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
//                transaction.hide(from).add(R.id.content_frame, to).commitAllowingStateLoss();
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
//                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }

    public void switchPage(final int i) {

        if (!fragmentShow.isAdded()) {
            FragmentTransaction transaction = manager.beginTransaction();
            // 标准动画
            transaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.hide(fragmentShow).add(R.id.content_frame, fragmentList.get(i)).commit();
        } else {
            switchContent(fragmentShow, fragmentList.get(i));
        }
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                L.e("tabHost",i+"");
                tabHost.getChildAt(i).setSelected(true);
                tabHost.getChildAt(0).setSelected(false);
            }
        });
    }
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();        //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    //发布dialog
    public void showfabuDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        picDialog = builder.create();
        picDialog.show();
        // 获取dialog的窗口
        Window window = picDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(android.R.color.white);
        View v = View.inflate(this, R.layout.dialog_fabu, null);
        // 获取v对象中的控件 .setOnClickListener
        picDialog.setCanceledOnTouchOutside(true);
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lParams = window.getAttributes();
        lParams.width = (int) (display.getWidth());
        window.setAttributes(lParams);

        ImageView iv_delete = (ImageView)v.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picDialog.dismiss();
            }
        });
        v.findViewById(R.id.ll_huizhen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddConsultationActivityH.class);
                startActivity(intent);
                picDialog.dismiss();
            }
        });
        v.findViewById(R.id.ll_luntan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddAnswer2ActivityH.class);
                startActivity(intent);
                picDialog.dismiss();
            }
        });
        //旋转动画
        AnimationSet animationSet = new AnimationSet(true);
        //参数1：从哪个旋转角度开始
        //参数2：转到什么角度
        //后4个参数用于设置围绕着旋转的圆的圆心在哪里
        //参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
        //参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        //参数5：确定y轴坐标的类型
        //参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        RotateAnimation rotateAnimation = new RotateAnimation(0, 90,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(500);
        animationSet.addAnimation(rotateAnimation);
        iv_delete.startAnimation(animationSet);
        window.setContentView(v);
    }


    //确认dengludialog
    private void LoginDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        logoutDialog = builder.create();
        logoutDialog.show();
        // 获取dialog的窗口
        Window window = logoutDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View v = View.inflate(this, R.layout.dialog_login, null);
        // 获取v对象中的控件 .setOnClickListener
        TextView logoutcancel = (TextView) v.findViewById(R.id.upload_from_local);
        TextView logoutconfirm = (TextView) v.findViewById(R.id.pic_cancel);

        logoutDialog.setCanceledOnTouchOutside(true);
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lParams = window.getAttributes();

        lParams.width = (int) (display.getWidth() * 2.7 / 3.1);
        window.setAttributes(lParams);
        //按钮的监听
        logoutcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.dismiss();
            }
        });
        logoutconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                logoutDialog.dismiss();
            }
        });
        window.setContentView(v);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }
}
