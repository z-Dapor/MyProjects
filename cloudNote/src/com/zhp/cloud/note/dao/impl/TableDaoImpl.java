package com.zhp.cloud.note.dao.impl;

import com.zhp.cloud.note.dao.TableDao;
import com.zhp.cloud.util.constants.Constants;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service("tableDaoImpl")
public class TableDaoImpl implements TableDao{

	private static Logger logger = LoggerFactory.getLogger(TableDaoImpl.class);
	/**
	 * 创建hbase的表
	 * tableName：表名
	 * familys：列族数组
	 * IOException：IO异常
	 */
	@Override
	public void creatTable(String tableName, String[] familys) throws IOException{
		//验证所要创建的表是否存在
		if (Constants.ADMIN.tableExists(tableName)) {
			logger.debug("表存在，名字为："+tableName);
		}else{
			//通过表名创建表
			HTableDescriptor table = new HTableDescriptor(tableName);
			for (int i = 0; i < familys.length; i++) {
				//创建列族
				HColumnDescriptor family = new HColumnDescriptor(familys[i]);
				//向表中添加列族
				table.addFamily(family);
			}
			//添加表
			Constants.ADMIN.createTable(table);
			logger.debug("创建表"+tableName+"成功");
		}
	}
	/**
	 * 创建hbase的表
	 * tableName：表名
	 * familys：列族数组
	 * IOException：IO异常
	 */
	@Override
	public void createTableSplit(String tableName, String[] familys) throws IOException {
			if (Constants.ADMIN.tableExists(tableName)) {
				logger.debug("表存在，名字为："+tableName);
			} else {
				HTableDescriptor desc = new HTableDescriptor(tableName);
				for (int i = 0; i < familys.length; i++) {
					HColumnDescriptor family = new HColumnDescriptor(familys[i]);
					desc.addFamily(family);
				}
				//切分表
				Constants.ADMIN.createTable(desc, "a0".getBytes(), "a10000".getBytes(), 3);
				logger.debug("创建表"+tableName+"成功");
			}
	}
	/**
	 * 创建hbase的表
	 * tableName：表名
	 * familys：列族数组
	 * IOException：IO异常
	 */
	@Override
	public void createTableSplit2(String tableName, String[] familys) throws IOException {
			if (Constants.ADMIN.tableExists(tableName)) {
				logger.debug("表存在，名字为："+tableName);
			} else {
				HTableDescriptor desc = new HTableDescriptor(tableName);
				for (int i = 0; i < familys.length; i++) {
					HColumnDescriptor family = new HColumnDescriptor(familys[i]);
					desc.addFamily(family);
				}
				//切分表
				byte[][] regions = new byte[][] { Bytes.toBytes("a3333333"),Bytes.toBytes("a6666666") };
				// 表示有三个region分别放入key：
				// [1] start key: , end key: A
				// [2] start key: A, end key: D
				// [3] start key: D, end key:
				Constants.ADMIN.createTable(desc, regions);
				logger.debug("创建表"+tableName+"成功");
			}
	}
	/**
	 * 删除表
	 * tableName:表名
	 * IOException:IO异常
	 */
	@Override
	public void deleteTable(String tableName) throws IOException{
		//验证所要创建的表是否存在
		if (!Constants.ADMIN.tableExists(tableName)) {
			logger.debug("表不存在,表名："+tableName);
		} else {
			//删除表之前，先要将表disable
			Constants.ADMIN.disableTable(tableName);
			//删除表
			Constants.ADMIN.deleteTable(tableName);
			logger.debug("删除表"+tableName+"成功");
		}
	}
	/**
	 * 获取表
	 * tableName：表名
	 * HTableInterface：table操作类
	 * @throws IOException 
	 */
	@Override
	public Table getTable(String tableName) throws IOException{
		//从连接池中获取表
		return  Constants.CONNECTION.getTable(TableName.valueOf(tableName));
	}
	public static void main(String[] args) throws IOException {
		TableDaoImpl tm = new TableDaoImpl();
		String[] familys ={"aaa1","aaa2","aaa3"};
		tm.creatTable("wangsf_aaa", familys);
		Table table = tm.getTable("wangsf_aaa");
		System.out.println(table.getTableDescriptor());
	}

}
