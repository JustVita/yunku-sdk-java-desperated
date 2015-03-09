package com.yunkuent.sdk;

import com.google.gson.Gson;
import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * http请求
 */
final class NetConnection {

    private static final String LOG_TAG = "NetConnection";
    public static final int TIMEOUT = 30000;

    /**
     * 发送请求
     *
     * @param url
     * @param method
     * @param params
     * @param headParams
     * @return
     */
    public static String sendRequest(String url, String method,
                                     ArrayList<NameValuePair> params, ArrayList<NameValuePair> headParams) {
        LogPrint.print("sendRequest(): url is: " + url + " " + params);

        HttpClient httpclient = new DefaultHttpClient();

        try {
            KeyStore trustStore;
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT);
            HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(httpParameters, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    httpParameters, registry);
            httpclient = new DefaultHttpClient(ccm, httpParameters);

            //移除为null的数据
            if(params!=null){
                Iterator<NameValuePair> keyIterator = params.iterator();
                while (keyIterator.hasNext()) {
                    NameValuePair nameValuePair = keyIterator.next();
                    if (nameValuePair.getValue() == null) {
                        keyIterator.remove();
                    }
                }
            }

            if (method.equals("GET")) {
                url += "?";
                for (int i = 0; i < params.size(); i++) {
                    url += params.get(i).getName() + "=" + URLEncoder.encodeUTF8(params.get(i).getValue()) + ((i == params.size() - 1) ? "" : "&");
                }
            }

            HttpMethod httpmethod = new HttpMethod(url);
            httpmethod.setMethod(method);
            httpmethod.addHeader("User-Agent", System.getProperties()
                    .getProperty("http.agent") + Config.USER_AGENT);
            if (!method.equals("GET") && params != null && !params.isEmpty()) {
                LogPrint.print(
                        "sendRequest(): params is: " + params.toString());
                httpmethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            }
            if (headParams != null && !headParams.isEmpty()) {
                for (NameValuePair nameValuePair : headParams) {
                    if (nameValuePair.getValue() == null) {
                        continue;
                    }
                    httpmethod.addHeader(nameValuePair.getName(),
                            nameValuePair.getValue());
                }
            }
            HttpResponse response = httpclient.execute(httpmethod);

            String result = EntityUtils.toString(response.getEntity());
            ReturnResult returnResult = new ReturnResult(result, response.getStatusLine().getStatusCode());
            Gson gson = new Gson();
            return gson.toJson(returnResult);


        } catch (Exception e) {
            LogPrint.print(Level.WARNING, LOG_TAG + " sendRequest(): Exception is: " + e.toString());
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return "";
    }


}
