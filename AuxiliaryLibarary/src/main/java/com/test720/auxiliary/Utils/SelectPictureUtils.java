package com.test720.auxiliary.Utils;

import android.content.Context;

import com.test720.auxiliary.GalleryFinal.GlideImageLoader;
import com.test720.auxiliary.GalleryFinal.GlidePauseOnScrollListener;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by jie on 2016/7/12.
 *
 * https://github.com/pengjianbo/GalleryFinal   项目地址  下面不懂的参数可看源码查看
 *
 * 下面的图片加载方式可用个人喜好去 项目地址参看 本人用的Glide
 *
 *
 */
public class SelectPictureUtils {
    private Context mContext;
    cn.finalteam.galleryfinal.ImageLoader imageLoader;
    PauseOnScrollListener pauseOnScrollListener = null;
    //接收相机回调
    private static int REQUEST_CODE_CAMERA = 1000;
    //接收相册回调
    private static int REQUEST_CODE_GALLERY = 1001;
    private static int REQUEST_CODE_CROP = 1002;
    private static int REQUEST_CODE_EDIT = 1003;



    /**
     * 初始化参数
     * */
    public SelectPictureUtils(Context mContext)
    {
        this.mContext = mContext;
        //设置主题
        ThemeConfig themeConfig = ThemeConfig.CYAN;
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        themeConfig = theme;
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(false)
                .setEnableCrop(false)
                .setEnableRotate(false)
                .setCropSquare(false)
                .setEnablePreview(true)
                .build();
        imageLoader = new GlideImageLoader();
        pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);
        CoreConfig coreConfig = new CoreConfig.Builder(mContext, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(pauseOnScrollListener)
                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
    }

    /**
     * 调用相机
     * */
    public static void CameraImage(GalleryFinal.OnHanlderResultCallback onHanlderResultCallback)
    {
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, onHanlderResultCallback);
    }
    /**
     * 单选相册
     * */
    public static void RadioAlbum(GalleryFinal.OnHanlderResultCallback onHanlderResultCallback)
    {
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, onHanlderResultCallback);
    }
    /**
     * 多选相册
     * @param count 图片选择的限制张数
     * */
    public static void MultiselectAlbum(int count,GalleryFinal.OnHanlderResultCallback onHanlderResultCallback)
    {
        //带配置
        FunctionConfig config = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(count)
                .setEnablePreview(true)
                .build();
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, onHanlderResultCallback);
    }






      /**
       * 得到返回数据监听实例
       * */
//    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
//        @Override
//        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
//            if (resultList != null) {
//            }
//        }
//
//        @Override
//        public void onHanlderFailure(int requestCode, String errorMsg) {
//            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
//        }
//    };

}
