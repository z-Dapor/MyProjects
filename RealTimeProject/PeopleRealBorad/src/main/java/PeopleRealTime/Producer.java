/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Producer
 * Author:   臧浩鹏
 * Date:     2018/7/17 13:22
 * Description: 模拟生产者实现订单信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PeopleRealTime;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 *
 * 〈模拟生产者实现订单信息〉
 *
 * @author 臧浩鹏
 * @create 2018/7/17
 * @since 1.0.0
 */
public class Producer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","192.168.5.48:9092");
        properties.put("ack","all");
        properties.put("retries",0);
        properties.put("batch.size",16384);
        properties.put("linger.ms",1);
        properties.put("buffer.memory",16384);
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //创建kafkaProducer
        KafkaProducer<Object, Object> producer = new KafkaProducer<>(properties);
        System.out.println("---------Producer开始发送数据---------");
        while (true){
            producer.send(new ProducerRecord<Object, Object>("realBoard",new PayMentInfo().random()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
