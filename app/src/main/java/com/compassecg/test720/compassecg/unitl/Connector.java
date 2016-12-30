package com.compassecg.test720.compassecg.unitl;

/**
 * Created by anim on 2016/6/l1.
 */
public class Connector {
    int cityid = 0;

    public static String hedaer = "http://120.26.141.238/syqapp/index.php/home/index/getHeaderImg/user_account/";
    public static String lll = "http://120.26.141.238/syqapp/";
    //接口网址
    public static String staticUrl = "http://120.26.141.238/syqapp/index.php/home/";
    public static String Link = staticUrl + "Link/";
//    public static String check_index=staticUrl+"check/"
    public static String GetPetroleum = Link + "GetPetroleum";//石油
    public static String unpay = "http://120.26.141.238/syqapp/index.php/home/Pay/unionpay/";//银联
    public static String weiXinPay = "http://120.26.141.238/syqapp/index.php/home/Pay/weiXinPay/";//微信
    //登录       http://120.26.141.238/fenbao_test/index.php/seller/login
    public static String login = staticUrl + "login/";
    public static String launch = login + "launch";//启动页
    public static String logins = "http://120.26.141.238/syqapp/index.php/home/login/logins/";//登录
    public static String search = login + "search";//搜索
    public static String theheadlines = login + "theheadlines";//石油头条
    public static String collection = login + "collection"; //个人收藏
    public static String click = login + "click"; //点击量接口
    public static String index_login = login + "index";//首页
    public static String Index = staticUrl + "Index/";//
    public static String datal = Index + "data";
    public static String createOrder = Index + "createOrder";//购买
    public static String indexSearch = Index + "indexSearch";//搜索
    public static String returnNickname = Index + "returnNickname";//环信获取昵称
    public static String searchUser = Index + "searchUser";//搜索用户回答问题

    public static String report = Index + "report";//举报用户
    public static String setRemark = Index + "setRemark";//修改备注
    //环信
    public static String Huanxin = staticUrl + "Huanxin/";
    public static String sendSms = Huanxin + "sendSms";
    public static String getsearch = Huanxin + "search";
    public static String newApply = Huanxin + "newApply";
    public static String getInfo = Huanxin + "getInfo";
    public static String bind = Huanxin + "bind";
    public static String thirdParty = Huanxin + "thirdParty";
    //邀请回答问题
    public static String inviteAnswer = Huanxin + "inviteAnswer";
    public static String invite = Huanxin + "invite";
    //注册
    public static String Register = staticUrl + "Register/";
    public static String code = Register + "code";//验证码
    public static String zhuce_code = Register + "zhuce_code";//注册code
    public static String codelbang = Register + "code";//绑定code
    public static String indexlogin = Register + "index";//注册
    public static String purchasr = Register + "purchase";//购买
    public static String back = Register + "back";//忘记密码
    public static String packages = Register + "packages";//购买套餐
    public static String getCode = Huanxin + "getCode";//二维码
    //Rental

    public static String Rental = staticUrl + "Rental/";
    public static String index = Rental + "index";  //二手租售
    public static String detailed = Rental + "detailed";   //我要和有设备详情接口
    public static String classified = Rental + "classified";//类别排序
    public static String prices = Rental + "prices";//价格排序多少。。。之上
    public static String priceis = Rental + "priceis";//价格排序 多少之间
    public static String theheight = Rental + "theheight";//价格最高
    public static String minimum = Rental + "minimum";//价格最低
    public static String newest = Rental + "newest";//最新、最旧

    //Recruit
    public static String friendList = Huanxin + "friendList";//好友列表
    public static String addFriend = Huanxin + "addFriend";//添加好友
    public static String delFriend = Huanxin + "delFriend";//删除好友
    public static String contacts = Huanxin + "contacts";//石油


    public static String Recruit = staticUrl + "Recruit/";
    public static String personnel = Recruit + "recruitList";//人才招聘接口
    public static String detaileds = Recruit + "detaileds";
    public static String company = Recruit + "company";//人才招聘公司详情
    public static String positions = Recruit + "positions";//liebiao
    public static String throwresume = Recruit + "throwresume";//人才招聘投递简历接口
    public static String positiontype = Recruit + "positiontype";//分类排序
    public static String experiences = Recruit + "experiences";//经验排序
    public static String monthlys = Recruit + "monthlys";//月薪
    public static String graduate = Recruit + "graduate";//工作性质
    public static String educations = Recruit + "educations";//学历
    public static String timeis = Recruit + "timeis";//时间
    public static String addressi = Recruit + "addressi";//地点

    //项目接口
    public static String Project = staticUrl + "Project/";//项目接口我有和我要
    public static String index_project = Project + "index";//list
    public static String dledproject = Project + "dledproject";//项目详情
    public static String highesti = Project + "highesti";//价格最高   minimumi
    public static String minimumi = Project + "minimumi";//时间最新   sortorder   sortorder
    public static String theold = Project + "theold";//时间最旧
    public static String thenew = Project + "thenew";
    public static String sortorder = Project + "sortorder";//类型排序

    //问答接口
    public static String Problem = staticUrl + "Problem/";
    public static String index_problem = Problem + "index";//问答接口
    public static String detailsprm = Problem + "detailsprm2";//问答详情
    public static String mynewsl = Problem + "myNews";//问答详情
    public static String answerdts = Problem + "answerdts";//回答详情
    public static String anwcmt = Problem + "anwcmt";//用户评论和回答的list
    public static String modify = Problem + "modify";//用户评论删除和修改的接口
    public static String experts = Problem + "experts";//专家
    public static String expertlist = Problem + "expertlist";//分类专家
    public static String expertsis = Problem + "expertsis";//专家详情
    public static String ZAN = Problem + "zan";//点赞
    public static String subAbopt = Problem + "subAbopt";//采纳

    //Personal
    public static String Personal = staticUrl + "Personal/";
    public static String index_Personal = Personal + "index";//个人中心，个人资料
    public static String modifys = Personal + "modifys";//修改资料
    public static String password = Personal + "password";//修改密码
    public static String mycollection = Personal + "mycollection";//个人中心收藏接口
    public static String purchase = Personal + "purchase";//购买记录
    public static String myanswer1 = Personal + "myanswer";//我的采纳
    public static String myrelease = Personal + "myrelease"; //个人中心我的发布
    public static String myanswer = Personal + "myanswer";//我的回答
    public static String myrelease_Personal = Personal + "myrelease";//我的发布
    public static String myRelease = staticUrl + "Myrelease/";
    public static String delResume = myRelease + "delResume";//删除简历
    public static String editResume = myRelease + "editResume";//修改简历
    public static String getMyContent = Personal + "getMyContent";
    public static String myresumexx = Personal + "myresumexx";//我的简历、详情
    public static String myresume = Personal + "myresume";//我的简历
    public static String mythe = Personal + "mythe";//我的页面信息
    public static String birthday = Personal + "birthday";//生日修改
    public static String feedback = Personal + "feedback";//意见反馈
    public static String authentication = Personal + "authentication";//企业认证
    public static String expertis = Personal + "expertis";//专家认证
    public static String mynews = Personal + "mynews";//我的消息

    //Classification
    public static String Classification = staticUrl + "Classification/";
    public static String index_calssification = Classification + "index";//设备分类
    public static String projectis = Classification + "projectis";//项目发布分类
    public static String province = Classification + "province";//获取省分类
    public static String city = Classification + "city";//获取市分类
    public static String putquestionsto = Classification + "putquestionsto";//问题分类
    public static String recruitis = Classification + "recruitis";//招聘分类
    public static String expert = Classification + "expert";//分类
    public static String deletes = Classification + "deletes";//删除
    public static String city_region = "http://120.26.141.238/taozhi/index.php/home/user/getAllCity";//省市区
    //Myrelease
    public static String Myrelease = staticUrl + "Myrelease/";
    public static String index_Myrelease = Myrelease + "index";//设备发布
    public static String hairitems_myrelease = Myrelease + "hairitems";//发布项目和服务
    public static String resume = Myrelease + "resume";//发布简历
    public static String putquestionsto_Personal = Myrelease + "putquestionsto";//发布题问

    //Headlines
    public static String Headlines = staticUrl + "Headlines/";
    public static String updates = Headlines + "updates";
    public static String Headlinesindex = Headlines + "index";
    public static String carousel = Headlines + "carousel";


}
