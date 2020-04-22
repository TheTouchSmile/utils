package com.smile.utils.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Map工具类
 *
 * @author TheTouchSmile
 * @date 2020/4/22 15:13
 */
public class MapUtil {

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        // 获取map中的所有key
        List<String> keys = new ArrayList<String>(params.keySet());
        // 升序排序
        Collections.sort(keys);
        String prestr = "";
        // 根据升序排列的key拼接
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
}
