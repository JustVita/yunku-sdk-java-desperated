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
    public static final String UESRNAME = "412635195@qq.com";
    public static final String PASSWORD = "bozaihoho";
    public static final String CLIENT_ID = "4379c43c790bdc46788feb13e41973db";
    public static final String CLIENT_SECRET = "23413f4422a238d0a7a47f8a2fed60f9";

    private static YunkuEngine mSdk;

    public static void main(String[] args) {
        YunkuEngine.PRINT_LOG = true;
        mSdk = new YunkuEngine(UESRNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET);
        deserializeOauth(mSdk.accessToken());
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
                System.out.println(OauthErrorMsg.convertMsg(data.getError()));

            }
        }

        System.out.println(returnResult.getResult());
    }


}

