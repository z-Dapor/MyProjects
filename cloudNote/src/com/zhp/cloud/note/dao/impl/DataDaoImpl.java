package com.zhp.cloud.note.dao.impl;

import com.zhp.cloud.note.bean.Note;
import com.zhp.cloud.note.dao.DataDao;
import com.zhp.cloud.util.JsonUtil;
import com.zhp.cloud.util.constants.Constants;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("dataDaoImpl")
public class DataDaoImpl implements DataDao {
	private static Logger logger = LoggerFactory.getLogger(DataDaoImpl.class);

	/**
	 * 插入数据 
	 * tableName：表名 
	 * rowKey：rowKey 
	 * family：列族 
	 * qualifier：列描述，即列 
	 * value：插入的值
	 * IOException：IO异常
	 */
	@Override
	public boolean insertData(String tableName, String rowKey, String family,
							  String qualifier, String value)  {
		Table table = null;
		try {
			table = Constants.CONNECTION.getTable(TableName.valueOf(tableName));
			// 创建put，添加rowKey
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
					// 放入数据
					Bytes.toBytes(value));
			// 将数据存入table
			table.put(put);
			table.flushCommits();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				table.close();// 关闭table
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
		
	}

	/**
	 * 插入数据 
	 * tableName：表名 
	 * rowKey：rowKey
	 * famQuaVals：二维数组，[[famliy，qualifier，value],……………………] 
	 * IOException：IO异常
	 */
	@Override
	public boolean insertData(String tableName, String rowKey, String[][] famQuaVals)  {
		Table table = null;
		try {
			table =  Constants.CONNECTION.getTable(TableName.valueOf(tableName));
			//AutoFlush指的是在每次调用HBase的Put操作，是否提交到HBase Server。 默认是true,每次会提交。如果此时是单条插入，就会有更多的IO,从而降低性能
			table.setAutoFlushTo(false);
			//Write Buffer Size在AutoFlush为false的时候起作用，默认是2MB,也就是当插入数据超过2MB,就会自动提交到Server
			table.setWriteBufferSize(Constants.HBASE_WRITE_BUFFER);
			List<Put> lp = new ArrayList<>();
			for (int i = 0; i < famQuaVals.length; i++) {
				//创建put，添加rowKey
				Put put = new Put(Bytes.toBytes(rowKey));
				if (famQuaVals[i][2]!=null) {
					//放入数据
					put.add(Bytes.toBytes(famQuaVals[i][0]), Bytes.toBytes(famQuaVals[i][1]),Bytes.toBytes(famQuaVals[i][2]));
				}
				lp.add(put);
			}
			//将数据存入table
			table.put(lp);
			table.flushCommits();//提交
			logger.debug("插入数据成功!" + table.getTableDescriptor()+"===="+tableName+"/"+rowKey+"/"+famQuaVals);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 根据rowKey删除数据 
	 * tableName：表名 
	 * rowKey：rowKey 
	 * IOException：IO异常
	 */
	@Override
	public boolean deleteData(String tableName, String rowKey)  {
		Table table = null;
		try {
			table =  Constants.CONNECTION.getTable(TableName.valueOf(tableName));
			// 创建delete类，存入要删除的rowKey
			Delete del = new Delete(Bytes.toBytes(rowKey));
			// 删除数据
			table.delete(del);
			table.flushCommits();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
		
	}

	/**
	 * 查询所有数据
	 *  tableName：表名 
	 *  ResultScanner:结果列表 
	 *  IOException：IO异常
	 */
	@Override
	public ResultScanner queryAll(String tableName) throws IOException {
		// 从连接池中获取表
		Table table =  Constants.CONNECTION.getTable(TableName.valueOf(tableName));
		// 创建scan，用于查询
		Scan scan = new Scan();
		// 通过scan查询结果
		ResultScanner scanner = table.getScanner(scan);
		/*
		 * for (Result row: scanner) {//循环出每一条row
		 * System.out.println(row.getRow()); for (KeyValue kv : row.raw())
		 * {//循环row的不同列 System.out.println(new String(kv.getRow()));//获取rowkey
		 * System.out.println(new String(kv.getFamily()));//获取列族
		 * System.out.println(new String(kv.getQualifier()));//获取列描述
		 * System.out.println(new String(kv.getValue()));//获取值
		 * System.out.println(kv.getTimestamp());//获取版本时间 } }
		 */
		table.close();// 关闭table
		return scanner;
	}

	/**
	 * 通过rowKey查询笔记列表
	 * tableName：表名 
	 * rowKey：rowKey 
	 * IOExceptionL：IO异常
	 * 
	 */
	@Override
	public List<Note> queryNoteListByRowKey(String rowKey) {
		Result queryByRowKey;
		List<Note> noteList = null;
		try {
			// 从连接池中获取表
			Table table =  Constants.CONNECTION.getTable(TableName.valueOf(Constants.NOTEBOOK_TABLE_NAME));
			// 获取get，注入rowKey
			Get get = new Get(rowKey.getBytes());
			queryByRowKey = table.get(get);
			byte[] value = queryByRowKey.getValue(Constants.NOTEBOOK_FAMLIY_NOTEBOOKINFO.getBytes(), Constants.NOTEBOOK_NOTEBOOKINFO_CLU_NOTELIST.getBytes());
			if (value!=null) {
				String str = new String(value);
				//将list转换为json
				noteList = JsonUtil.changeStringToListNote(str);
			}
			table.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noteList;
	}
	/**
	 * 查询笔记详情
	 * 通过rowKey查询
	 * tableName：表名 
	 * rowKey：rowKey 
	 * IOExceptionL：IO异常
	 * 
	 */
	@Override
	public Note queryNoteByRowKey(String rowKey) {
		Result row;
		Note note =null;
		try {
			// 从连接池中获取表
			Table table =  Constants.CONNECTION.getTable(TableName.valueOf(Constants.NOTE_TABLE_NAME));
			// 获取get，注入rowKey
			Get get = new Get(rowKey.getBytes());
			row = table.get(get);
			if (row!=null) {
				note = new Note();
				note.setRowKey(new String(row.getRow()));
				note.setName(new String(row.getValue(
						Constants.NOTE_FAMLIY_NOTEINFO.getBytes(),
						Constants.NOTE_NOTEINFO_CLU_NOTENAME.getBytes()), "UTF-8"));
				note.setCreateTime(new String(row.getValue(
						Constants.NOTE_FAMLIY_NOTEINFO.getBytes(),
						Constants.NOTE_NOTEINFO_CLU_CREATETIME.getBytes()), "UTF-8"));
				byte[] content = row.getValue(
						Constants.NOTE_FAMLIY_CONTENTINFO.getBytes(),
						Constants.NOTE_CONTENTINFO_CLU_CONTENT.getBytes());
				if (content != null) {
					String string = new String(content, "UTF-8");
					note.setContent(string);
				} else {
					note.setContent("");
				}
				note.setStatus(new String(row.getValue(
						Constants.NOTE_FAMLIY_NOTEINFO.getBytes(),
						Constants.NOTE_NOTEINFO_CLU_STATUS.getBytes()), "UTF-8"));
				
			}
			table.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return note;
	}
	@Override
	public List<String> queryByRowKeyString(String tableName, String rowKey)
	{
		Result queryByRowKey = null;
		List<String> noteList = null;
		try {
			// 从连接池中获取表
			Table table =  Constants.CONNECTION.getTable(TableName.valueOf(tableName));
			// 获取get，注入rowKey
			Get get = new Get(rowKey.getBytes());
			queryByRowKey = table.get(get);
			byte[] value = queryByRowKey.getValue(Constants.NOTEBOOK_FAMLIY_NOTEBOOKINFO.getBytes(), Constants.NOTEBOOK_NOTEBOOKINFO_CLU_NOTELIST.getBytes());
			if (value!=null) {
				String str = new String(value);
				//将list转换为json
				noteList = JsonUtil.changeStringToListString(str);
			}
			table.close();// 关闭table
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noteList;
	}

	/**
	 * 查询部分rowKey数据 
	 * tableName：表名 
	 * rowKeys:rowKey列表 
	 * IOException:IO异常
	 */
	@Override
	public ResultScanner querySome(String tableName, List<String> rowKeys)
			throws IOException {
		// 从连接池中获取表
		Table table =  Constants.CONNECTION.getTable(TableName.valueOf(tableName));
		// 创建scan，用于查询
		Scan scan = new Scan();
		// FilterList.Operator.MUST_PASS_ONE（或）的关系
		FilterList filter = new FilterList(FilterList.Operator.MUST_PASS_ONE);

		for (String rowKey : rowKeys) {
			String[] split = rowKey.split(",");
			filter.addFilter(new SingleColumnValueFilter(Bytes.toBytes(split[0]), Bytes.toBytes(split[1]),CompareOp.EQUAL, Bytes.toBytes(split[2])));
		}
		scan.setFilter(filter);
		ResultScanner scanner = table.getScanner(scan);
		table.close();
		return scanner;
	}

	/**
	 * 通过rowKey正则表达式查询 
	 * tableName：表名 
	 * reg：正则表达式 
	 * ResultScanner：结果集 
	 * IOException：IO异常
	 */
	@Override
	public ResultScanner queryByReg(String tableName, String reg) throws IOException {
		// 从连接池中获取表
		Table table =  Constants.CONNECTION.getTable(TableName.valueOf(tableName));
		// 创建scan，用于查询
		Scan scan = new Scan();
		// 创建正则表达式filter
		RowFilter filter = new RowFilter(CompareOp.EQUAL, new RegexStringComparator(reg));
		// 设置filter
		scan.setFilter(filter);
		// 通过scan查询结果
		ResultScanner scanner = table.getScanner(scan);
		table.close();// 关闭table
		return scanner;
	}
	/**
	 * 从一个表复制笔记到另一个表
	 * fromTableName：源表
	 * fromRowKey：源表rowKey
	 * toTableName：目的表
	 * toRowKey：目的表RowKey
	 */
	@Override
	public void copyData(String fromTableName, String toTableName, String fromRowKey, String toRowKey) throws IOException {
		// 从连接池中获取表
		Table fromTable =  Constants.CONNECTION.getTable(TableName.valueOf(fromTableName));
		//从连接池中获取表
		Table toTable =  Constants.CONNECTION.getTable(TableName.valueOf(toTableName));
		// 获取get，注入rowKey
		Get get = new Get(fromRowKey.getBytes());
		// 获取单条row
		Result row = fromTable.get(get);
		//AutoFlush指的是在每次调用HBase的Put操作，是否提交到HBase Server。 默认是true,每次会提交。如果此时是单条插入，就会有更多的IO,从而降低性能
		toTable.setAutoFlushTo(false);
		//Write Buffer Size在AutoFlush为false的时候起作用，默认是2MB,也就是当插入数据超过2MB,就会自动提交到Server
		toTable.setWriteBufferSize(Constants.HBASE_WRITE_BUFFER);
		List<Put> lp = new ArrayList<Put>();
		//循环数据
		 for (KeyValue kv : row.raw()) {
			 //创建put，添加rowKey
			 Put put = new Put(Bytes.toBytes(toRowKey));
			 //放入数据
			 put.add(kv.getFamily(), kv.getQualifier(),kv.getValue());
			 lp.add(put);
			 }
		//将数据存入table
		 toTable.put(lp);
		 toTable.flushCommits();//提交
		logger.debug("拷贝数据成功!fromTableName：" + fromTableName+";toTableName:"+toTableName+";fromRowKey"+fromRowKey+";toRowKey"+toRowKey);
		toTable.close();//关闭table
		fromTable.close();//关闭table
	}

	@Override
	public Result queryByRowKey(String noteTableName, String rowKey) {

		Result row = null;
		try {
			// 从连接池中获取表
			Table table =  Constants.CONNECTION.getTable(TableName.valueOf(Constants.NOTE_TABLE_NAME));
			// 获取get，注入rowKey
			Get get = new Get(rowKey.getBytes());
			row = table.get(get);
			table.close();// 关闭table
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	
	}

}
