package com.compassecg.test720.compassecg.GroupConsultation.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.compassecg.test720.compassecg.MainActivity;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.UploadPicture.ChoosePicFolderActivity;
import com.compassecg.test720.compassecg.View.KeyboardListenRelativeLayout;
import com.compassecg.test720.compassecg.unitl.AudioRecoderUtils;
import com.compassecg.test720.compassecg.unitl.LocalImageHelper;
import com.compassecg.test720.compassecg.unitl.PopupWindowFactory;
import com.compassecg.test720.compassecg.unitl.TimeUtils;
import com.orhanobut.logger.Logger;
import com.test720.auxiliary.Utils.NoBarBaseActivity;
import com.test720.auxiliary.Utils.T;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddAnswerActivityH extends NoBarBaseActivity  {

    private RelativeLayout rl_text;
    private RelativeLayout rl_song;
    private RelativeLayout rl_pic;
    private GridView lv_gridview;
    private LocalImageHelper.LocalFile fakeImage;
    int MaxNumaber = 9;//要求上传最大数值
    private static final int A = 999;
    private List<LocalImageHelper.LocalFile> showFiles = new ArrayList<>();//附件材料
    private List<String> allItems = new ArrayList<>();
    private MyAdapter adapter;
    private AlertDialog picDialog;
    private String imageName;
    private File imageFile;
    /**
     * 请求裁剪
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;
    private LinearLayout ll_bottom;
    private KeyboardListenRelativeLayout root;
    private EditText et_content;
    private ImageView iv_back;
    private ImageView iv_jianpan;
    private ImageView iv_lvyin;
    private ImageView iv_pic;
    private ImageView mImageView;
    private TextView mTextView;
    private AudioRecoderUtils mAudioRecoderUtils;
    private PopupWindow mPop;


    private int time=0;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情
            if(msg.what==1){
                if(record){
                    Log.i("WOLF","把time=1");
                    time=1;
                }
            }
            super.handleMessage(msg);
        }
    };
    private boolean record=true;
    private TextView iv_song;
    private ImageView iv_yuyin;
    private AnimationDrawable animationDrawable;
    private ImageView iv_yuyin2;
    private FrameLayout fl_yuyin;
    private boolean playing=false;
    private MediaPlayer player;
    private long record_time=0;
    private boolean more30=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);
        initView();
        setAdapter();
        setListenner();
        setgridview();
//        controlKeyboardLayout(root,ll_bottom);
    }

    private void setAdapter() {

    }

    private void setListenner() {
        rl_text.setOnClickListener(this);
        rl_song.setOnClickListener(this);
        rl_pic.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_song.setOnClickListener(this);

       iv_song.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               T.showShort(AddAnswerActivityH.this,"点击了删除");
               fl_yuyin.setVisibility(View.GONE);
               if(playing){
                   //停止播放
                   player.stop();
                   animationDrawable.stop();
                   iv_yuyin2.setVisibility(View.VISIBLE);
                   //playing=false;
               }
               return false;
           }
       });
        root.setOnKeyboardStateChangedListener(new KeyboardListenRelativeLayout.IOnKeyboardStateChangedListener() {
            @Override
            public void onKeyboardStateChanged(int state) {
                switch (state) {
                    case KeyboardListenRelativeLayout.KEYBOARD_STATE_HIDE://软键盘隐藏
                        Log.i("WOLF","软键盘隐藏");
                        Logger.d("软键盘隐藏");
                        //更改图标
                        iv_jianpan.setImageResource(R.mipmap.answer_btn_jianpan);
                        //controlKeyboardLayout(root);
                        break;
                    case KeyboardListenRelativeLayout.KEYBOARD_STATE_SHOW://软键盘显示
                        Log.i("WOLF","软键盘显示");
                        Logger.d("软键盘显示");
                        //更改图标
                        iv_jianpan.setImageResource(R.mipmap.answer_btn_jianpan_pre);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void initView() {
        rl_text=getView(R.id.rl_text);
        rl_song=getView(R.id.rl_song);
        rl_pic=getView(R.id.rl_pic);
        lv_gridview=getView(R.id.lv_gridview);
        root=getView(R.id.root);
        ll_bottom=getView(R.id.ll_bottom);
        et_content=getView(R.id.et_content);
        iv_back=getView(R.id.iv_back);
        iv_jianpan=getView(R.id.iv_jianpan);
        iv_lvyin=getView(R.id.iv_lvyin);
        iv_pic=getView(R.id.iv_pic);
        iv_song=getView(R.id.iv_song);
        iv_yuyin=getView(R.id.iv_yuyin);
        iv_yuyin2=getView(R.id.iv_yuyin2);
        fl_yuyin=getView(R.id.fl_yuyin);

        /**
         * 录音相关
         */
        final KeyboardListenRelativeLayout rl = (KeyboardListenRelativeLayout) findViewById(R.id.root);

        rl_song = (RelativeLayout) findViewById(R.id.rl_song);

        //PopupWindow的布局文件
        final View view = View.inflate(this, R.layout.layout_microphone, null);

        final PopupWindowFactory mPop = new PopupWindowFactory(this,view);

        //PopupWindow布局文件里面的控件
        mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
        mTextView = (TextView) view.findViewById(R.id.tv_recording_time);

        mAudioRecoderUtils = new AudioRecoderUtils();

        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                mTextView.setText(TimeUtils.long2String(time));
                if(time>30*1000){
                    //超过30秒停止录音
                    more30=true;
                    record=false;
                    record_time = mAudioRecoderUtils.stopRecord();
                    iv_song.setText(record_time/1000+"〞");
                    mPop.dismiss();
                    fl_yuyin.setVisibility(View.VISIBLE);
                }
            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                //Toast.makeText(AddAnswerActivityH.this, "录音保存在：" + filePath, Toast.LENGTH_SHORT).show();
                mTextView.setText(TimeUtils.long2String(0));
            }
        });

        //Button的touch监听
        rl_song.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        Log.i("WOLF","ACTION_DOWN"+time);
                        if(playing){
                            //停止播放
                            player.stop();
                            animationDrawable.stop();
                            iv_yuyin2.setVisibility(View.VISIBLE);
                            playing=false;
                        }

                        //新线程，按下小于一秒不保存录音
                        record=true;
                        MyThread mThread=new MyThread();
                        new Thread(mThread).start();

                        mPop.showAtLocation(rl,Gravity.CENTER,0,0);

                        //mButton.setText("松开保存");
                        mAudioRecoderUtils.startRecord();

                        break;

                    case MotionEvent.ACTION_UP:
                        record=false;
                        Log.i("WOLF","ACTION_UP"+time);

                        record_time = mAudioRecoderUtils.stopRecord();
                        if(more30){
                            iv_song.setText("30〞");
                            more30=false;
                        }else {
                            iv_song.setText(record_time/1000+"〞");
                        }


                      /*  if(time==1) {

                                mAudioRecoderUtils.stopRecord();

                               //结束录音（保存录音文件）
//                        mAudioRecoderUtils.cancelRecord();    //取消录音（不保存录音文件）
                        }else {
                            T.showShort(AddAnswerActivityH.this,"录音时间少于1秒");
                            mAudioRecoderUtils.cancelRecord();//取消录音（不保存录音文件）
                        }
                        time=0;*/
                        mPop.dismiss();
                        fl_yuyin.setVisibility(View.VISIBLE);
                        //mButton.setText("按住说话");

                        break;
                }
                return true;
            }
        });

    }

    /**
     * 线程
     */
    public class MyThread implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (record) {
                try {
                    Thread.sleep(1000);// 线程暂停10秒，单位毫秒
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);// 发送消息

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_text:
                popInput();
                iv_jianpan.setImageResource(R.mipmap.answer_btn_jianpan_pre);

                break;
            case R.id.rl_song:
                closeKey();
                break;
            case R.id.rl_pic:
                closeKey();
                showPicDialog();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_song:
                if(playing){
                    //停止播放
                    player.stop();
                    animationDrawable.stop();
                    iv_yuyin2.setVisibility(View.VISIBLE);
                    playing=false;
                }else {
                    //开始播放
                    iv_yuyin2.setVisibility(View.GONE);

                    player = new MediaPlayer();
                    String path = "/sdcard/record/wolf.amr";
                    try {
                        player.setDataSource(path);
                        player.prepare();
                        player.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            animationDrawable.stop();
                            iv_yuyin2.setVisibility(View.VISIBLE);
                            playing=false;
                        }
                    });

                    //开始动画
                    iv_yuyin.setBackgroundResource(R.drawable.yuyin);
                    animationDrawable = (AnimationDrawable)
                            iv_yuyin.getBackground();
                    animationDrawable.start();
                    playing=true;
                }

                break;
        }
    }

    /**
     * 关闭键盘
     */
    private void closeKey() {
        InputMethodManager  inputManager =
                (InputMethodManager)et_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
        //更改图标
        iv_jianpan.setImageResource(R.mipmap.answer_btn_jianpan);
    }

    /**
     * 获取焦点并弹出输入法
     */
    private void popInput() {
        et_content.setFocusable(true);
        et_content.setFocusableInTouchMode(true);
        et_content.requestFocus();
        InputMethodManager  inputManager =
                (InputMethodManager)et_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et_content, 0);
    }

    public void setgridview() {
        lv_gridview = getView(R.id.lv_gridview);
        Resources r = mContext.getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.mipmap.btn_tianjia) + "/"
                + r.getResourceTypeName(R.mipmap.btn_tianjia) + "/"
                + r.getResourceEntryName(R.mipmap.btn_tianjia));
        fakeImage = LocalImageHelper.getInstance().getFake();
        fakeImage.setOriginalUri("fake");
        fakeImage.setThumbnailUri(uri.toString());
        List<LocalImageHelper.LocalFile> items = LocalImageHelper.getInstance().getCheckedItems();
        if (items.size() < MaxNumaber) {
            if(items.size()==0){
                //没有图片的时候不显示“加号”
            }else {
                showFiles.addAll(items);
                showFiles.add(fakeImage);
            }
        } else {
            showFiles.addAll(items);
        }
        adapter = new MyAdapter(mContext, showFiles);
        lv_gridview.setAdapter(adapter);
        setGridViewHeight(lv_gridview);
    }

    public class MyAdapter extends BaseAdapter {
        private Context m_context;
        List<LocalImageHelper.LocalFile> files;


        public MyAdapter(Context context, List<LocalImageHelper.LocalFile> files) {
            m_context = context;
            this.files = files;
        }

        @Override
        public int getCount() {
            return files.size();
        }

        @Override
        public LocalImageHelper.LocalFile getItem(int i) {
            return files.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;

            if (convertView == null || convertView.getTag() == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(m_context, R.layout.slmple_layout_grdview, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                viewHolder.checkBox.setOnCheckedChangeListener(new Mycheck());
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if(files.size()!=0) {

                if (files.get(i) == fakeImage) {
                    viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPicDialog();
                        }
                    });
                    viewHolder.checkBox.setVisibility(View.GONE);
                } else {
                    viewHolder.imageView.setOnClickListener(null);
                    viewHolder.checkBox.setVisibility(View.VISIBLE);
                }
                LocalImageHelper.LocalFile localFile = files.get(i);
                Glide.with(mContext).load(localFile.getThumbnailUri()).into(viewHolder.imageView);

                viewHolder.checkBox.setTag(localFile);
                viewHolder.checkBox.setChecked(files.contains(localFile));
            }
            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
        }

        private class Mycheck implements CompoundButton.OnCheckedChangeListener {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    if (files.contains(compoundButton.getTag())) {
                        //删除当前页面显示的图片
                        files.remove(compoundButton.getTag());
                        //  L.e("===files", files.size() + "");
                        //删除helper里选中的对应图片
                        LocalImageHelper.getInstance().getCheckedItems().remove(compoundButton.getTag());
//                        L.e("===files2", LocalImageHelper.getInstance().getCheckedItems().size() + "");
                        List<LocalImageHelper.LocalFile> items = LocalImageHelper.getInstance().getCheckedItems();
                        showFiles.clear();
                        if (items.size() < MaxNumaber) {
                            if(items.size()==0){

                            }else {
                                showFiles.addAll(items);
                                showFiles.add(fakeImage);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        setGridViewHeight(lv_gridview);
                    }
                }
            }
        }
    }

    //弹出对话框
    public void showPicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        picDialog = builder.create();
        picDialog.show();
        // 获取dialog的窗口
        Window window = picDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(android.R.color.white);
        View v = View.inflate(this, R.layout.dialog_choose_pic, null);
        // 获取v对象中的控件 .setOnClickListener
        picDialog.setCanceledOnTouchOutside(true);
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lParams = window.getAttributes();
        lParams.width = (int) (display.getWidth());
        window.setAttributes(lParams);
        v.findViewById(R.id.take_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChoosePicFolderActivity.class);
                intent.putExtra("MaxNumber", MaxNumaber);
                startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCROP);
                //当界面摧毁后, 该发布界面的图片都清空, 防止下一个同类界面的选图重用
                picDialog.cancel();
            }
        });
        v.findViewById(R.id.upload_from_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePics();
//当界面摧毁后, 该发布界面的图片都清空, 防止下一个同类界面的选图重用

                picDialog.dismiss();

            }
        });
        v.findViewById(R.id.pic_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picDialog.dismiss();
            }
        });
        window.setContentView(v);
    }

    //相机拍照
    private void takePics() {
        File path = Environment.getExternalStorageDirectory();
        //可能拍照多张
        imageName = "info" + System.currentTimeMillis() + ".png";
        String cameraPath = LocalImageHelper.getInstance().setCameraImgPath();
        imageFile = new File(cameraPath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent, A);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_GETIMAGE_BYCROP:
                if (LocalImageHelper.getInstance().isResultOk()) {
                    LocalImageHelper.getInstance().setResultOk(false);
                    //获取选中的图片
                    List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                    showFiles.clear();
                    showFiles.addAll(files);
                    if (files.size() < MaxNumaber) {
                        showFiles.add(fakeImage);
                    }
                    List<LocalImageHelper.LocalFile> checks = LocalImageHelper.getInstance().getCheckedItems();

                    allItems.clear();
                    for (LocalImageHelper.LocalFile f : checks) {
                        allItems.add(f.getThumbnailUri());
                    }
                    adapter.notifyDataSetChanged();
                    setGridViewHeight(lv_gridview);
                }
                break;
            case A:
//                Log.e("===skkkkkkkkkkk", showFiles.toString());
                if (resultCode == Activity.RESULT_OK) {
                    String cameraPath = LocalImageHelper.getInstance().getCameraImgPath();
                    if (cameraPath == null | cameraPath.equals("")) {
                        Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File file = new File(cameraPath);
                    Uri uri = Uri.fromFile(file);
                    LocalImageHelper.LocalFile b = new LocalImageHelper.LocalFile();
                    b.setOriginalUri(uri.toString());
                    b.setThumbnailUri(uri.toString());
                    b.setOrientation(getBitmapDegree(cameraPath));
                    LocalImageHelper.getInstance().getCheckedItems().add(b);
                    LocalImageHelper.getInstance().setResultOk(true);
                    List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                    showFiles.clear();

                    showFiles.addAll(files);
                    if (files.size() < MaxNumaber) {
                        showFiles.add(fakeImage);
                    }
                    //   L.e("===size", files.size() + "");
                    List<LocalImageHelper.LocalFile> checks = LocalImageHelper.getInstance().getCheckedItems();
                    allItems.clear();

                    for (LocalImageHelper.LocalFile f : checks) {
                        allItems.add(f.getThumbnailUri());
                    }
                    adapter.notifyDataSetChanged();
                    setGridViewHeight(lv_gridview);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "取消拍照", Toast.LENGTH_SHORT).show();
                } else
                    break;
        }
    }

    /**
     * 读取图片的旋转的角度，还是三星的问题，需要根据图片的旋转角度正确显示
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    @Override
    public void Getsuccess(JSONObject jsonObject, int what) {
        super.Getsuccess(jsonObject, what);
        if (jsonObject.getIntValue("msg") == 1) {
            Toast.makeText(mContext, "发送申请成功！", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            T.showShort(mContext, "发送申请失败！");
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setGridViewHeight(GridView gridView) {
        ListAdapter adapter = gridView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeigt = 0;
        double itemCount = adapter.getCount();
        double horizentalNumger = gridView.getNumColumns();
        int verticalNumber = (int) Math.ceil(itemCount / horizentalNumger);
        View item = adapter.getView(0, null, gridView);
        item.measure(0, 0);
        int itemHeight = item.getMeasuredHeight();
        int space = gridView.getVerticalSpacing();
        totalHeigt = verticalNumber * itemHeight + (verticalNumber - 1) * space;
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeigt;
        gridView.setLayoutParams(params);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalImageHelper.getInstance().getCheckedItems().clear();

        if(playing){
            //停止播放
            player.stop();
        }

    }
}
