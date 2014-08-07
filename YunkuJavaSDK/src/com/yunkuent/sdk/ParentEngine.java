package com.yunkuent.sdk;

import com.yunkuent.sdk.utils.URLEncoder;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Brandon on 2014/8/6.
 */
class ParentEngine {

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

    /**
     * 签名
     *
     * @param array
     * @return
     */
    protected String generateSign(String[] array) {
        String string_sign = "";
        for (int i = 0; i < array.length; i++) {
            string_sign += array[i] + (i == array.length - 1 ? "" : "\n");
        }

        return URLEncoder.encodeUTF8(Util.getHmacSha1(string_sign, mClientSecret));
    }

    protected String generateSign(String[] array,String orgClientSecret){
        String string_sign = "";
        for (int i = 0; i < array.length; i++) {
            string_sign += array[i] + (i == array.length - 1 ? "" : "\n");
        }

        return URLEncoder.encodeUTF8(Util.getHmacSha1(string_sign, orgClientSecret));
    }

    protected String[] paramSorted(ArrayList<NameValuePair> params) {
        if (params != null) {
            SortedSet<KeyValuePair> sortedSet = new TreeSet<KeyValuePair>();
            for (NameValuePair nameValuePair : params) {
                sortedSet.add(new KeyValuePair(nameValuePair.getName(), nameValuePair.getValue()));
            }
            String[] arr = new String[sortedSet.size()];
            int i = 0;
            for (KeyValuePair pair : sortedSet) {
                arr[i] = pair.value;
                i++;
            }
            return arr;

        }
        return new String[0];
    }

    class KeyValuePair implements Comparable<KeyValuePair> {
        String key, value;

        public KeyValuePair(String key, String value) {
            super();
            this.key = key;
            this.value = value;
        }

        public int compareTo(KeyValuePair o) {
            return key.compareTo(o.key);
        }
    }


}
