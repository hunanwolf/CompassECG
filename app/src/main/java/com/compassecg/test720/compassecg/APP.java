package com.compassecg.test720.compassecg;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.compassecg.test720.compassecg.tooclass.domain.friend;
import com.compassecg.test720.compassecg.unitl.LocalImageHelper;
import com.compassecg.test720.compassecg.unitl.MyLocationListener;
import com.compassecg.test720.compassecg.unitl.UuidUtil;
import com.easemob.chat.EMConversation;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.test720.auxiliary.Utils.CrashHandler;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jie on 2016/l4/8.
 */
public class APP extends MultiDexApplication {
    public static String size = "";//公司规模
    public static String nature = "";//公司性质
    public static String industry = "";//行业
    public static String professional = "";//行业
    public static String Professionalid = "";//行业id
    public static String Record_formal = "";//学历
    public static String TheTitle = "";//职称
    public static String induWorking_year = "";//工作年限
    public static String zhiwei = "";//期望职位
    public static String leix = "";//期望工作类型
    public static String cxiprice = "";//期望薪资
    public static String address111 = "";//期望薪资

//    public static List<jianl> RecordTags1 = new ArrayList<>();
//    public static List<education> educationlist = new ArrayList<>();
//    public static List<projectjy> projectjylist = new ArrayList<>();

    public static String nameTags;//问题选择添加后的分类name
    public static String idallist;//问题选择添加后的分类id

    public static String joldnew = "";//几成新
    public static String header = "";//头像uri
    public static String name = "";//名字
    public static Bitmap heade = null;
    public static String headerUrl = "";
    public static String username = "";//账号
    public static String password = "";//密码
    public static String uuid = "";//uuid;
    public static String classificationid = "";//是否是专家
    public static String nickname = "";//昵称
    public static String id = "";//d当前公司id
    public static String saying = "";//签名
    public static String idlist = "";//当前
    public static String region = "";//自选地址

    public static String citycjoser = "";//人才招聘city
    //到期时间
    public static int chat1 = 0;
    public static int consultation1 = 0;
    public static int project1 = 0;
    public static int recruit1 = 0;
    public static int rental1 = 0;


    public static String skill;
    public static String self;

    public static String useridlpp = "";
    public static List<String> usernames = new ArrayList<>();
    public static List<String> list1 = new ArrayList<>();


    public BDLocationListener myListener = new MyLocationListener();
    public static LocationClient mLocationClient = null;
    public static double JINDY;//经度
    public static double WEIDU;//维度
    public static String cityName;
    public static String cityCode;


    public static List<EMConversation> listhuihua = new ArrayList<>();


    public static String baiduCity = "";
    public static BDLocation location;
//    public static List<phone> contactList = new ArrayList();//手机联系人
//    public static List<EaseUser> contactList1 = new ArrayList<>();//好友类表
    public static List<friend> friendList = new ArrayList<>();//好友类表
    //简历临时信息
    public static String namesl = "";
    public static String worlk = "";
    public static String phones = "";
    public static String email = "";
    public static String xuel = "";
    public static String sex = "";
    public static String city = "";
    public static int DaoQi;
    public static int TongZ;
    //环信
    public static Context applicationContext;
    private static APP instance;
    // login user name
    public final String PREF_USERNAME = "username";
    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";
    //微信
    public static IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        instance = this;

        /**
         * this function will initialize the HuanXin SDK
         *
         * @return boolean true if caller can continue to call HuanXin related APIs after calling onInit, otherwise false.
         *
         * 环信初始化SDK帮助函数
         * 返回true如果正确初始化，否则false，如果返回为false，请在后续的调用中不要调用任何和环信相关的代码
         *
         * for example:
         * 例子：
         *
         * public class DemoHXSDKHelper extends HXSDKHelper
         *
         * HXHelper = new DemoHXSDKHelper();
         * if(HXHelper.onInit(context)){
         *     // do HuanXin related work
         * }
         */
        //init demo helper
        DemoHelper.getInstance().init(applicationContext);

        initCrash();
        LocalImageHelper.init(this.getApplicationContext());
    }

    private void initCrash() {
        UuidUtil.getLoginInfo(getApplicationContext());
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        SDKInitializer.initialize(this);
        initBaiduLocation();

        //QQ
        PlatformConfig.setQQZone("1105829006", "qPIDMea449yQOojI");
        //微信
        PlatformConfig.setWeixin("wxfcae2dc0aabb683c", "e4b365f0035565651038cc06748a3095");

        //微信支付
        api = WXAPIFactory.createWXAPI(this, "wxfcae2dc0aabb683c", false);
        //         将APP_ID注册到微信l
        api.registerApp("wxfcae2dc0aabb683c");
    }


    private void initBaiduLocation() {
        mLocationClient = new LocationClient(getApplicationContext());  //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public static APP getInstance() {
        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
