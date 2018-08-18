/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PayMentInfo
 * Author:   臧浩鹏
 * Date:     2018/7/17 12:47
 * Description: 支付详情
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PeopleRealTime;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br> 
 * 〈支付详情〉
 *
 * @author 臧浩鹏
 * @create 2018/7/17
 * @since 1.0.0
 */
public class PayMentInfo implements Serializable{

    private static final long serialVersionUID = 6121959443475280628L;

    private String orderId;
    //订单编号
    private Date createOrderTime;
    //订单创建时间
    /**
     *
     * 支付编号

     *
     */
    private String paymentId;
    /**
     *
     * 支付时间
     *
     */
    private Date paymentTime;

    private String productId;
    //商品编号
    private String productName;
    //商品名称
    private long productPrice;
    //商品价格
    private long promotionPrice;
    //促销价格
    /**
     *
     * 商铺编号
     *
     */
    private String shopId;

    private String shopName;
    //商铺名称
    /**
     *
     * 店铺电话
     *
     */
    private String shopMobile;

    private long payPrice;
    //订单支付价格
    /**
     *
     * 订单数量
     *
     */
    private int num;

    /**省 市 县
     * <Province>19</Province>
     * <City>1657</City>
     * <County>4076</County>
     */
    private String province;
    private String city;
    private String county;
    /**
     *
     * 分类
     *
     */
    private String catagorys;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateOrderTime() {
        return createOrderTime;
    }

    public void setCreateOrderTime(Date createOrderTime) {
        this.createOrderTime = createOrderTime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopMobile() {
        return shopMobile;
    }

    public void setShopMobile(String shopMobile) {
        this.shopMobile = shopMobile;
    }

    public long getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(long payPrice) {
        this.payPrice = payPrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public PayMentInfo() {
    }

    public PayMentInfo(String orderId, Date createOrderTime, String paymentId, Date paymentTime, String productId, String productName, long productPrice, long promotionPrice, String shopId, String shopName, String shopMobile, long payPrice, int num, String province, String city, String county) {
        this.orderId = orderId;
        this.createOrderTime = createOrderTime;
        this.paymentId = paymentId;
        this.paymentTime = paymentTime;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.promotionPrice = promotionPrice;
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopMobile = shopMobile;
        this.payPrice = payPrice;
        this.num = num;
        this.province = province;
        this.city = city;
        this.county = county;
    }

    @Override
    public String toString() {
        return "PayMentInfo{" +
                "orderId='" + orderId + '\'' +
                ", createOrderTime=" + createOrderTime +
                ", paymentId='" + paymentId + '\'' +
                ", paymentTime=" + paymentTime +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", promotionPrice=" + promotionPrice +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopMobile='" + shopMobile + '\'' +
                ", payPrice=" + payPrice +
                ", num=" + num +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", catagorys='" + catagorys + '\'' +
                '}';
    }

    public String random(){
        this.orderId = UUID.randomUUID().toString().replaceAll("-","");
        this.paymentId = UUID.randomUUID().toString().replaceAll("-","");
        this.productPrice = new Random().nextInt(1000);
        this.promotionPrice = new Random().nextInt(500);
        this.payPrice = new Random().nextInt(480);
        this.shopId = new Random().nextInt(200000)+"";
        this.num = new Random().nextInt(10);
        this.catagorys = new Random().nextInt(10000)+","+new Random().nextInt(10000)+","+new Random().nextInt(10000);
        this.province = new Random().nextInt(23)+"";
        this.city = new Random().nextInt(265)+"";
        this.county = new Random().nextInt(1489)+"";
        Date date = new Date();
        this.createOrderTime = date;
        String s = JSON.toJSONString(this);

        return  s;
    }

    public String getCatagorys() {
        return catagorys;
    }

    public void setCatagorys(String catagorys) {
        this.catagorys = catagorys;
    }
}
