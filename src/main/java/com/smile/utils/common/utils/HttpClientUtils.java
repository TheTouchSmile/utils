package com.smile.utils.common.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * http发送post请求
 *
 * @author TheTouchSmile
 * @date 2019/5/8 14:39
 */
public class HttpClientUtils {

    /**
     * 利用http发送一个文件几其他参数
     *
     * @param file    需要发送的文件
     * @param httpUrl 请求地址
     * @return 响应报文
     */
    public static String sendHttpPost(String httpUrl, File file) {
        // 构建HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 构建POST请求
        HttpPost httpPost = new HttpPost(httpUrl);
        String responseContent = null;
        try {
            // 构建文件体
            FileBody fileBody = new FileBody(file, ContentType.MULTIPART_FORM_DATA, file.getName());
            HttpEntity httpEntity = MultipartEntityBuilder
                    .create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart("username", new StringBody("123456", ContentType.create("text/plain", Consts.UTF_8)))
                    .addPart("upload", fileBody).build();
            httpPost.setEntity(httpEntity);
            // 发送请求,接收响应
            HttpResponse response = client.execute(httpPost);
            httpEntity = response.getEntity();
            responseContent = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送键值对形式的参数
     *
     * @param httpUrl 请求地址
     * @param map     请求参数map集合
     * @return 响应报文
     */
    public static String sendHttpPost(String httpUrl, Map<String, Object> map) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (String key : map.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, (String) map.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(150000).setConnectTimeout(150000)
                .setConnectionRequestTimeout(150000).build();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送字符流请求
     *
     * @param httpUrl     请求地址
     * @param requestBody 请求体
     * @return 响应报文
     */
    public static String sendHttpPost(String httpUrl, String requestBody) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        // 添加请求头
        httpPost.addHeader("Content-Type", "application/json");
        StringEntity reqEntity = new StringEntity(requestBody, Charset.forName("UTF-8"));
        httpPost.setEntity(reqEntity);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(150000).setConnectTimeout(150000)
                .setConnectionRequestTimeout(150000).build();
        CloseableHttpResponse response = null;
        String responseContent = null;
        HttpClientBuilder builder = HttpClients.custom();
        try {
            // 创建默认的httpClient实例.
            if (httpUrl.startsWith("https://")) {
                SSLConnectionSocketFactory sslConnectionSocketFactory = mySSLConnectionSocketFactory();
                builder.setSSLSocketFactory(sslConnectionSocketFactory);
            }
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = builder.build().execute(httpPost);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                builder.build().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 绕过https证书认证实现访问。
     *
     * @return mySSLConnectionSocketFactory
     * @throws KeyManagementException   KeyManagementException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    private static SSLConnectionSocketFactory mySSLConnectionSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {}

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sslContext.init(null, new TrustManager[]{tm}, null);
        return new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    }
}
