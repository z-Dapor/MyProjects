/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProcessOrderBolt
 * Author:   臧浩鹏
 * Date:     2018/7/17 14:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PeopleRealTime;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * 〈一句话功能简述〉<br> 
 * 〈计算各个指标并存在Redis中,来共前台调用〉
 *
 * @author 臧浩鹏
 * @create 2018/7/17
 * @since 1.0.0
 */
public class ProcessOrderBolt extends BaseBasicBolt {
    private static final Logger log = Logger.getLogger("ProcessOrderBolt");
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        System.out.println("-------------------------------------");
        Object value = tuple.getValue(4);
        System.out.println("-------------------------------------");
        PayMentInfo payMentInfo = JSONObject.parseObject(value.toString(), PayMentInfo.class);
        Jedis conn = JedisUtil.getConn();

        //一：首先计算每个店的维度
        /**
         *1.每个店铺的总销售额 shopId
         * Redis的key设计：date:order:shopId:price
         *
         *2.每个店铺的购买人数
         * Redis的key设计：date:order:shopId:customerNum
         *
         *3.每个店铺的销售数量
         * Redis的key设计：date:order:shopId:salNum
         */
        DateFormat dateInstance = SimpleDateFormat.getDateInstance();
        String today = dateInstance.format(new Date()).replaceAll("-","");
        String shopId = payMentInfo.getShopId();
        long payPrice = payMentInfo.getPayPrice();
        int num = payMentInfo.getNum();
        conn.incrBy(today+":order:shopId"+shopId+":price",payPrice);
        conn.incrBy(today+":order:shopId"+shopId+":customerNum",1);
        conn.incrBy(today+"order:shopId"+shopId+":salNum",num);
        //平台维度的数据统计
        /**
         *
         *1. 平台的总销售额度 total
         *  Redis的key设计：date:order:total:totalSaleMoney
         *
         *2. 平台今天下单次数
         *  Redis的key设计：date:order:total:customerNum
         *
         *3. 平台商品的销售数量
         *  Redis的key设计:
         */
        conn.incrBy(today+"order:total:totalSaleMoney",payMentInfo.getPayPrice());
        conn.incrBy(today+"order:total:customerNum",1);
        conn.incrBy(today+"order:total:customerNum",1);
        /**
         * 商品的维度
         *  每个商品的总销售额
            Redis的rowKey设计date:order:productId:price
            每个商品的购买人数
            Redis的rowKey设计date:order:productId:user
            每个商品的销售数量
            Redis的rowKey设计date:order:productId:num
         */
        String productId = payMentInfo.getProductId();
        conn.incrBy(today+"order:productId"+productId+":price",payMentInfo.getPayPrice());
        conn.incrBy(today+"order:productId"+productId+":user",1);
        conn.incrBy(today+"order:productId"+productId+":num",num);

        conn.close();

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
