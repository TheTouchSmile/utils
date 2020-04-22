package com.smile.utils.common.utils;

import java.util.*;

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
        List<String> keys = new ArrayList<>(params.keySet());
        // 升序排序
        Collections.sort(keys);
        StringBuilder prestr = new StringBuilder();
        // 根据升序排列的key拼接
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                prestr.append(key).append("=").append(value);
            } else {
                prestr.append(key).append("=").append(value).append("&");
            }
        }
        return prestr.toString();
    }

    /**
     * 将“参数=参数值”的模式用“&”字符拼接的字符串 分割然后以键值对存入 LinkHashMap
     *
     * @param s 字符串
     * @return LinkedHashMap
     */
    public static Map<String, String> splitStringToMap(String s) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        // 将字符串拆分成一维数组
        String[] temp = s.split("&");
        for (int i = 0; i < temp.length; i++) {
            // 继续分割并存到另一个一临时的一维数组当中
            String[] arr = temp[i].split("=");
            String value;
            if (arr.length == 1) {
                value = "";
            } else if (arr.length > 2) {
                value = temp[i].substring(arr[0].length() + 1);
            } else {
                value = arr[1];
            }
            map.put(arr[0], value);
        }
        return map;
    }

}
