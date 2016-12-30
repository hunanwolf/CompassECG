package com.compassecg.test720.compassecg.GroupConsultation.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.compassecg.test720.compassecg.GroupConsultation.adapter.GroupKindsAnswerAdapterH;
import com.compassecg.test720.compassecg.GroupConsultation.adapter.GroupKindsAnswerFinalAdapterH;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.View.KeyboardListenRelativeLayout;
import com.compassecg.test720.compassecg.View.MyListView;
import com.compassecg.test720.compassecg.View.NetworkImageAdapter;
import com.compassecg.test720.compassecg.View.NineGridView;
import com.orhanobut.logger.Logger;
import com.test720.auxiliary.Utils.NoBarBaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnswerDetailsActivityH extends NoBarBaseActivity {

    private NineGridView mNineGridView;
    private MyListView listView;
    List<String> list=new ArrayList<String>();
    private ImageView iv_back;
    private RelativeLayout rl_left;
    private RelativeLayout rl_right;
    private KeyboardListenRelativeLayout root;
    private EditText et_content;
    private RelativeLayout rl_message;
    private LinearLayout ll_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_details_activity_h);
        initView();
        setAdapter();
        setListenner();
    }

    private void setListenner() {
        iv_back.setOnClickListener(this);
        rl_left.setOnClickListener(this);
        rl_right.setOnClickListener(this);
        root.setOnKeyboardStateChangedListener(new KeyboardListenRelativeLayout.IOnKeyboardStateChangedListener() {
            @Override
            public void onKeyboardStateChanged(int state) {
                switch (state) {
                    case KeyboardListenRelativeLayout.KEYBOARD_STATE_HIDE://软键盘隐藏
                        Log.i("WOLF","软键盘隐藏");
                        Logger.d("软键盘隐藏");
                        controlKeyboardLayout(root);

                        rl_message.setVisibility(View.GONE);
                        ll_bottom.setVisibility(View.VISIBLE);
                        break;
                    case KeyboardListenRelativeLayout.KEYBOARD_STATE_SHOW://软键盘显示
                        Log.i("WOLF","软键盘显示");
                        Logger.d("软键盘显示");

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setAdapter() {
        list.clear();
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        list.add("http://i.dimg.cc/8f/3c/9f/39/8e/48/0b/b4/ff/0d/a8/8a/62/22/f3/6a.jpg");
        NetworkImageAdapter adapter = new NetworkImageAdapter(this, list);
        mNineGridView.setAdapter(adapter);
        mNineGridView.setOnImageClickListener(new Nicgriadview());

        /*GroupKindsAnswerAdapterH adapter_answer=new GroupKindsAnswerAdapterH(this);
        listView.setAdapter(adapter_answer);
        listView.setFocusable(false);*/

        GroupKindsAnswerFinalAdapterH adapter_answer=new GroupKindsAnswerFinalAdapterH(this);
        listView.setAdapter(adapter_answer);
        listView.setFocusable(false);
    }

    private void initView() {
        mNineGridView=getView(R.id.mNineGridView);
        listView=getView(R.id.listView);
        iv_back=getView(R.id.iv_back);
        rl_left=getView(R.id.rl_left);
        rl_right=getView(R.id.rl_right);
        root=getView(R.id.root);
        et_content=getView(R.id.et_content);
        rl_message=getView(R.id.rl_message);
        ll_bottom=getView(R.id.ll_bottom);
    }
    public class Nicgriadview implements NineGridView.OnImageClickListener {

        public Nicgriadview() {

        }

        @Override
        public void onImageCilcked(int position, View view) {
            /*current_posion = position;
            setViewPager(list3.get(posn).getPicture());*/
            Intent intent=new Intent(AnswerDetailsActivityH.this, BrowsePicActivityH.class);
            intent.putExtra("path", (Serializable) list);
            intent.putExtra("position",String.valueOf(position));
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_left:
                break;
            case R.id.rl_right:
                popInput();
                rl_message.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.GONE);
                break;
        }
    }

    /**
            *
            *            最外层布局，需要调整的布局
    *            被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
    /**/
    private void controlKeyboardLayout(final View root) {
        // 注册一个回调函数，当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时调用这个回调函数。
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        //获取当前界面可视部分
                        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                        //获取屏幕的高度
                        int screenHeight =  getWindow().getDecorView().getRootView().getHeight();
                        //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                        int heightDifference = screenHeight - r.bottom;
                        Log.e("Keyboard Size", "Size: " + heightDifference);

                    }
                });
    }
    /**
     * 获取焦点并弹出输入法
     */
    private void popInput() {
        et_content.setFocusable(true);
        et_content.setFocusableInTouchMode(true);
        et_content.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager)et_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et_content, 0);
    }
}
