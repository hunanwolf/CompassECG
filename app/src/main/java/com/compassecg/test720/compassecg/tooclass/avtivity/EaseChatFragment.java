package com.compassecg.test720.compassecg.tooclass.avtivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.compassecg.test720.compassecg.APP;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.tooclass.EaseConstant;
import com.compassecg.test720.compassecg.tooclass.InviteMessgeDao;
import com.compassecg.test720.compassecg.tooclass.controller.EaseUI;
import com.compassecg.test720.compassecg.tooclass.domain.EaseEmojicon;
import com.compassecg.test720.compassecg.tooclass.domain.friend;
import com.compassecg.test720.compassecg.tooclass.utils.EaseCommonUtils;
import com.compassecg.test720.compassecg.tooclass.utils.EaseImageUtils;
import com.compassecg.test720.compassecg.tooclass.utils.EaseUserUtils;
import com.compassecg.test720.compassecg.tooclass.widget.EaseAlertDialog;
import com.compassecg.test720.compassecg.tooclass.widget.EaseChatExtendMenu;
import com.compassecg.test720.compassecg.tooclass.widget.EaseChatInputMenu;
import com.compassecg.test720.compassecg.tooclass.widget.EaseChatMessageList;
import com.compassecg.test720.compassecg.tooclass.widget.EaseTitleBar;
import com.compassecg.test720.compassecg.tooclass.widget.EaseVoiceRecorderView;
import com.compassecg.test720.compassecg.tooclass.widget.chatrow.EaseCustomChatRowProvider;
import com.compassecg.test720.compassecg.unitl.Connector;
import com.easemob.EMChatRoomChangeListener;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.EMValueCallBack;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatRoom;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.TextMessageBody;
import com.easemob.exceptions.EaseMobException;
import com.easemob.util.EMLog;
import com.easemob.util.PathUtil;
import com.loopj.android.http.RequestParams;
import com.test720.auxiliary.Utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 可以直接new出来使用的聊天对话页面fragment，
 * 使用时需调用setArguments方法传入chatType(会话类型)和userId(用户或群id)
 * app也可继承此fragment续写
 * <br/>
 * <br/>
 * 参数传入示例可查看demo里的ChatActivity
 */
@SuppressLint("ResourceAsColor")
public class EaseChatFragment extends EaseBaseFragment implements EMEventListener {
    protected static final String TAG = "EaseChatFragment";
    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;

    // 是否处于阅后即焚状态的标志，true为阅后即焚状态：此状态下发送的消息都是阅后即焚的消息，暂时实现了文字和图片，false表示正常状态
    public boolean isReadFire = false;
    /**
     * 传入fragment的参数
     */
    protected Bundle fragmentArgs;
    protected int chatType;
    protected String toChatUsername;
    protected String type = "";
    protected String id;
    protected EaseChatMessageList messageList;
    protected EaseChatInputMenu inputMenu;
    private InviteMessgeDao inviteMessgeDao;
    protected EMConversation conversation;
    private ProgressDialog progressDialog;
    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;

    protected Handler handler = new Handler();
    protected File cameraFile;
    protected EaseVoiceRecorderView voiceRecorderView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;
    PopupWindow PopupWindow;//
    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected int pagesize = 20;
    //    protected GroupListener groupListener;
    protected EMMessage contextMenuMessage;

    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;
    List<friend> list1 = new ArrayList<>();
    protected int[] itemStrings = {R.string.attach_take_pic, R.string.attach_picture};
    protected int[] itemdrawables = {R.drawable.ease_chat_takepic_selector, R.drawable.ease_chat_image_selector
    };
    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE};
    private EMChatRoomChangeListener chatRoomChangeListener;
    private boolean isMessageListInited;
    protected MyItemClickListener extendMenuItemClickListener;
    private int SATAR = 1;
    private int ALGNJOA = 2;
    private TextView titl;
    private TextView address;
    String telephone;
    LinearLayout titlebar;
    ImageView phonel;
    private List<String> usernames = new ArrayList<>();
    int haoyouindex;
    EaseTitleBar title_bar;
    private List<String> list2 = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    ImageView img;
    TextView title;
    RelativeLayout back;
    String tile;
    private int SATARl = 3;
    private List<String> blackList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ease_fragment_chat, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        titl = (TextView) getView().findViewById(R.id.titl);
        title = (TextView) getView().findViewById(R.id.title);
        address = (TextView) getView().findViewById(R.id.address);
        titlebar = (LinearLayout) getView().findViewById(R.id.titlebar);
        phonel = (ImageView) getView().findViewById(R.id.phone);
        title_bar = (EaseTitleBar) getView().findViewById(R.id.title_bar);
        img = (ImageView) getView().findViewById(R.id.img);
        back = (RelativeLayout) getView().findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        phonel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + telephone);
                intent.setData(data);
                startActivity(intent);
            }
        });


        fragmentArgs = getArguments();
        // 判断单聊还是群聊
        chatType = fragmentArgs.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        // 会话人或群组id
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);
        type = fragmentArgs.getString(EaseConstant.PROLMELE_TYPE);
        id = fragmentArgs.getString(EaseConstant.PROLMELE_ID);

        blackList = EMContactManager.getInstance().getBlackListUsernames();
        for (int i = 0; i < blackList.size(); i++) {
            if (toChatUsername.equals(blackList.get(i))) {
                isblack = 1;
                break;
            }
            L.e("isblack", isblack + "");
        }


        getDatae();
        getdatae();
        getDatael();

        super.onActivityCreated(savedInstanceState);
    }

    public void getDatael() {

        RequestParams params = new RequestParams();
        params.put("phone", APP.username);
        Post(Connector.friendList, params, SATARl);


    }

    /**
     * 刷新页面
     */

    public void getDatae() {

        list2.add(toChatUsername);
        L.e("list2", list2.toString());
        RequestParams params = new RequestParams();
        params.put("id", APP.uuid);
        params.put("mobile_json", JSONArray.toJSONString(list2));
        Post(Connector.returnNickname, params, ALGNJOA);

    }

    public void getdatae() {
        RequestParams params = new RequestParams();
        params.put("id", id);
        L.e("type", type + "id" + id);
        params.put("type", type);
        Post(Connector.getInfo, params, SATAR);
    }

    /**
     * init view
     */
    protected void initView() {
        // 按住说话录音控件
        voiceRecorderView = (EaseVoiceRecorderView) getView().findViewById(R.id.voice_recorder);

        // 消息列表layout
        messageList = (EaseChatMessageList) getView().findViewById(R.id.message_list);
        if (chatType != EaseConstant.CHATTYPE_SINGLE)
            messageList.setShowUserNick(true);
        listView = messageList.getListView();

        extendMenuItemClickListener = new MyItemClickListener();
        inputMenu = (EaseChatInputMenu) getView().findViewById(R.id.input_menu);
        registerExtendMenuItem();
        // init input menu
        inputMenu.init(null);
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onSendMessage(String content) {
                // 发送文本消息
                sendTextMessage(content);
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        // 发送语音消息
                        sendVoiceMessage(voiceFilePath, voiceTimeLength);
                    }
                });
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
                //发送大表情(动态表情)
                sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }
        });

        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);

        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * 设置属性，监听等
     */
    protected void setUpView() {

        if (chatType == EaseConstant.CHATTYPE_SINGLE) { // 单聊
            // 设置标题
            if (EaseUserUtils.getUserInfo(toChatUsername) != null) {

//                titleBar.setTitle(EaseUserUtils.getUserInfo(toChatUsername).getNick());
//                titleBar.setBackgroundColor(mContext.getResources().getColor(R.color.white));

            }
//            try {
//                usernames = EMContactManager.getInstance().getContactUserNames();

//                for (int i = 0; i < usernames.size(); i++) {
//                    if (toChatUsername.equals(usernames.get(i))) {
//
//                        img.setImageResource(R.drawable.renyuan);
//                        haoyouindex = 1;
//                    } else {
//                        img.setImageResource(R.drawable.jia2);
//                        haoyouindex = 2;
//                    }
//                }
//            } catch (EaseMobException e) {
//                e.printStackTrace();
//            }
        } else {
            titleBar.setRightImageResource(R.drawable.ease_to_group_details_normal);
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
//                // 群聊
//                EMGroup group = EMGroupManager.getInstance().getGroup(toChatUsername);
//                if (group != null)
//                    titleBar.setTitle(group.getGroupName());
//                // 监听当前会话的群聊解散被T事件
//                groupListener = new GroupListener();
//                EMGroupManager.getInstance().addGroupChangeListener(groupListener);
            } else {
                onChatRoomViewCreation();
            }

        }
        if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
            onConversationInit();
            onMessageListInit();
        }

        // 设置标题栏点击事件
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
//        titleBar.setRightLayoutClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//                    if (haoyouindex == 1) {
//                        startActivity(new Intent(getActivity(), information.class).putExtra("username", toChatUsername));
//                    } else {
//                        popwind();
//                    }
////                    emptyHistory();
//                } else {
//                    toGroupDetails();
//                }
//            }
//        });
//
//        img.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//                    if (haoyouindex == 1) {
//                        startActivity(new Intent(getActivity(), information.class).putExtra("username", toChatUsername));
//                    } else {
//                        popwind();
//                    }
////                    emptyHistory();
//                } else {
//                    toGroupDetails();
//                }
//            }
//        });
        setRefreshLayoutListener();

        // show forward message if the message is not null
        String forward_msg_id = getArguments().getString("forward_msg_id");
        if (forward_msg_id != null) {
            // 发送要转发的消息
            forwardMessage(forward_msg_id);
        }
    }

    int isblack = 0;

//    public void popwind() {
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        // 引入窗口配置文件
//        View view = inflater.inflate(R.layout.popwind_adduser, null);
//        // 创建PopupWindow对象
//        PopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//        RelativeLayout btn = (RelativeLayout) view.findViewById(R.id.id1);
//        RelativeLayout btn1 = (RelativeLayout) view.findViewById(R.id.id2);
//
//        if (isblack == 1) {
//            btn1.setVisibility(View.INVISIBLE);
//        }
//        // 需要设置一下此参数，点击外边可消失
//        PopupWindow.setBackgroundDrawable(new
//                BitmapDrawable());//这一句 要加，不然点击外边不消失。
//        //设置点击窗口外边窗口消失
//        PopupWindow.setOutsideTouchable(true);
//        // 设置此参数获得焦点，否则无法点击
////设置popwindow的位置  tv:为微信右上角+号view，居于+号下方
//        int mxl = 30;
////        int ll=mmm.getHeight();
//        PopupWindow.showAsDropDown(img, -mxl, 30);
//        PopupWindow.setFocusable(true);
//        // 显示窗口
//        PopupWindow.showAsDropDown(view);
//
//        btn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                progressDialog = new ProgressDialog(getActivity());
//                String stri = getResources().getString(R.string.Is_sending_a_request);
//                progressDialog.setMessage(stri);
//                progressDialog.setCanceledOnTouchOutside(false);
//                progressDialog.show();
//
//                new Thread(new Runnable() {
//                    public void run() {
//                        try {
//                            //demo写死了个reason，实际应该让用户手动填入
//                            String s = getResources().getString(R.string.Add_a_friend);
//                            EMContactManager.getInstance().addContact(toChatUsername, s);
//                            getActivity().runOnUiThread(new Runnable() {
//                                public void run() {
//                                    progressDialog.dismiss();
//                                    String s1 = getResources().getString(R.string.send_successful);
//                                    Toast.makeText(getContext(), s1, Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } catch (final Exception e) {
//                            getActivity().runOnUiThread(new Runnable() {
//                                public void run() {
//                                    progressDialog.dismiss();
//                                    String s2 = getResources().getString(R.string.Request_add_buddy_failure);
//                                    Toast.makeText(getContext(), s2 + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    }
//                }).start();
//
//                PopupWindow.dismiss();
//            }
//        });
//
//        btn1.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                movelist(toChatUsername);
//            }
//        });
//    }

    /**
     * 把user移入到黑名单
     */
    protected void movelist(final String username) {
        final ProgressDialog pd = new ProgressDialog(mContext);
        String st1 = mContext.getResources().getString(R.string.Is_moved_into_blacklist);
        final String st2 = mContext.getResources().getString(R.string.Move_into_blacklist_success);
        final String st3 = mContext.getResources().getString(R.string.Move_into_blacklist_failure);
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    //移除黑名单
                    EMContactManager.getInstance().addUserToBlackList(username, false);
//                    EMContactManager.getInstance().deleteUserFromBlackList(username);
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(mContext, st2, 0).show();
//                            blackList = EMContactManager.getInstance().getBlackListUsernames();
//                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (EaseMobException e) {
                    e.printStackTrace();
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(mContext, st3, 0).show();
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * by lzan13
     * 设置阅后即焚模式的开关，在easeui中默认是关闭状态，需要在Demo层面调用此方法
     *
     * @param isOpen
     */
    public void setReadFire(boolean isOpen) {
        if (chatType == EaseConstant.CHATTYPE_SINGLE && isOpen) {
            isReadFire = true;
            titleBar.setBackgroundResource(R.color.top_bar_red_bg);
            titleBar.setFireImageResource(R.drawable.ease_fire_white_24dp);
            Toast.makeText(getActivity(), R.string.toast_read_fire_opened, Toast.LENGTH_LONG).show();
            // 设置阅后即焚开关监听
            titleBar.setFireClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 根据当前状态，改变标题栏颜色，红色表示阅后即焚状态，蓝色表示正常状态
                    if (isReadFire) {
                        titleBar.setBackgroundResource(R.color.top_bar_normal_bg);
                        Toast.makeText(getActivity(), R.string.toast_read_fire_close, Toast.LENGTH_LONG).show();
                        isReadFire = false;
                    } else {
                        titleBar.setBackgroundResource(R.color.top_bar_red_bg);
                        Toast.makeText(getActivity(), R.string.toast_read_fire_opened, Toast.LENGTH_LONG).show();
                        isReadFire = true;
                    }
                }
            });
        } else {
            titleBar.closeReadFire();
            titleBar.setBackgroundResource(R.color.top_bar_normal_bg);
        }
    }

    /**
     * 注册底部菜单扩展栏item; 覆盖此方法时如果不覆盖已有item，item的id需大于3
     */
    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }


    protected void onConversationInit() {
        // 获取当前conversation对象
        conversation = EMChatManager.getInstance().getConversation(toChatUsername);
        // 把此会话的未读数置为0
        conversation.markAllMessagesAsRead();

        // 初始化db时，每个conversation加载数目是getChatOptions().getNumberOfMessagesLoaded
        // 这个数目如果比用户期望进入会话界面时显示的个数不一样，就多加载一些
        final List<EMMessage> msgs = conversation.getAllMessages();
        int msgCount = msgs != null ? msgs.size() : 0;
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (msgs != null && msgs.size() > 0) {
                msgId = msgs.get(0).getMsgId();
            }
            if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
            } else {
                conversation.loadMoreGroupMsgFromDB(msgId, pagesize - msgCount);
            }
        }

    }

    protected void onMessageListInit() {
        messageList.init(toChatUsername, chatType, chatFragmentHelper != null ?
                chatFragmentHelper.onSetCustomChatRowProvider() : null);
        //设置list item里的控件的点击事件
        setListItemClickListener();

        messageList.getListView().setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });

        isMessageListInited = true;
    }

    protected void setListItemClickListener() {
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onAvatarClick(username);
                }
            }

            @Override
            public void onResendClick(final EMMessage message) {
                new EaseAlertDialog(getActivity(), R.string.resend, R.string.confirm_resend, null, new EaseAlertDialog.AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (!confirmed) {
                            return;
                        }
                        resendMessage(message);
                    }
                }, true).show();
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                contextMenuMessage = message;
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onMessageBubbleLongClick(message);
                }
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                if (chatFragmentHelper != null) {
                    return chatFragmentHelper.onMessageBubbleClick(message);
                }
                return false;
            }
        });
    }

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
                            List<EMMessage> messages;
                            try {
                                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                                    messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                } else {
                                    messages = conversation.loadMoreGroupMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                }
                            } catch (Exception e1) {
                                swipeRefreshLayout.setRefreshing(false);
                                return;
                            }
                            if (messages.size() > 0) {
                                messageList.refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }
                            isloading = false;
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_more_messages),
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片
                if (cameraFile != null && cameraFile.exists())
                    sendImageMessage(cameraFile.getAbsolutePath());
            } else if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_MAP) { // 地图
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    sendLocationMessage(latitude, longitude, locationAddress);
                } else {
                    Toast.makeText(getActivity(), R.string.unable_to_get_loaction, 0).show();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isMessageListInited) {
            messageList.refresh();
        }
        EaseUI.getInstance().pushActivity(getActivity());
        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(
                this,
                new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage,
                        EMNotifierEvent.Event.EventOfflineMessage,
                        EMNotifierEvent.Event.EventDeliveryAck,
                        EMNotifierEvent.Event.EventReadAck,
                        EMNotifierEvent.Event.EventNewCMDMessage});
    }

    @Override
    public void onStop() {
        super.onStop();
        // unregister this event listener when this activity enters the
        // background
        EMChatManager.getInstance().unregisterEventListener(this);

        // 把此activity 从foreground activity 列表里移除
        EaseUI.getInstance().popActivity(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (groupListener != null) {
//            EMGroupManager.getInstance().removeGroupChangeListener(groupListener);
//        }
        if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            EMChatManager.getInstance().leaveChatRoom(toChatUsername);
        }

        if (chatRoomChangeListener != null) {
            EMChatManager.getInstance().removeChatRoomChangeListener(chatRoomChangeListener);
        }
    }

    /**
     * 事件监听,registerEventListener后的回调事件
     * <p>
     * see {@link EMNotifierEvent}
     */
    @Override
    public void onEvent(EMNotifierEvent event) {
        switch (event.getEvent()) {
            case EventNewMessage:
                // 获取到message
                EMMessage message = (EMMessage) event.getData();

                String username = null;
                // 群组消息
                if (message.getChatType() == ChatType.GroupChat || message.getChatType() == ChatType.ChatRoom) {
                    username = message.getTo();
                } else {
                    // 单聊消息
                    username = message.getFrom();
                }

                EMLog.d(TAG, "onEvent: From: " + message.getFrom() + " to: " + message.getTo());
                // 如果是当前会话的消息，刷新聊天页面
                // if sync with other terminal, the getTo() equals toChatUsername
                if (username.equals(toChatUsername) || message.getTo().equals(toChatUsername)) {
                    messageList.refreshSelectLast();
                    // 声音和震动提示有新消息
                    EaseUI.getInstance().getNotifier().viberateAndPlayTone(message);
                } else {
                    // 如果消息不是和当前聊天ID的消息
                    EaseUI.getInstance().getNotifier().onNewMsg(message);
                }

                break;
            case EventDeliveryAck:
            case EventReadAck:
                // 获取到message
//                EMMessage ackMessage = (EMMessage) event.getData();
//                // 判断接收到ack的这条消息是不是阅后即焚的消息，如果是，则说明对方看过消息了，对方会销毁，这边也删除(现在只有txt iamge file三种消息支持 )
//                if (ackMessage.getBooleanAttribute(EaseConstant.EASE_ATTR_READFIRE, false)
//                        && (ackMessage.getType() == Type.TXT || ackMessage.getType() == Type.VOICE || ackMessage.getType() == Type.IMAGE)) {
//                    conversation.removeMessage(ackMessage.getMsgId());
//                }
//                messageList.refresh();
                break;
            case EventOfflineMessage:
                // a list of offline messages
                // List<EMMessage> offlineMessages = (List<EMMessage>)
                // event.getData();
                messageList.refresh();

                break;
            case EventNewCMDMessage:
                EMMessage cmdMessage = (EMMessage) event.getData();
                //获取消息body
                CmdMessageBody cmdMsgBody = (CmdMessageBody) cmdMessage.getBody();
                final String action = cmdMsgBody.action;//获取自定义action
                if (action.equals(EaseConstant.EASE_ATTR_REVOKE)) {
                    EaseCommonUtils.receiveRevokeMessage(getActivity(), cmdMessage);
                    messageList.refresh();
                }
                break;
            default:
                break;
        }

    }

    public void onBackPressed() {
        if (inputMenu.onBackPressed()) {
            getActivity().finish();
            if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
                EMChatManager.getInstance().leaveChatRoom(toChatUsername);
            }
        }
    }

    protected void onChatRoomViewCreation() {
        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Joining......");
        EMChatManager.getInstance().joinChatRoom(toChatUsername, new EMValueCallBack<EMChatRoom>() {

            @Override
            public void onSuccess(final EMChatRoom value) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity().isFinishing() || !toChatUsername.equals(value.getUsername()))
                            return;
                        pd.dismiss();
                        EMChatRoom room = EMChatManager.getInstance().getChatRoom(toChatUsername);
                        if (room != null) {
                            titleBar.setTitle(room.getName());
                        } else {
                            titleBar.setTitle(toChatUsername);
                        }
                        EMLog.d(TAG, "join room success : " + room.getName());
                        addChatRoomChangeListenr();
                        onConversationInit();
                        onMessageListInit();
                    }
                });
            }

            @Override
            public void onError(final int error, String errorMsg) {
                // TODO Auto-generated method stub
                EMLog.d(TAG, "join room failure : " + error);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });
                getActivity().finish();
            }
        });
    }


    protected void addChatRoomChangeListenr() {
        chatRoomChangeListener = new EMChatRoomChangeListener() {

            @Override
            public void onChatRoomDestroyed(String roomId, String roomName) {
                if (roomId.equals(toChatUsername)) {
                    showChatroomToast(" room : " + roomId + " with room name : " + roomName + " was destroyed");
                    getActivity().finish();
                }
            }

            @Override
            public void onMemberJoined(String roomId, String participant) {
                showChatroomToast("member : " + participant + " join the room : " + roomId);
            }

            @Override
            public void onMemberExited(String roomId, String roomName, String participant) {
                showChatroomToast("member : " + participant + " leave the room : " + roomId + " room name : " + roomName);
            }

            @Override
            public void onMemberKicked(String roomId, String roomName, String participant) {
                if (roomId.equals(toChatUsername)) {
                    String curUser = EMChatManager.getInstance().getCurrentUser();
                    if (curUser.equals(participant)) {
                        EMChatManager.getInstance().leaveChatRoom(toChatUsername);
                        getActivity().finish();
                    } else {
                        showChatroomToast("member : " + participant + " was kicked from the room : " + roomId + " room name : " + roomName);
                    }
                }
            }

        };

        EMChatManager.getInstance().addChatRoomChangeListener(chatRoomChangeListener);
    }

    protected void showChatroomToast(final String toastContent) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), toastContent, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 扩展菜单栏item点击事件
     */
    public class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {

        @Override
        public void onClick(int itemId, View view) {
            if (chatFragmentHelper != null) {
                if (chatFragmentHelper.onExtendMenuItemClick(itemId, view)) {
                    return;
                }
            }
            switch (itemId) {
                case ITEM_TAKE_PICTURE: // 拍照
                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
                    selectPicFromLocal(); // 图库选择图片
                    break;
//            case ITEM_LOCATION: // 位置
//                startActivityForResult(new Intent(getActivity(), EaseBaiduMapActivity.class), REQUEST_CODE_MAP);
//                break;

                default:
                    break;
            }
        }

    }


    //发送消息方法
    //==========================================================================
    protected void sendTextMessage(String content) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        if (id != null) {
            message.setAttribute("id", id);
            message.setAttribute("type", type);
        }
        sendMessage(message);
    }

    protected void sendBigExpressionMessage(String name, String identityCode) {
        EMMessage message = EaseCommonUtils.createExpressionMessage(toChatUsername, name, identityCode);
        if (id != null) {
            message.setAttribute("id", id);
            message.setAttribute("type", type);
        }
        sendMessage(message);
    }

    protected void sendVoiceMessage(String filePath, int length) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        if (id != null) {
            message.setAttribute("id", id);
            message.setAttribute("type", type);
        }
        sendMessage(message);
    }

    protected void sendImageMessage(String imagePath) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
        if (id != null) {
            message.setAttribute("id", id);
            message.setAttribute("type", type);
        }
        sendMessage(message);
    }

    protected void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        if (id != null) {
            message.setAttribute("id", id);
            message.setAttribute("type", type);
        }
        sendMessage(message);
    }

    protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        if (id != null) {
            message.setAttribute("id", id);
            message.setAttribute("type", type);
        }
        sendMessage(message);
    }

    protected void sendFileMessage(String filePath) {
        EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
        if (id != null) {
            message.setAttribute("id", id);
            message.setAttribute("type", type);
        }
        sendMessage(message);
    }

    protected void sendMessage(EMMessage message) {
        if (chatFragmentHelper != null) {
            //设置扩展属性
            chatFragmentHelper.onSetMessageAttributes(message);
        }
        // 如果是群聊，设置chattype,默认是单聊
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            message.setChatType(ChatType.GroupChat);
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            message.setChatType(ChatType.ChatRoom);
        }
        //发送消息
        EMChatManager.getInstance().sendMessage(message, null);
        //刷新ui
        messageList.refreshSelectLast();
    }


    public void resendMessage(EMMessage message) {
        message.status = EMMessage.Status.CREATE;
        EMChatManager.getInstance().sendMessage(message, null);
        messageList.refresh();
    }

    //===================================================================================


    /**
     * 根据图库图片uri发送图片
     *
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }

    /**
     * 根据uri发送文件
     *
     * @param uri
     */
    protected void sendFileByUri(Uri uri) {
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;

            try {
                cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            Toast.makeText(getActivity(), R.string.File_does_not_exist, 0).show();
            return;
        }
        //大于10M不让发送
        if (file.length() > 10 * 1024 * 1024) {
            Toast.makeText(getActivity(), R.string.The_file_is_not_greater_than_10_m, 0).show();
            return;
        }
        sendFileMessage(filePath);
    }

    /**
     * 照相获取图片
     */
    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isExitsSdcard()) {
            Toast.makeText(getActivity(), R.string.sd_card_does_not_exist, 0).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMChatManager.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                REQUEST_CODE_CAMERA);
    }

    /**
     * 从图库获取图片
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }


    /**
     * 点击清空聊天记录
     */
    protected void emptyHistory() {
        String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
        new EaseAlertDialog(getActivity(), null, msg, null, new EaseAlertDialog.AlertDialogUser() {

            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    // 清空会话
                    EMChatManager.getInstance().clearConversation(toChatUsername);
                    messageList.refresh();
                }
            }
        }, true).show();
        ;
    }

    /**
     * 点击进入群组详情
     */
    protected void toGroupDetails() {
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EMGroup group = EMGroupManager.getInstance().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, 0).show();
                return;
            }
            if (chatFragmentHelper != null) {
                chatFragmentHelper.onEnterToChatDetails();
            }
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            if (chatFragmentHelper != null) {
                chatFragmentHelper.onEnterToChatDetails();
            }
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 转发消息
     *
     * @param forward_msg_id
     */
    protected void forwardMessage(String forward_msg_id) {
        final EMMessage forward_msg = EMChatManager.getInstance().getMessage(forward_msg_id);
        Type type = forward_msg.getType();
        switch (type) {
            case TXT:
                if (forward_msg.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
                    sendBigExpressionMessage(((TextMessageBody) forward_msg.getBody()).getMessage(),
                            forward_msg.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null));
                } else {
                    // 获取消息内容，发送消息
                    String content = ((TextMessageBody) forward_msg.getBody()).getMessage();
                    sendTextMessage(content);
                }
                break;
            case IMAGE:
                // 发送图片
                String filePath = ((ImageMessageBody) forward_msg.getBody()).getLocalUrl();
                if (filePath != null) {
                    File file = new File(filePath);
                    if (!file.exists()) {
                        // 不存在大图发送缩略图
                        filePath = EaseImageUtils.getThumbnailImagePath(filePath);
                    }
                    sendImageMessage(filePath);
                }
                break;
            default:
                break;
        }

        if (forward_msg.getChatType() == ChatType.ChatRoom) {
            EMChatManager.getInstance().leaveChatRoom(forward_msg.getTo());
        }
    }


    protected EaseChatFragmentHelper chatFragmentHelper;

    public void setChatFragmentHelper(EaseChatFragmentHelper chatFragmentHelper) {
        this.chatFragmentHelper = chatFragmentHelper;
    }


    public interface EaseChatFragmentHelper {
        /**
         * 设置消息扩展属性
         */
        void onSetMessageAttributes(EMMessage message);

        /**
         * 进入会话详情
         */
        void onEnterToChatDetails();

        /**
         * 用户头像点击事件
         *
         * @param username
         */
        void onAvatarClick(String username);

        /**
         * 消息气泡框点击事件
         */
        boolean onMessageBubbleClick(EMMessage message);

        /**
         * 消息气泡框长按事件
         */
        void onMessageBubbleLongClick(EMMessage message);

        /**
         * 扩展输入栏item点击事件,如果要覆盖EaseChatFragment已有的点击事件，return true
         *
         * @param view
         * @param itemId
         * @return
         */
        boolean onExtendMenuItemClick(int itemId, View view);

        /**
         * 设置自定义chatrow提供者
         *
         * @return
         */
        EaseCustomChatRowProvider onSetCustomChatRowProvider();
    }

    @Override
    public void Getsuccess(JSONObject jsonObject, int what) {
        super.Getsuccess(jsonObject, what);
        switch (what) {
            case 1:

                L.e("ibject", jsonObject.toString());

                if (jsonObject.getIntValue("code") == 1) {
                    if (type.equals("3")) {

                    } else {
                        titl.setText(jsonObject.getString("title"));
                        address.setText(jsonObject.getString("address"));
                        telephone = jsonObject.getString("phone");
                        titlebar.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case 2:

                list.clear();
                JSONArray list2 = jsonObject.getJSONArray("name_list");
                List<String> newStr5 = JSONObject.parseArray(list2.toJSONString(), String.class);
                list.addAll(newStr5);
                L.e("list", list.toString());

                titleBar.setTitle(list.get(0));
                L.e("tittl", title.getText().toString());
                break;


            case 3:
                int code = jsonObject.getIntValue("code");
                L.e("code", jsonObject.getIntValue("code") + "");
                if (code == 1) {
                    list1.clear();

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<friend> newStr9 = JSONObject.parseArray(jsonArray.toJSONString(), friend.class);
                    list1.addAll(newStr9);
                    APP.friendList.addAll(list1);
                    L.e("friend_list", APP.friendList.toString() + "人" + toChatUsername);

                    if (list1.size() == 0) {
                        img.setImageResource(R.drawable.jia2);
                        haoyouindex = 2;
                    }
                    for (int i = 0; i < list1.size(); i++) {
                        if (toChatUsername.equals(list1.get(i).getFriend_phone())) {

                            img.setImageResource(R.drawable.renyuan);
                            haoyouindex = 1;
                            break;
                        } else {
                            img.setImageResource(R.drawable.jia2);
                            haoyouindex = 2;
                        }
                    }
                }
                break;
        }
    }
}