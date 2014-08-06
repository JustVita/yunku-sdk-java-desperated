package com.yunkuent.sdk;

/**
 * Created by Brandon on 2014/8/6.
 */
public class ParentEngine {

    protected String mClientId;
    protected String mClientSecret;
    protected String mUsername;
    protected String mPassword;
    protected String mToken;
    protected String mRefreshToken;

    public static boolean PRINT_LOG = false;

    public ParentEngine(String username, String password, String clientId, String clientSecrect) {
        mUsername = username;
        mPassword = password;
        mClientId = clientId;
        mClientSecret = clientSecrect;
    }

    private ParentEngine(String username, String password, String clientId, String clientSecrect, String token, String refreshToken) {
        mUsername = username;
        mPassword = password;
        mClientId = clientId;
        mClientSecret = clientSecrect;
        mToken = token;
        mRefreshToken = refreshToken;

    }

    public String getToken() {
        return mToken;
    }

    /**
     * copy a object
     *
     * @return
     */
    public ParentEngine clone() {
        return new ParentEngine(mUsername, mPassword, mClientId, mClientSecret, mToken, mRefreshToken);
    }


}
