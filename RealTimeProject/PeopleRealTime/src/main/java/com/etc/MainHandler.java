/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MainHandler
 * Author:   臧浩鹏
 * Date:     2018/7/18 17:05
 * Description: 用于处理 格式化数据等
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.etc;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用于处理 格式化数据等〉
 *
 * @author 臧浩鹏
 * @create 2018/7/18
 * @since 1.0.0
 */
public class MainHandler {
    private static  ArrayList arr = new ArrayList();
    static {
        arr.add(0,"");
        arr.add(1,"");
        arr.add(2,"");
        arr.add(3,"");
    }
    public static ArrayList getFormat(String data) throws ParseException {
        System.out.println(data);
        String[] split = data.split("\\s+");
        String phone = split[0];
        String[] tude = split[1].split(",");
        //经度
        String longitude = tude[0];
        //维度
        String latitude = tude[1];
        //时间戳
        Long timestamp = DateUtil.getDateUtils().formatdate(split[2]+" "+split[3]);
        arr.set(0,phone);
        arr.set(1,longitude);
        arr.set(2,latitude);
        arr.set(3,timestamp);
        return arr;
    }
}
