package com.smile.utils.common.utils;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片工具类
 *
 * @author TheTouchSmile
 * @date 2020/4/22 17:25
 */
public class ImageUtil {

    /**
     * 将一张网络图片转化成Base64字符串
     *
     * @param imgURL 图片地址
     * @return Base64字符串
     */
    public String getBase64FromUrl(String imgURL) {
        InputStream inStream = null;
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inStream = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[4096];
            int len;
            while ((len = inStream.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            byte[] bytes = baos.toByteArray();
            inStream.close();
            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                inStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}
