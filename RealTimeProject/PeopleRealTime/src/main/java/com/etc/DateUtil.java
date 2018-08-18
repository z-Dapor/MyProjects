/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DateUtils
 * Author:   臧浩鹏
 * Date:     2018/7/18 17:08
 * Description: 日期工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.etc;


import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈日期工具类〉
 *
 * @author 臧浩鹏
 * @create 2018/7/18
 * @since 1.0.0
 */
public class DateUtil {
    private DateUtil() {
    }
    private static DateUtil dateUtils = null;

    public static DateUtil getDateUtils() {
        if (dateUtils==null){
            dateUtils = new DateUtil();
        }
        return dateUtils;
    }
    public Long formatdate(String data) throws ParseException {
        //Object o = df.parseObject(data.substring(1, data.length() - 1))
        String[] a = new String[5];
        a[0] = "yyyy-MM-dd HH:mm:ss";
        long time = 0;
        try {
             time = DateUtils.parseDate(data.substring(1, data.length() - 1), a).getTime();
        }catch (Exception e){
            System.err.println("格式化时间报错!!!");
            return 0L;
        }
        return time;


    }
}
