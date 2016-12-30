package com.compassecg.test720.compassecg.UploadPicture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.unitl.BarBaseActivity;
import com.compassecg.test720.compassecg.unitl.LocalImageHelper;
import com.test720.auxiliary.Utils.L;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadPicActivity extends BarBaseActivity {

    private static final int CHOOSE_PICTURE = 101;
    private static final int TAKE_PICTURE = 102;
    private static final int BYCROP_PICTURE = 103;
    private static final int A = 999;

    /**
     * 请求相册
     */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    /**
     * 请求相机
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    /**
     * 请求裁剪
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;

    int MaxNumaber;//要求上传最大数值

    private GridView gridView;
    private Button bt;
    private AlertDialog picDialog;
    private File imageFile;
    //拍照文件名
    private String imageName;
    private List<LocalImageHelper.LocalFile> showFiles = new ArrayList<>();
    private MyAdapter adapter;
    private LocalImageHelper.LocalFile fakeImage;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic);
        setTitleString("已上传");
        MaxNumaber = getIntent().getExtras().getInt("MaxNumber");
        initViews();


        Resources r = mContext.getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.mipmap.ic_launcher) + "/"
                + r.getResourceTypeName(R.mipmap.ic_launcher) + "/"
                + r.getResourceEntryName(R.mipmap.ic_launcher));
        fakeImage = LocalImageHelper.getInstance().getFake();
        fakeImage.setOriginalUri("fake");
        fakeImage.setThumbnailUri(uri.toString());


        List<LocalImageHelper.LocalFile> items = LocalImageHelper.getInstance().getCheckedItems();
        L.e("===itemsize=", items.size() + "");
        if (items.size() < MaxNumaber) {
            showFiles.addAll(items);
            showFiles.add(fakeImage);
        } else {
            showFiles.addAll(items);
        }

        initData();


    }

    private void initData() {
        gridView.setAdapter(adapter = new MyAdapter(mContext, showFiles));
    }

    private void initViews() {
        gridView = getView(R.id.gridView);
        bt = getView(R.id.bt);
        bt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                finish();
                break;
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
        View v = View.inflate(this, R.layout.photo_popup, null);
        // 获取v对象中的控件 .setOnClickListener
        picDialog.setCanceledOnTouchOutside(true);
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lParams = window.getAttributes();
        lParams.width = (int) (display.getWidth());
        window.setAttributes(lParams);
        v.findViewById(R.id.upload_from_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChoosePicFolderActivity.class);
                intent.putExtra("MaxNumber", MaxNumaber);
                startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCROP);
                picDialog.cancel();
            }
        });
        v.findViewById(R.id.take_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePics();
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

    //
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
        switch (requestCode) {case REQUEST_CODE_GETIMAGE_BYCROP:
                if (LocalImageHelper.getInstance().isResultOk()) {
                    LocalImageHelper.getInstance().setResultOk(false);
                    //获取选中的图片
                    List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                    showFiles.clear();
                    showFiles.addAll(files);
                    if (files.size() < MaxNumaber) {
                        showFiles.add(fakeImage);
                    }
                    adapter.notifyDataSetChanged();
                    L.e("===filessize=", files.size() + "");
                }

                break;

            case A:
                Log.e("===skkkkkkkkkkk", showFiles.toString());
                if (resultCode == Activity.RESULT_OK) {
                    String cameraPath = LocalImageHelper.getInstance().getCameraImgPath();
                    if (cameraPath==null|cameraPath.equals("")) {
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
                    adapter.notifyDataSetChanged();
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
                convertView = View.inflate(m_context, R.layout.simple_list_item2, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                viewHolder.checkBox.setOnCheckedChangeListener(new Mycheck());
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


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
                        L.e("===files", files.size() + "");
                        //删除helper里选中的对应图片
                        LocalImageHelper.getInstance().getCheckedItems().remove(compoundButton.getTag());
                        L.e("===files2", LocalImageHelper.getInstance().getCheckedItems().size() + "");
                        List<LocalImageHelper.LocalFile> items = LocalImageHelper.getInstance().getCheckedItems();
//                        if (LocalImageHelper.getInstance().getCheckedItems().size()<MaxNumaber){
//                            showFiles.add(fakeImage);
//                        }
                        showFiles.clear();
                        if (items.size() < MaxNumaber) {
                            showFiles.addAll(items);
                            showFiles.add(fakeImage);
                        }
                        adapter.notifyDataSetChanged();

                    }
                }
            }
        }


    }


}
