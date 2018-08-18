/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JedisUtil
 * Author:   臧浩鹏
 * Date:     2018/7/17 14:02
 * Description: Redis工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PeopleRealTime;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.logging.Logger;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Redis工具〉
 *
 * @author 臧浩鹏
 * @create 2018/7/17
 * @since 1.0.0
 */
public class JedisUtil {
    private static final Logger log = Logger.getLogger("JedisUtil");
    private  static JedisPool pool = null;
    /**
     *
     * @Description: 获取连接池并设置连接池参数
     *
     * @auther: 臧浩鹏
     * @date: 14:12 2018/7/17
     * @param: []
     * @return: redis.clients.jedis.JedisPool
     *
     */
    private static JedisPool getPool() {

        if (pool==null){
            //创建连接池配置
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            //最大连接数
            poolConfig.setMaxTotal(20);
            //最大空闲数
            poolConfig.setMaxIdle(5);
            //创建redis连接池
            // 使用jedisPool的时候，timeout一定要给出来，如果不给，redis很大概率会报错，超时
             pool = new JedisPool(poolConfig,"192.168.5.48",6379,3000);

        }
        return pool;
    }
    /**
     *
     * @Description: 获取jedis连接
     *
     * @auther: 臧浩鹏
     * @date: 14:14 2018/7/17
     * @param: []
     * @return: redis.clients.jedis.Jedis
     *
     */
    public static Jedis getConn(){
        return getPool().getResource();
    }
    /**
     *
     * @Description: 测试jedis
     *
     * @auther: 臧浩鹏
     * @date: 14:14 2018/7/17
     * @param: [args]
     * @return: void
     *
     */
    public static void main(String[] args) {
        /*Jedis client = JedisUtil.getConn();
        *//*client.append("first","yeah");
        client.scard("two");
        client.incrBy("two",6);
        String two = client.get("first");
        System.out.println(two);*//*

        DateFormat dateInstance = SimpleDateFormat.getDateInstance();
        String today = dateInstance.format(new Date()).replaceAll("-","");
        client.incrBy(today+":order:shopId"+1000+":price:",60);
        client.close();*/
        /*PayMentInfo payMentInfo = new PayMentInfo();
        //String date = "2018-7-17 13:14:16";
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sf.format(date));
        try {
            payMentInfo.setCreateOrderTime(date);
            System.out.println(sf.format(payMentInfo.getCreateOrderTime()));
        } finally {
            System.out.println("格式化结束");
        }*/
        log.info("99996666");
    }
}
