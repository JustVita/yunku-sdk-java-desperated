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
abstract class ParentEngine extends SignAbility implements HostConfig {

    protected static final String URL_API_TOKEN = OAUTH_HOST + "/oauth2/token";

    protected String mClientId;
    protected String mToken;
    protected boolean mIsEnt;
    protected String mTokenType;

    public ParentEngine(String clientId, String clientSecret, boolean isEnt) {
        mClientId = clientId;
        mClientSecret = clientSecret;
        mIsEnt = isEnt;
        mTokenType = isEnt ? "ent" : "";
    }

    /**
     * 获取token
     *
     * @return
     */
    public String accessToken(String username, String password) {
        String url = URL_API_TOKEN;
        String method = "POST";
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", Util.convert2MD532(password)));
        params.add(new BasicNameValuePair("client_id", mClientId));
        params.add(new BasicNameValuePair("client_secret", mClientSecret));
        params.add(new BasicNameValuePair("grant_type", mIsEnt ? "ent_password" : "password"));

        String result = NetConnection.sendRequest(url, method, params, null);
        ReturnResult returnResult = ReturnResult.create(result);
        LogPrint.print("accessToken:==>result:" + result);

        if (returnResult.getStatusCode() == HttpStatus.SC_OK) {
            LogPrint.print("accessToken:==>StatusCode:200");
            OauthData data = OauthData.create(returnResult.getResult());
            mToken = data.getToken();
        }
        return result;
    }

    public String getToken() {
        return mToken;
    }

}
