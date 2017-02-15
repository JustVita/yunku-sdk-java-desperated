package com.yunkuent.sdk;

import com.yunkuent.sdk.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Brandon on 14/12/16.
 */
abstract class SignAbility implements HostConfig {

    protected String mClientSecret;
    protected String mClientId;

    public abstract String getToken();

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
                if (value == null ) {
                    params.remove(key);
                    continue;
                }

                if(ignoreKeys.contains(key)){
                    continue;
                }
                string_sign += value + "\n";
            }
            string_sign += params.get(keys.get(size - 1));
        }
        return Util.getHmacSha1(string_sign, secret);
    }

    /**
     * 重新根据参数进行签名
     *  @param params
     * @param secret
     * @param ignoreKeys
     */
    protected void reSignParams(HashMap<String, String> params, String secret,
                                ArrayList<String> ignoreKeys) {
        params.remove("token");
        params.remove("sign");
        params.put("token", getToken());
        params.put("sign", generateSign(params, secret, ignoreKeys));
    }

    private Comparator<String> mComparator = new Comparator<String>() {
        public int compare(String p1, String p2) {
            return p1.compareTo(p2);
        }
    };
}