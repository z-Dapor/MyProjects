/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: RealBoardTopology
 * Author:   臧浩鹏
 * Date:     2018/7/17 13:48
 * Description: storm-kafka的整合类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PeopleRealTime;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

/**
 * 〈storm-kafka的整合类〉
 *
 * @author 臧浩鹏
 * @create 2018/7/17
 * @since 1.0.0
 */
public class RealBoardTopology {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();
        KafkaSpoutConfig.Builder<String, String> kafkaConfig = KafkaSpoutConfig.builder("192.168.5.48:9092", "realBoard");
        kafkaConfig.setGroupId("dapor");
        KafkaSpoutConfig<String, String> build = kafkaConfig.setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST).build();
        KafkaSpout<String, String> kafkaSpout = new KafkaSpout<>(build);

        builder.setSpout("kafkaSpout",kafkaSpout,2);
        builder.setBolt("processBolt",new ProcessOrderBolt(),4).localOrShuffleGrouping("kafkaSpout");
        Config config = new Config();
        if (args.length>0){
            config.setNumWorkers(2);
            config.setDebug(false);
            StormSubmitter.submitTopology(args[0],config,builder.createTopology());
        }else {
            LocalCluster localCluster = new LocalCluster();
            config.setDebug(true);
            //实时看板
            localCluster.submitTopology("realBoard",config,builder.createTopology());
        }

    }
}
