import Model.OauthData;
import com.yunkuent.sdk.OauthErrorMsg;
import com.yunkuent.sdk.YunkuEngine;
import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.HttpStatus;

/**
 * Created by Brandon on 2014/8/6.
 */
public class SDKTester {
    public static final String UESRNAME = "";
    public static final String PASSWORD = "";
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    private static YunkuEngine mSdk;

    public static void main(String[] args) {
        YunkuEngine.PRINT_LOG = true;
        mSdk = new YunkuEngine(UESRNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET);
        mSdk.accessToken(true);
//获取库列表
//        deserializeOauth(mSdk.getLibList());
        //获取库授权
//        deserializeOauth(mSdk.bindLib(146540,"",""));
        //取消库授权
//        deserializeOauth(mSdk.unBindLib("9affb8f78fd5914a7218d7561db6ddec"));
        //获取库中文件
//        deserializeOauth(mSdk.getFileList("9affb8f78fd5914a7218d7561db6ddec", "0af31f7a64bff5d6ed2abf0bb7da1d6b",(int)Util.getUnixDateline(), 0, ""));

        //获取更新列表
//        deserializeOauth(mSdk.getUpdateList("9affb8f78fd5914a7218d7561db6ddec", "0af31f7a64bff5d6ed2abf0bb7da1d6b", (int) Util.getUnixDateline(), false, 0));

        //获取文件(夹)信息
//        deserializeOauth(mSdk.getFileInfo("9affb8f78fd5914a7218d7561db6ddec", "0af31f7a64bff5d6ed2abf0bb7da1d6b",(int) Util.getUnixDateline(),"test"));
        //创建文件夹
//        deserializeOauth(mSdk.createFolder("9affb8f78fd5914a7218d7561db6ddec", "0af31f7a64bff5d6ed2abf0bb7da1d6b",(int) Util.getUnixDateline(),"test","Brandon"));
        //上传文件
//        deserializeOauth(mSdk.createFile("9affb8f78fd5914a7218d7561db6ddec", "0af31f7a64bff5d6ed2abf0bb7da1d6b",(int) Util.getUnixDateline(),"test","Brandon","D:\\test.txt"));
        //删除文件
//        deserializeOauth(mSdk.del("9affb8f78fd5914a7218d7561db6ddec", "0af31f7a64bff5d6ed2abf0bb7da1d6b",(int) Util.getUnixDateline(),"test","Brandon"));
        //移动文件
//        deserializeOauth(mSdk.move("9affb8f78fd5914a7218d7561db6ddec", "0af31f7a64bff5d6ed2abf0bb7da1d6b",(int) Util.getUnixDateline(),"test","1/test","Brandon"));
        //文件连接
        deserializeOauth(mSdk.link("9affb8f78fd5914a7218d7561db6ddec", "0af31f7a64bff5d6ed2abf0bb7da1d6b", (int) Util.getUnixDateline(), "1/test"));
//发送消息
        deserializeOauth(mSdk.sendmsg("9affb8f78fd5914a7218d7561db6ddec", "0af31f7a64bff5d6ed2abf0bb7da1d6b", (int) Util.getUnixDateline(), "msgTest", "msg", "", "", "Brandon"));

    }

    private static void deserializeOauth(String result) {
        //复制到剪贴板
        Util.copyToClipboard(result);

        ReturnResult returnResult = ReturnResult.create(result);
        OauthData data = OauthData.create(returnResult.getResult());
        if (data != null) {

            if (returnResult.getStatusCode() == HttpStatus.SC_OK) {
                //成功的结果
                System.out.println(mSdk.getToken());
                // System.out.println(data.getToken()); 两者方法都可以拿到

            } else {
                //解析result中的内容
                System.out.println(data.getError());

            }
        }

        System.out.println(returnResult.getResult());
    }


}

