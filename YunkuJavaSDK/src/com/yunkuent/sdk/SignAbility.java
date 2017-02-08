package com.yunkuent.sdk;

import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.Util;
import java.net.HttpURLConnection;
import java.util.*;
import org.apache.http.util.TextUtils;

/**
 * Created by Brandon on 14/12/16.
 */
abstract class SignAbility implements HostConfig {

    private final static String LOG_TAG = SignAbility.class.getSimpleName();

    protected static final String URL_API_TOKEN = OAUTH_HOST + "/oauth2/token2";

    protected String mClientSecret;
    protected String token;
    protected String refreshToken;
    protected String mClientId;
    protected String mToken;
    protected String mTokenType;
    protected boolean mIsEnt;

    /**
     * API签名,SSO签名
     *
     * @param params
     * @return
     */
    public String generateSign(HashMap<String, String> params) {
        return generateSign(params, mClientSecret);
    }

    /**
     * 根据clientsecret 签名
     *
     * @param params
     * @return
     */
    public String generateSign(HashMap<String, String> params, String secret) {
        return generateSign(params, secret, new ArrayList<String>());
    }

    /**
     * 根据clientsecret 签名 ,排除不需要签名的value
     *
     * @param params
     * @param secret
     * @param ignoreKeys
     * @return
     */
    protected String generateSign(HashMap<String, String> params, String secret, ArrayList<String> ignoreKeys) {
        ArrayList<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys, mComparator);
        int size = params.size();
        String string_sign = "";

        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                String key = keys.get(i);
                String value = params.get(key);
                if (value == null) {
                    continue;
                }

                string_sign += value + "\n";
            }
            string_sign += params.get(keys.get(size - 1));
        }
        return Util.getHmacSha1(string_sign, secret);
    }

    private void reSignParams(HashMap<String, String> params, ArrayList<String> ignoreKeys) {
        reSignParams(params, mClientSecret, true, ignoreKeys);
    }

    private Comparator<String> mComparator = new Comparator<String>() {
        public int compare(String p1, String p2) {
            return p1.compareTo(p2);
        }
    };

    /**
     * 如果身份验证有问题,会自动刷token
     *
     * @param url
     * @param method
     * @param params
     * @param headParams
     * @param ignoreKeys
     * @return
     */
    private String sendRequestWithAuth(String url, RequestMethod method,
                                       HashMap<String, String> params, HashMap<String, String> headParams, ArrayList<String> ignoreKeys) {
        String returnString = NetConnection.sendRequest(url, method, params, headParams);
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            if (returnResult.getStatusCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshToken();
                reSignParams(params, ignoreKeys);
                returnString = NetConnection.sendRequest(url, method, params, headParams);
            }
        }
        return returnString;
    }

    /**
     * 重新获得token
     */
    public boolean refreshToken() {
        if (TextUtils.isEmpty(refreshToken)) {
            return false;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        params.put("client_id", mClientId);
        params.put("sign", generateSign(params));

        String returnString = new RequestHelper().setUrl(URL_API_TOKEN).setMethod(RequestMethod.POST).setParams(params).executeSync();
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            OauthData data = OauthData.create(returnResult.getResult());
            if (data != null) {
                data.setCode(returnResult.getStatusCode());
                if (data.getCode() == HttpURLConnection.HTTP_OK) {
                    token = data.getToken();
                    refreshToken = data.getRefresh_token();
                    return true;
                }

                LogPrint.print(LOG_TAG + "token:" + token + "_refreshToken:" + refreshToken);
            }

        }
        return false;
    }

    /**
     * 重新根据参数进行签名
     *
     * @param params
     * @param secret
     * @param needEncode
     * @param ignoreKeys
     */
    protected void reSignParams(HashMap<String, String> params, String secret,
                                boolean needEncode, ArrayList<String> ignoreKeys) {
        params.remove("token");
        params.remove("sign");
        params.put("token", token);
        params.put("sign", generateSign(params, secret, ignoreKeys));
    }

    /**
     * 请求协助类
     */
    protected class RequestHelper {
        RequestMethod method;
        HashMap<String, String> params;
        HashMap<String, String> headParams;
        String url;
        boolean checkAuth;

        ArrayList<String> ignoreKeys;

        RequestHelper setMethod(RequestMethod method) {
            this.method = method;
            return this;
        }

        RequestHelper setParams(HashMap<String, String> params) {
            this.params = params;
            return this;
        }

        RequestHelper setHeadParams(HashMap<String, String> headParams) {
            this.headParams = headParams;
            return this;
        }

        RequestHelper setCheckAuth(boolean checkAuth) {
            this.checkAuth = checkAuth;
            return this;
        }

        RequestHelper setUrl(String url) {
            this.url = url;
            return this;
        }

        public RequestHelper setIgnoreKeys(ArrayList<String> ignoreKeys) {
            this.ignoreKeys = ignoreKeys;
            return this;
        }

        /**
         * 同步执行
         *
         * @return
         */
        String executeSync() {
            checkNecessaryParams(url, method);

            if (!Util.isNetworkAvailableEx()) {
                return "";
            }

            if (checkAuth) {
                return sendRequestWithAuth(url, method, params, headParams, ignoreKeys);
            }
            return NetConnection.sendRequest(url, method, params, headParams);
        }

        private void checkNecessaryParams(String url, RequestMethod method) {
            if (TextUtils.isEmpty(url)) {
                throw new IllegalArgumentException("url must not be null");
            }

            if (method == null) {
                throw new IllegalArgumentException("method must not be null");
            }
        }
    }
}