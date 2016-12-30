package com.compassecg.test720.compassecg.tooclass.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.compassecg.test720.compassecg.APP;
import com.compassecg.test720.compassecg.DemoHelper;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.tooclass.EaseConstant;
import com.compassecg.test720.compassecg.tooclass.InviteMessgeDao;
import com.compassecg.test720.compassecg.tooclass.avtivity.EaseBaseFragment;
import com.compassecg.test720.compassecg.tooclass.utils.EaseCommonUtils;
import com.compassecg.test720.compassecg.tooclass.widget.EaseConversationList;
import com.compassecg.test720.compassecg.unitl.Connector;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.loopj.android.http.RequestParams;
import com.test720.auxiliary.Utils.L;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import static com.easemob.EMNotifierEvent.Event.EventConversationListChanged;
import static com.easemob.EMNotifierEvent.Event.EventNewCMDMessage;
import static com.easemob.EMNotifierEvent.Event.EventNewMessage;
import static com.easemob.EMNotifierEvent.Event.EventOfflineMessage;
import static com.easemob.EMNotifierEvent.Event.EventReadAck;


/**
 * 会话列表fragment
 */
public class EaseConversationListFragment extends EaseBaseFragment implements OnClickListener, EMEventListener {

    protected boolean hidden;
    protected boolean OK;
    protected List<EMConversation> conversationList = new ArrayList<EMConversation>();
    protected EaseConversationList conversationListView;
    protected FrameLayout errorItemContainer;
    protected RelativeLayout add_to_user;
    protected boolean isConflict;
    protected PopupWindow PopupWindow;//
    private List<String> list = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    private int SATAR = 1;
    int okdatae;
    private InviteMessgeDao inviteMessgeDao;
    ImageView img1;
    private boolean isRunning = true;
    private Runnable runis;
    private List<String> list3 = new ArrayList<>();

    private void shit() {

        //todo shuaxin

        if (isRunning) {
            conversationListView.postDelayed(runis = new Runnable() {
                @Override
                public void run() {
//                    L.e("消息", inviteMessgeDao.getUnreadMessagesCount() + getUnreadMsgCountTotal() + "");

                    if (inviteMessgeDao == null) {
                        inviteMessgeDao = new InviteMessgeDao(getActivity());
                    }
//                    L.e("消息", inviteMessgeDao.getUnreadMessagesCount() + "");
                    if (inviteMessgeDao.getUnreadMessagesCount() > 0) {
                        img1.setImageResource(R.drawable.jia4);
                    } else {
                        img1.setImageResource(R.drawable.jia2);
                    }
                    shit();

                }
            }, 3000);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ease_fragment_conversation_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 会话列表控件
        inviteMessgeDao = new InviteMessgeDao(getActivity());
        conversationListView = (EaseConversationList) getView().findViewById(R.id.list);
        // 搜索框
//        query = (EditText) getView().findViewById(R.id.query);
        // 搜索框中清除button
//        clearSearch = (ImageButton) getView().findViewById(R.id.search_clear);
        img1 = (ImageView) getView().findViewById(R.id.img1);
        errorItemContainer = (FrameLayout) getView().findViewById(R.id.fl_error_item);
        add_to_user = (RelativeLayout) getView().findViewById(R.id.add_to_user);
        add_to_user.setOnClickListener(this);
        shit();

    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for (EMConversation conversation : EMChatManager.getInstance().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

    @Override
    protected void setUpView() {

        getDatae();
        if (listItemClickListener != null) {
            conversationListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EMConversation conversation = conversationListView.getItem(position);
                    listItemClickListener.onListItemClicked(conversation);
                }
            });
        }
        EMChatManager.getInstance().addConnectionListener(connectionListener);


        conversationListView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
    }


    /**
     * 监听事件
     */
    @Override
    public void onEvent(EMNotifierEvent event) {

        L.e("oneven", "进来没");
        switch (event.getEvent()) {
            case EventNewMessage: // 普通消息
                EMMessage message = (EMMessage) event.getData();
                // 提示新消息
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
                getDatae();
                break;
            case EventOfflineMessage: {
                getDatae();

                break;
            }

            case EventConversationListChanged: {
                getDatae();
                break;
            }

            case EventNewCMDMessage:
                EMMessage cmdMessage = (EMMessage) event.getData();
                //获取消息body
                CmdMessageBody cmdMsgBody = (CmdMessageBody) cmdMessage.getBody();
                final String action = cmdMsgBody.action;//获取自定义action
                if (action.equals(EaseConstant.EASE_ATTR_REVOKE)) {
                    EaseCommonUtils.receiveRevokeMessage(getActivity(), cmdMessage);
                }
                getDatae();
                break;
            case EventReadAck:
                // TODO 这里当此消息未加载到内存中时，ackMessage会为null，消息的删除会失败
                EMMessage ackMessage = (EMMessage) event.getData();
                EMConversation conversation = EMChatManager.getInstance().getConversation(ackMessage.getTo());
                // 判断接收到ack的这条消息是不是阅后即焚的消息，如果是，则说明对方看过消息了，对方会销毁，这边也删除(现在只有txt iamge file三种消息支持 )
                if (ackMessage.getBooleanAttribute(EaseConstant.EASE_ATTR_READFIRE, false)
                        && (ackMessage.getType() == EMMessage.Type.TXT
                        || ackMessage.getType() == EMMessage.Type.VOICE
                        || ackMessage.getType() == EMMessage.Type.IMAGE)) {
                    // 判断当前会话是不是只有一条消息，如果只有一条消息，并且这条消息也是阅后即焚类型，当对方阅读后，这边要删除，会话会被过滤掉，因此要加载上一条消息
                    if (conversation.getAllMessages().size() == 1 && conversation.getLastMessage().getMsgId().equals(ackMessage.getMsgId())) {
                        if (ackMessage.getChatType() == EMMessage.ChatType.Chat) {
                            conversation.loadMoreMsgFromDB(ackMessage.getMsgId(), 1);
                        } else {
                            conversation.loadMoreGroupMsgFromDB(ackMessage.getMsgId(), 1);
                        }
                    }
                    conversation.removeMessage(ackMessage.getMsgId());
                }
                getDatae();
                break;
            default:
                break;
        }
    }
    public void getDataetow() {

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {

//                // 更新消息未读数

                if (inviteMessgeDao == null) {
                    inviteMessgeDao = new InviteMessgeDao(getActivity());
                }

                if (inviteMessgeDao.getUnreadMessagesCount() > 0) {
                    img1.setImageResource(R.drawable.jia4);
                } else {
                    img1.setImageResource(R.drawable.jia2);
                }
                conversationList.clear();
                conversationList.addAll(loadConversationList());
                list2.clear();
                for (int i = 0; i < conversationList.size(); i++) {
                    list2.add(conversationList.get(i).getUserName());
                }
                RequestParams params = new RequestParams();
                params.put("id", APP.uuid);
                params.put("mobile_json", JSONArray.toJSONString(list2));
                Post(Connector.returnNickname, params, SATAR);

                L.e("log", "进来没");
//            }
//        });
    }

    public void getDatae() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                // 更新消息未读数

                if (inviteMessgeDao == null) {
                    inviteMessgeDao = new InviteMessgeDao(getActivity());
                }

                if (inviteMessgeDao.getUnreadMessagesCount() > 0) {
                    img1.setImageResource(R.drawable.jia4);
                } else {
                    img1.setImageResource(R.drawable.jia2);
                }
                conversationList.clear();
                conversationList.addAll(loadConversationList());
                list2.clear();
                for (int i = 0; i < conversationList.size(); i++) {
                    list2.add(conversationList.get(i).getUserName());
                }
                RequestParams params = new RequestParams();
                params.put("id", APP.uuid);
                params.put("mobile_json", JSONArray.toJSONString(list2));
                Post(Connector.returnNickname, params, SATAR);

                L.e("log", "进来没");
            }
        });
    }


    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.CONNECTION_CONFLICT) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };
    private EaseConversationListItemClickListener listItemClickListener;

    protected Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    onConnectionDisconnected();
                    break;
                case 1:
                    onConnectionConnected();
                    break;
                case 3:
                    L.e("消息", inviteMessgeDao.getUnreadMessagesCount() + "");
                    if (inviteMessgeDao == null) {
                        inviteMessgeDao = new InviteMessgeDao(getActivity());
                    }
                    if (inviteMessgeDao.getUnreadMessagesCount() > 0) {
                        img1.setImageResource(R.drawable.jia4);
                    } else {
                        img1.setImageResource(R.drawable.jia2);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 连接到服务器
     */
    protected void onConnectionConnected() {
        errorItemContainer.setVisibility(View.GONE);
    }

    /**
     * 连接断开
     */
    protected void onConnectionDisconnected() {
        errorItemContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 刷新页面
     */
    public void refresh() {

        getDatae();
//            conversationList.addAll(loadConversationList());


    }

    /**
     * 获取会话列表
     *
     * @param context
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // 获取所有会话，包括陌生人
        Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();
        // 过滤掉messages size为0的conversation
        /**
         * 如果在排序过程中有新消息收到，lastMsgTime会发生变化 影响排序过程，Collection.sort会产生异常
         * 保证Conversation在Sort过程中最后一条消息的时间不变 避免并发问题
         */
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    // if(conversation.getType() !=
                    // EMConversationType.ChatRoom){
                    sortList.add(
                            new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                    // }
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * 根据最后一条消息的时间排序
     *
     * @param usernames
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first == con2.first) {
                    return 0;
                } else if (con2.first > con1.first) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow()
                .getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
//            refresh();
            getDataetow();
        }
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(getActivity());

        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(this,
                new EMNotifierEvent.Event[]{
                        EventNewMessage,
                        EventOfflineMessage,
                        EventConversationListChanged,
                        EventNewCMDMessage,
                        EventReadAck
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMChatManager.getInstance().removeConnectionListener(connectionListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isConflict) {
            outState.putBoolean("isConflict", true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_user:

                break;
        }

    }




    public interface EaseConversationListItemClickListener {
        /**
         * 会话listview item点击事件
         *
         * @param conversation 被点击item所对应的会话
         */
        void onListItemClicked(EMConversation conversation);
    }

    /**
     * 设置listview item点击事件
     *
     * @param listItemClickListener
     */
    public void setConversationListItemClickListener(EaseConversationListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    @Override
    public void Getsuccess(JSONObject jsonObject, int what) {
        super.Getsuccess(jsonObject, what);
        list.clear();
        list3.clear();
        JSONArray list2 = jsonObject.getJSONArray("name_list");

        for (int i = 0; i < list2.size(); i++) {
            Log.e("===", list2.getString(i));
            list.add(list2.getString(i));
        }
        JSONArray list1 = jsonObject.getJSONArray("header_list");
        for (int i = 0; i < list1.size(); i++) {
            Log.e("===", list1.getString(i));
            list3.add(list1.getString(i));
        }
        rturnlist(list3);
        if (!OK) {
            OK = true;
            if (conversationList.size()!=list.size()){
                getDatae();

            }else {
                conversationListView.init(conversationList, list, list3);
            }
        } else {
            L.e("ll");
            if (conversationList.size() != list.size()) {
                getDatae();
            } else {
                conversationListView.setlist(conversationList, list, list3);
            }
        }
    }

    public void  rturnlist(List<String> list3) {

    }
}
