package com.yunkuent.sdk;

import com.yunkuent.sdk.utils.URLEncoder;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Brandon on 14/12/16.
 */
abstract class SignAbility {

    protected String mClientSecret;

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

    protected String[] paramSorted(ArrayList<NameValuePair> params) {
        if (params != null) {
            SortedSet<KeyValuePair> sortedSet = new TreeSet<KeyValuePair>();
            for (NameValuePair nameValuePair : params) {
                if (nameValuePair.getValue() == null) {
                    continue;
                }
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
