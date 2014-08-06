package com.yunkuent.sdk;

import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Brandon on 2014/8/6.
 */
public class YunkuEngine extends ParentEngine {
    private static final String DEFAULT_PATH = "YunKuExtApi\\Team\\";
    private static final String HOST = "http://a.goukuai.cn";
    private static final String URL_API_TOKEN = HOST + "/oauth2/token";


    public YunkuEngine(String username, String password, String clientId, String clientSecrect) {
        super(username, password, clientId, clientSecrect);
        LogPrint.print("test");
    }

    /**
     * 获取token
     *
     * @return
     */
    public String accessToken() {

        String method = "POST";
        String url = URL_API_TOKEN;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", mUsername));
        params.add(new BasicNameValuePair("password", Util.convert2MD532(mPassword)));
        params.add(new BasicNameValuePair("client_id", mClientId));
        params.add(new BasicNameValuePair("client_secret", mClientSecret));
        params.add(new BasicNameValuePair("grant_type", "password"));

        String result = NetConnection.sendRequest(url, method, params, null);
        ReturnResult returnResult = ReturnResult.create(result);
        LogPrint.print("accessToken:==>result:" + result);

        if (returnResult.getStatusCode() == HttpStatus.SC_OK) {
            LogPrint.print("accessToken:==>StatusCode:200" );
            OauthData data = OauthData.create(returnResult.getResult());
            mToken = data.getToken();
            mRefreshToken = data.getRefresh_token();
        }
        return result;
    }



}
