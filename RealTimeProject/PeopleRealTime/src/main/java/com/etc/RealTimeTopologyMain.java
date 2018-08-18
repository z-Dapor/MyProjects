/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: RealTimeTopologyMain
 * Author:   臧浩鹏
 * Date:     2018/7/18 16:16
 * Description: 项目启动类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.etc;

import com.google.common.collect.Maps;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.jdbc.bolt.JdbcInsertBolt;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.mapper.JdbcMapper;
import org.apache.storm.jdbc.mapper.SimpleJdbcMapper;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

import java.sql.SQLException;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈项目启动类〉
 *
 * @author 臧浩鹏
 * @create 2018/7/18
 * @since 1.0.0
 */
public class RealTimeTopologyMain {
    public static void main(String[] args) throws SQLException {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        // KafkaSpout配置
        KafkaSpoutConfig.Builder<String, String> kafkaConfig = KafkaSpoutConfig.builder("192.168.5.48:9092", "people").setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST).setGroupId("Journey");
        KafkaSpout<String,String> kafkaSpout = new KafkaSpout<>(kafkaConfig.build());
        //JdbcSpout配置

      /* *//* Map hikariConfigMap = Maps.newHashMap();
        hikariConfigMap.put("jdbcUrl","jdbc:mysql://localhost:3306/project");
        hikariConfigMap.put("username","root");
        hikariConfigMap.put("password","root");
        hikariConfigMap.put("dataSource.cachePrepStmts",true);
        hikariConfigMap.put("dataSource.prepStmtCacheSize",250);
        hikariConfigMap.put("dataSource.prepStmtCacheSqlLimit",2048);
        hikariConfigMap.put("dataSource.useServerPrepStmts",true);
        hikariConfigMap.put("dataSource.useLocalSessionState",true);
        hikariConfigMap.put("dataSource.rewriteBatchedStatements",true);
        hikariConfigMap.put("dataSource.cacheResultSetMetadata",true);
        hikariConfigMap.put("dataSource.cacheServerConfiguration",true);
        hikariConfigMap.put("dataSource.elideSetAutoCommits",true);
        hikariConfigMap.put("dataSource.maintainTimeStats",false);*//*
        HikariConfig hikariConfigconfig = new HikariConfig("src/hikari.properties");
        HikariDataSource ds = new HikariDataSource(hikariConfigconfig);*/
        Map hikariConfigMap = Maps.newHashMap();
        hikariConfigMap.put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariConfigMap.put("dataSource.url", "jdbc:mysql://localhost/project");
        hikariConfigMap.put("dataSource.user","root");
        hikariConfigMap.put("dataSource.password","root");
        ConnectionProvider connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);
        String tableName = "people";
        JdbcMapper simpleJdbcMapper = new SimpleJdbcMapper(tableName, connectionProvider);
        JdbcInsertBolt userPersistanceBolt = new JdbcInsertBolt(connectionProvider, simpleJdbcMapper)
                .withTableName("people")
                .withQueryTimeoutSecs(30);


        topologyBuilder.setSpout("kafkaSpout",kafkaSpout);
        topologyBuilder.setBolt("BaseDeal",new BaseDeal()).shuffleGrouping("kafkaSpout");
        topologyBuilder.setBolt("JdbcInsertBolt",userPersistanceBolt).shuffleGrouping("BaseDeal");




        Config config = new Config();
        if (args.length>0){
            config.setNumWorkers(2);
            config.setDebug(false);
            try {
                StormSubmitter.submitTopology(args[0],config,topologyBuilder.createTopology());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            } catch (AuthorizationException e) {
                e.printStackTrace();
            }
        }else {
            LocalCluster localCluster = new LocalCluster();
            config.setDebug(true);
            //绑定topic
            localCluster.submitTopology("people",config,topologyBuilder.createTopology());
        }
    }
}
