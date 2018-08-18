/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: BaseDeal
 * Author:   臧浩鹏
 * Date:     2018/7/18 16:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.etc;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 臧浩鹏
 * @create 2018/7/18
 * @since 1.0.0
 */
public class BaseDeal extends BaseRichBolt {
    private OutputCollector collector;
    @Override
    public void prepare(Map stormConf, TopologyContext context,OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        try{
            Object value = tuple.getValue(4);
            ArrayList format = MainHandler.getFormat(value.toString());
            System.out.println("-----------------"+format.get(3)+"--------------");
            System.out.println("------------------------------------------------");
            System.out.println("------------------------------------------------");
            System.out.println("------------------------------------------------");
            collector.emit(new Values(format.get(3),Double.parseDouble((String)format.get(1)),Double.parseDouble((String)format.get(2))));
            this.collector.ack(tuple);
        }catch (Exception e){
            e.printStackTrace();
            this.collector.fail(tuple);
        }

    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer Declarer) {
            Declarer.declare(new Fields("time","longitude","latitude"));
    }
}
