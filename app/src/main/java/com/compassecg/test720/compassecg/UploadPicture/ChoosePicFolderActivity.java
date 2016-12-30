package com.compassecg.test720.compassecg.UploadPicture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.compassecg.test720.compassecg.R;
import com.compassecg.test720.compassecg.UploadPicture.adapters.FolderAdapter;
import com.compassecg.test720.compassecg.unitl.LocalImageHelper;
import com.test720.auxiliary.Utils.NoBarBaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import java.util.List;
import java.util.Map;


/**
 * Created by dee on 15/11/19.
 */
public class ChoosePicFolderActivity extends NoBarBaseActivity {
    private ListView listView;
    private LocalImageHelper helper;
    public static final String LOCAL_FOLDER_NAME="local_folder_name";//跳转到相册页的文件夹名称
    private ArrayList<String> folderNames;
    private Map<String, List<LocalImageHelper.LocalFile>> folders;

     int MaxNumaber;//最大上传数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pic_folder);
        MaxNumaber=getIntent().getExtras().getInt("MaxNumber");
        initView();
    }

    public void initView() {
        listView = getView(R.id.albumListView);
        helper = LocalImageHelper.getInstance();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //开启线程初始化本地图片列表，该方法是synchronized的，因此当AppContent在初始化时，此处阻塞
                LocalImageHelper.getInstance().initImage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //初始化完毕后，显示文件夹列表
                            initAdapter();
                    }
                });
            }
        }).start();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, LocalAlbumDetailActivity.class);
                intent.putExtra("MaxNumber",MaxNumaber);
                intent.putExtra(LOCAL_FOLDER_NAME, folderNames.get(i));
                intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
            }
        });

    }

    public void initAdapter() {

        folderNames = new ArrayList<>();
        helper= LocalImageHelper.getInstance();
        folders = helper.getFolderMap();

        Iterator iter = folders.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            folderNames.add(key);
        }
        //根据文件夹内的图片数量降序显示
        Collections.sort(folderNames, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                Integer num1 = helper.getFolder(arg0).size();
                Integer num2 = helper.getFolder(arg1).size();
                return num2.compareTo(num1);
            }
        });


        listView.setAdapter(new FolderAdapter(folders,this, folderNames));
    }







}
