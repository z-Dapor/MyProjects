/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MapSource
 * Author:   臧浩鹏
 * Date:     2018/7/21 9:34
 * Description: 源码解读
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.project.searchSource;

import java.util.*;
import java.util.concurrent.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈源码解读〉
 *
 * @author 臧浩鹏
 * @create 2018/7/21
 * @since 1.0.0
 */
public class MapSource {
    public final static int THREAD_POOL_SIZE = 10;

    public static Map<String, Integer> hashTableObject = null;
    public static Map<String, Integer> synchronizedMapObject = null;
    public static Map<String, Integer> concurrentHashMapObject = null;

    public static void main(String[] args) throws InterruptedException {


        ExecutorService  executor = Executors.newSingleThreadExecutor();




        // 测试HashTable
        hashTableObject = new Hashtable<String, Integer>();
        performTest(hashTableObject);

        // 测试synchronizedMap
        synchronizedMapObject = Collections.synchronizedMap(new HashMap<String, Integer>());
        performTest(synchronizedMapObject);

        // 测试ConcurrentHashMap
        concurrentHashMapObject = new ConcurrentHashMap<String, Integer>();
        performTest(concurrentHashMapObject);

    }

    private static void performTest(final Map<String, Integer> map) throws InterruptedException {
        System.err.println("开始测试 : " + map.getClass());
        long averageTime = 0;
        for (int i = 0; i < 5; i++) {
            long startTime = System.nanoTime();
            ExecutorService es = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            for (int j = 0; j < THREAD_POOL_SIZE; j++) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 500000; i++) {
                            Integer randomNumber = (int) Math.ceil(Math.random() * 550000);
                            map.put(String.valueOf(randomNumber), randomNumber);
                        }
                    }
                });
            }

            es.shutdown();

            es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

            long entTime = System.nanoTime();
            long totalTime = (entTime - startTime) / 1000000L;
            averageTime += totalTime;
            System.out.println("添加耗时" + totalTime + " ms");
        }
        System.out.println("操作 " + map.getClass() + " 平均时间为 " + averageTime / 5 + " ms\n");
    }

}
