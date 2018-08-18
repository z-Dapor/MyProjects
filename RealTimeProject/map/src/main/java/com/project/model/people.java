/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: people
 * Author:   臧浩鹏
 * Date:     2018/7/19 14:42
 * Description: 有关people的时间戳 经纬度信息
 * History:
 * <author>          <count>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.project.model;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈有关people的时间戳 经纬度信息〉
 *
 * @author 臧浩鹏
 * @create 2018/7/19
 * @since 1.0.0
 */

public class people implements Serializable{

    private static final long serialVersionUID = 990105222195877767L;

    private long count;
    private double lng;
    private double lat;

    public people() {
    }

    public people(long count, double lng, double lat) {
        this.count = count;
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "people{" +
                "count=" + count +
                ", lng=" + lng +
                ", lat=" + lat +
                '}';
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
