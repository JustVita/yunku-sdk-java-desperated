import Model.BaseData;
import com.yunkuent.sdk.*;
import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.Util;

import java.net.HttpURLConnection;
import java.util.HashMap;

public class Example {

    private static EntLibManager mEntLibManager;
    private static EntManager mEntManager;
    private static EntFileManager mFileManager;
    private static ThirdPartyManager mThirdPartyManager;

    public static void main(String[] args) {
        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        entLibExample();
//        entFileExample();
//            thirdPartyExample();
    }

    /**
     * 企业文件库操作
     */
    private static void entLibExample() {
        mEntLibManager = new EntLibManager(OauthConfig.CLIENT_ID, OauthConfig.CLIENT_SECRET);

        //获取文件库列表
        deserializeReturn(mEntLibManager.getLibList());

        //获取文件库信息
//        deserializeReturn(mEntLibManager.getInfo(455161));

        //获取文件库授权, 获取orgClientId和orgClientSecret用于文件操作
//        deserializeReturn(mEntLibManager.bind(455161,null,null));

        //取消文件库授权
//        deserializeReturn(mEntLibManager.unBind("FAILqPvfj2yqZXQM6vEcf0EfU"));

    }

    /**
     * 对某个文件库进行操作
     */
    private static void entFileExample() {
        // orgClientId orgClientSecret 可以通过 EntLibManager 中 bind 获取库授权获取, 或从管理后台中的文件库"授权"获取
        String orgClientId = "";
        String orgClientSecret = "";

        mFileManager = new EntFileManager(orgClientId, orgClientSecret);

        //获取库中文件
        deserializeReturn(mFileManager.getFileList());

        //通过文件唯一标识获取下载地址
//        deserializeReturn(mFileManager.getDownloadUrlByHash("",false,EntFileManager.NetType.DEFAULT));

        //通过文件路径获取下载地址
//        deserializeReturn(mFileManager.getDownloadUrlByFullPath("",false, EntFileManager.NetType.DEFAULT));

        //获取文件(夹)信息, 返回结果中的"preview"即是预览URL地址
        deserializeReturn(mFileManager.getFileInfo("StoneBridgeMoonrise.jpg", EntFileManager.NetType.DEFAULT));
    }

    /**
     * 单点登录
     */

    private static void thirdPartyExample(){

        String outId = "";

        mThirdPartyManager = new ThirdPartyManager(OauthConfig.CLIENT_ID, OauthConfig.CLIENT_SECRET, outId);

        //开通企业
//          deserializeReturn(mThirdPartyManager.createEnt("yunku","yunku","","",""));

        //扩展参数
//        HashMap<String, String> map = new HashMap<>();
//        map.put("__setting_site_url","aaa");
//        deserializeReturn(mThirdPartyManager.createEnt(map,"yunkuTest","yunkuTest","","",""));

        //获取企业信息
//        deserializeReturn(mThirdPartyManager.getEntInfo());

        //购买
//        deserializeReturn(mThirdPartyManager.subscribe("subscribe",-1,1,12));

        //升级
//        deserializeReturn(mThirdPartyManager.upgrade("upgrade",-1,1));

        //续费
//        deserializeReturn(mThirdPartyManager.renew("renew",12));

        //退订
//        deserializeReturn(mThirdPartyManager.unsubscribe("unsubscribe"));

        //获取企业token
//        deserializeReturn(mThirdPartyManager.getEntToken());

        //获取单点登录地址
//        deserializeReturn(mThirdPartyManager.getSsoUrl(""));

    }
    /**
     * 解析返回内容
     *
     * @param result
     */
    private static void deserializeReturn(String result) {

        //解析结果
        ReturnResult returnResult = ReturnResult.create(result);

        if (returnResult.getStatusCode() == HttpURLConnection.HTTP_OK) {
            //成功的结果
            System.out.println("return 200");

        } else {
            //解析result中的内容
            BaseData data = BaseData.create(returnResult.getResult());
            if (data != null) {
                //如果可解析，则返回错误信息和错误号
                System.out.println(data.getErrorCode() + ":" + data.getErrorMsg());
            }
        }
        System.out.println(returnResult.getResult());
        //复制到剪贴板
        Util.copyToClipboard(returnResult.getResult());
    }


}

