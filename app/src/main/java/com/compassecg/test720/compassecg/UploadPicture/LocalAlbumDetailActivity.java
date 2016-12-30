package com.compassecg.test720.compassecg.UploadPicture;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import com.test720.auxiliary.Utils.ActivityUtil;

import java.util.List;


/**
 * @author linjizong
 * @Description:欢迎页测试
 * @date 2015-l4-11
 */
public class LocalAlbumDetailActivity extends BarBaseActivity implements View.OnClickListener
        , CompoundButton.OnCheckedChangeListener {

    GridView gridView;
    //View pagerContainer;//图片显示部分
    String folder;
    List<LocalImageHelper.LocalFile> currentFolder = null;
    LocalImageHelper helper = LocalImageHelper.getInstance();
    List<LocalImageHelper.LocalFile> checkedItems;
    List<LocalImageHelper.LocalFile> checkedItemflase;
    Button button;

    int  MaxNumber;//最大上传数
    public static final String LOCAL_FOLDER_NAME="local_folder_name";//跳转到相册页的文件夹名称
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_album_detail);
        setTitleString(getIntent().getStringExtra(LOCAL_FOLDER_NAME));
        MaxNumber=getIntent().getExtras().getInt("MaxNumber");
        if (!LocalImageHelper.getInstance().isInited()) {
            finish();
            return;
        }
        gridView = (GridView) findViewById(R.id.gridview);
       // pagerContainer = findViewById(R.id.pagerview);
        button = getView(R.id.bt);
        button.setOnClickListener(this);
        folder = getIntent().getExtras().getString(LOCAL_FOLDER_NAME);


        new Thread(new Runnable() {
            @Override
            public void run() {
                //防止停留在本界面时切换到桌面，导致应用被回收，图片数组被清空，在此处做一个初始化处理
                helper.initImage();
                //获取该文件夹下地所有文件
                final List<LocalImageHelper.LocalFile> folders = helper.getFolder(folder);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (folders != null) {
                            currentFolder = folders;
                            MyAdapter adapter = new MyAdapter(mContext, folders);
                            gridView.setAdapter(adapter);
                            //设置当前选中数量
                            switch (MaxNumber){
                                case 2:
                                    if (checkedItemflase.size() + LocalImageHelper.getInstance().getCurrentSize() > 0) {
                                        button.setText("完成(" + checkedItemflase.size()
                                        /*+ LocalImageHelper.getInstance().getCurrentSize()) */
                                                + "/"+MaxNumber+")");
                                        button.setEnabled(true);
                                    } else {
                                    }
                                    break;
                                case 9:
                                    if (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize() > 0) {
                                        button.setText("完成(" + checkedItems.size()
                                        /*+ LocalImageHelper.getInstance().getCurrentSize()) */
                                                + "/"+MaxNumber+")");
                                        button.setEnabled(true);
                                    } else {
                                    }
                                    break;
                                case 4:
                                    if (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize() > 0) {
                                        button.setText("完成(" + checkedItems.size()
                                        /*+ LocalImageHelper.getInstance().getCurrentSize()) */
                                                + "/"+MaxNumber+")");
                                        button.setEnabled(true);
                                    } else {
                                    }
                                    break;
                                case 3:
                                    if (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize() > 0) {
                                        button.setText("完成(" + checkedItems.size()
                                        /*+ LocalImageHelper.getInstance().getCurrentSize()) */
                                                + "/"+MaxNumber+")");
                                        button.setEnabled(true);
                                    } else {
                                    }
                                    break;
                            }

                        }
                    }
                });
            }
        }).start();
        switch (MaxNumber){
            case 2:
                checkedItemflase = helper.getCheckedItemflase();
                break;

            case 9:
                checkedItems = helper.getCheckedItems();
                break;

            case  4:
                checkedItems = helper.getCheckedItems();
                break;

            case  3:
                checkedItems = helper.getCheckedItems();
                break;
        }

        LocalImageHelper.getInstance().setResultOk(false);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt:
                switch (MaxNumber){
                    case  2:

                        LocalImageHelper.getInstance().setCurrentSize(checkedItemflase.size());
                        LocalImageHelper.getInstance().setResultOk(true);
                        finish();
                        ActivityUtil.finishActivity(ChoosePicFolderActivity.class);
                        break;
                    case  4:

                        LocalImageHelper.getInstance().setCurrentSize(checkedItems.size());
                        LocalImageHelper.getInstance().setResultOk(true);
                        finish();
                        ActivityUtil.finishActivity(ChoosePicFolderActivity.class);
                        break;

                    case  9:
                        LocalImageHelper.getInstance().setCurrentSize(checkedItems.size());
                        LocalImageHelper.getInstance().setResultOk(true);
                        finish();
                        ActivityUtil.finishActivity(ChoosePicFolderActivity.class);

                        break;
                    case  3:
                        LocalImageHelper.getInstance().setCurrentSize(checkedItems.size());
                        LocalImageHelper.getInstance().setResultOk(true);
                        finish();
                        ActivityUtil.finishActivity(ChoosePicFolderActivity.class);

                        break;
                }

                break;
        }
    }



    public class MyAdapter extends BaseAdapter {
        private Context m_context;
        List<LocalImageHelper.LocalFile> paths;

        public MyAdapter(Context context, List<LocalImageHelper.LocalFile> paths) {
            m_context = context;
            this.paths = paths;
        }

        @Override
        public int getCount() {
            return paths.size();
        }

        @Override
        public LocalImageHelper.LocalFile getItem(int i) {
            return paths.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;

            if (convertView == null || convertView.getTag() == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(m_context, R.layout.simple_list_item, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                viewHolder.checkBox.setOnCheckedChangeListener(LocalAlbumDetailActivity.this);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            LocalImageHelper.LocalFile localFile = paths.get(i);
            Glide.with(mContext).load(localFile.getThumbnailUri()).into(viewHolder.imageView);

            viewHolder.checkBox.setTag(localFile);

            switch (MaxNumber){
                case 2:
                    viewHolder.checkBox.setChecked(checkedItemflase.contains(localFile));
                    break;

                case 4:
                    viewHolder.checkBox.setChecked(checkedItems.contains(localFile));
                    break;


                case 9:
                    viewHolder.checkBox.setChecked(checkedItems.contains(localFile));
                    break;
                case 3:
                    viewHolder.checkBox.setChecked(checkedItems.contains(localFile));
                    break;
            }

            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo  showViewPager(i);
//                    ShowToast("图片点击放大");
                }
            });
            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


        switch (MaxNumber){
            case 2:
                if (!b) {
                    if (checkedItemflase.contains(compoundButton.getTag())) {
                        checkedItemflase.remove(compoundButton.getTag());
                    }
                } else {
                    if (!checkedItemflase.contains(compoundButton.getTag())) {
                        if(checkedItemflase.size()/*+LocalImageHelper.getInstance().getCurrentSize()*/>=MaxNumber){
                            Toast.makeText(this,"最多选择"+MaxNumber+"张图片",Toast.LENGTH_SHORT).show();
                            compoundButton.setChecked(false);
                            return;
                        }
                        checkedItemflase.add((LocalImageHelper.LocalFile) compoundButton.getTag());
                    }
                }
                if (checkedItemflase.size()+ LocalImageHelper.getInstance().getCurrentSize()> 0) {
                    button.setText("完成(" +checkedItemflase.size()
                    /*+LocalImageHelper.getInstance().getCurrentSize())*/
                            + "/"+MaxNumber+")");
                    button.setEnabled(true);
                } else {
                    button.setText("完成");
                    button.setEnabled(false);
                }
                break;

            case 4:
                if (!b) {
                    if (checkedItems.contains(compoundButton.getTag())) {
                        checkedItems.remove(compoundButton.getTag());
                    }
                } else {
                    if (!checkedItems.contains(compoundButton.getTag())) {
                        if(checkedItems.size()/*+LocalImageHelper.getInstance().getCurrentSize()*/>=MaxNumber){
                            Toast.makeText(this,"最多选择"+MaxNumber+"张图片",Toast.LENGTH_SHORT).show();
                            compoundButton.setChecked(false);
                            return;
                        }
                        checkedItems.add((LocalImageHelper.LocalFile) compoundButton.getTag());
                    }
                }
                if (checkedItems.size()+ LocalImageHelper.getInstance().getCurrentSize()> 0) {
                    button.setText("完成(" +checkedItems.size()
                    /*+LocalImageHelper.getInstance().getCurrentSize())*/
                            + "/"+MaxNumber+")");
                    button.setEnabled(true);
                } else {
                    button.setText("完成");
                    button.setEnabled(false);
                }
                break;

            case 9:
                if (!b) {
                    if (checkedItems.contains(compoundButton.getTag())) {
                        checkedItems.remove(compoundButton.getTag());
                    }
                } else {
                    if (!checkedItems.contains(compoundButton.getTag())) {
                        if(checkedItems.size()/*+LocalImageHelper.getInstance().getCurrentSize()*/>=MaxNumber){
                            Toast.makeText(this,"最多选择"+MaxNumber+"张图片",Toast.LENGTH_SHORT).show();
                            compoundButton.setChecked(false);
                            return;
                        }
                        checkedItems.add((LocalImageHelper.LocalFile) compoundButton.getTag());
                    }
                }
                if (checkedItems.size()+ LocalImageHelper.getInstance().getCurrentSize()> 0) {
                    button.setText("完成(" +checkedItems.size()
                    /*+LocalImageHelper.getInstance().getCurrentSize())*/
                            + "/"+MaxNumber+")");
                    button.setEnabled(true);
                } else {
                    button.setText("完成");
                    button.setEnabled(false);
                }
                break;
            case 3:
                if (!b) {
                    if (checkedItems.contains(compoundButton.getTag())) {
                        checkedItems.remove(compoundButton.getTag());
                    }
                } else {
                    if (!checkedItems.contains(compoundButton.getTag())) {
                        if(checkedItems.size()/*+LocalImageHelper.getInstance().getCurrentSize()*/>=MaxNumber){
                            Toast.makeText(this,"最多选择"+MaxNumber+"张图片",Toast.LENGTH_SHORT).show();
                            compoundButton.setChecked(false);
                            return;
                        }
                        checkedItems.add((LocalImageHelper.LocalFile) compoundButton.getTag());
                    }
                }
                if (checkedItems.size()+ LocalImageHelper.getInstance().getCurrentSize()> 0) {
                    button.setText("完成(" +checkedItems.size()
                    /*+LocalImageHelper.getInstance().getCurrentSize())*/
                            + "/"+MaxNumber+")");
                    button.setEnabled(true);
                } else {
                    button.setText("完成");
                    button.setEnabled(false);
                }
                break;
        }


    }

}