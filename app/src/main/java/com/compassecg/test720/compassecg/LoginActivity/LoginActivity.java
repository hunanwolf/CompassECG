package com.compassecg.test720.compassecg.LoginActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.compassecg.test720.compassecg.APP;
import com.compassecg.test720.compassecg.DemoHelper;
import com.compassecg.test720.compassecg.MainActivity;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.tooclass.utils.EaseCommonUtils;
import com.compassecg.test720.compassecg.unitl.Connector;
import com.compassecg.test720.compassecg.unitl.UuidUtil;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroupManager;
import com.loopj.android.http.RequestParams;
import com.test720.auxiliary.Utils.ActivityUtil;
import com.test720.auxiliary.Utils.L;
import com.test720.auxiliary.Utils.NoBarBaseActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends NoBarBaseActivity {
    int SATAT = 1;
    int SATATKL = 2;
    private UMShareAPI mShareAPI = null;
    RelativeLayout back;

    TextView register;

    EditText pass;

    ImageView clear;

    EditText phone;
    List<String> listb = new ArrayList<String>();
    Button ok;
    TextView passLost;
    private boolean progressShow;
    private Intent intent;
    String phonetext;
    private boolean autoLogin = false;
    String getPhonetext;
    String password;
    public static List<String> usernames = new ArrayList<>();//好友列表
    public static List<EMConversation> listhuihua = new ArrayList<>();
    private String currentPassword, currentUsername;
    LinearLayout QQ, WX;
    SHARE_MEDIA platform = null;
    int typel;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // 如果登录成功过，直接进入主页面
//        if (DemoHelper.getInstance().isLoggedIn()) {
//            autoLogin = true;
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            return;
//        }
        setContentView(R.layout.activity_login);
        mShareAPI = UMShareAPI.get(this);
//        if (!APP.uuid.isEmpty()) {
//            finish();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.umeng_socialize_fade_in, R.anim.umeng_socialize_fade_out);
//        }

        initsetView();

        initView();
    }

    /**
     * 登录
     *
     */
    public void login() {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        currentUsername = phone.getText().toString().trim();
        currentPassword = "123";

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();

        final long start = System.currentTimeMillis();
        // 调用sdk登陆方法登陆聊天服务器
        EMChatManager.getInstance().login(currentUsername, "123", new EMCallBack() {

            @Override
            public void onSuccess() {
                if (!progressShow) {
                    return;
                }
                // 登陆成功，保存用户名
                DemoHelper.getInstance().setCurrentUserName(currentUsername);
                // 注册群组和联系人监听
                DemoHelper.getInstance().registerGroupAndContactListener();

                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
//                EMGroupManager.getInstance().loadAllGroups();
                EMChatManager.getInstance().loadAllConversations();

                //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        getDatae();


                    ActivityUtil.finishAllActivity();
                    finish();
                    // 进入主页面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
//                    }
//                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void initsetView() {

        QQ = getView(R.id.QQ);
        QQ.setOnClickListener(this);
        WX = getView(R.id.WX);
        WX.setOnClickListener(this);
        QQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typel = 1;
                L.e("longgin", "ajfanf");
                platform = SHARE_MEDIA.QQ;
//                Intent intent = new Intent(LoginActivity.this, AuthActivity.class);
//                startActivity(intent);

                mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
            }
        });
        WX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typel = 2;
                platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
            }
        });
        back = getView(R.id.back);
        register = getView(R.id.register);
        pass = getView(R.id.pass);
        clear = getView(R.id.clear);
        phone = getView(R.id.phone);
        ok = getView(R.id.ok);
        passLost = getView(R.id.pass_lost);

        back.setOnClickListener(this);
        register.setOnClickListener(this);
        ok.setOnClickListener(this);
        passLost.setOnClickListener(this);
        clear.setOnClickListener(this);

        // 如果用户名改变，清空密码
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass.setText("");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //自动设置手机账号
//        if (DemoHelper.getInstance().getCurrentUsernName() != null) {
//            phone.setText(DemoHelper.getInstance().getCurrentUsernName());
//        }
    }

    public void initView() {
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                phonetext = phone.getText().toString();
                if (!TextUtils.isEmpty(phone.getText())) {
                    clear.setVisibility(View.VISIBLE);
                    return;
                }

                if (phonetext.equals("") || phonetext == null) {

                    clear.setVisibility(View.GONE);
                    return;
                }

                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    phone.setText(sb.toString());
                    phone.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("^[1][0-9]{1}[0-9]{9}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            for (String key : data.keySet()) {
                Log.e("xxxxxx key = " + key + "    value= " + data.get(key));
            }

            if (data != null) {
                uid = data.get("openid");
//                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                RequestParams params = new RequestParams();
                params.put("uuid", data.get("openid"));
                params.put("type", typel);
                L.e(params.toString());
                Getl(Connector.bind, params, SATATKL);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "get fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                ActivityUtil.finishAllActivity();
                startActivity(new Intent(mContext, MainActivity.class));
//                System.exit(0);
                break;
            case R.id.register:
//                intent = new Intent(this, RegisterActivity.class);
//                startActivity(intent);
                break;
            case R.id.clear:
                phone.setText("");
                clear.setVisibility(View.INVISIBLE);
                break;
            case R.id.ok:
                if (!isMobile(phone.getText().toString().replace(" ", ""))) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass.getText())) {

                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    getDatae();

                }
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

                break;
            case R.id.pass_lost:
//                intent = new Intent(this, PassLostActivity.class);
//                startActivity(intent);
                overridePendingTransition(R.anim.umeng_socialize_slide_in_from_bottom, R.anim.umeng_socialize_slide_out_from_bottom);
                break;
        }
    }

    public void getDatae() {
        getPhonetext = phone.getText().toString();
        password = pass.getText().toString();
        RequestParams params = new RequestParams();
        params.put("username", getPhonetext);
        params.put("password", password);
        Post(Connector.logins, params, SATAT);
    }

    @Override
    public void Getsuccess(JSONObject jsonObject, int what) {

        switch (what) {
            case 1:
                int status = jsonObject.getIntValue("msg");
                if (status == 1) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

                    JSONObject Uid = jsonObject.getJSONObject("uid");
                    APP.uuid = Uid.getString("id");
                    phonetext = phone.getText().toString();
                    String username = phonetext.replaceAll("\\s", "");
                    APP.username = username;
                    APP.classificationid = Uid.getString("classificationid");

                    L.e("uuid", "" + jsonObject.getString("id"));
                    L.e("uuid", "" + APP.classificationid);
                    UuidUtil.saveLoginInfo(mContext);
                    login();
//                    finish();
//                    ActivityUtil.finishAllActivity();
//                    // 进入主页面
//                    Intent intent = new Intent(LoginActivity.this,
//                            MainActivity.class);
//                    startActivity(intent);

//                    finish();
                } else {
                    if (status == 0) {
                        Toast.makeText(this, "未注册！", Toast.LENGTH_LONG).show();
                    }
                    if (status == 3) {
                        Toast.makeText(this, "密码错误！", Toast.LENGTH_LONG).show();
                    }

                }
                break;

            case 2:

                if (jsonObject.getIntValue("code") == 0) {
//                    startActivity(new Intent(LoginActivity.this, BinDingActivity.class).putExtra("uid", uid).putExtra("type", typel));
                    ShowToast(jsonObject.getString("msg"));
                } else {
                    JSONObject object = jsonObject.getJSONObject("data");
                    APP.uuid = object.getString("id");
                    APP.username=object.getString("phone");
                    loginL(object.getString("phone"));
                    UuidUtil.saveLoginInfo(mContext);

                }


                break;
        }

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


    /**
     * 登录
     *
     * @param
     */
    public void loginL(final String currentUsername) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }

        currentPassword = "123";

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();

        final long start = System.currentTimeMillis();
        // 调用sdk登陆方法登陆聊天服务器
        EMChatManager.getInstance().login(currentUsername, "123", new EMCallBack() {

            @Override
            public void onSuccess() {
                if (!progressShow) {
                    return;
                }
                // 登陆成功，保存用户名
                DemoHelper.getInstance().setCurrentUserName(currentUsername);
                // 注册群组和联系人监听
                DemoHelper.getInstance().registerGroupAndContactListener();

                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                EMGroupManager.getInstance().loadAllGroups();
                EMChatManager.getInstance().loadAllConversations();

                //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                // 进入主页面
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
