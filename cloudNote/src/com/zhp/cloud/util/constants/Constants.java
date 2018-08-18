package com.zhp.cloud.util.constants;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/***
 * 程序中使用的静态字段定义在此处
 * @author Dapor
 */
public class Constants {
	/**hbase配置*/
	//配置信息类
	public static  Configuration CONFIG;
	//表管理类
	public static  HBaseAdmin ADMIN ;
	public static Connection CONNECTION ;
	//批量写的buffer大小
	public static long HBASE_WRITE_BUFFER;
	/**笔记本信息**/
	//表名
	public static final String NOTEBOOK_TABLE_NAME="nbook";
	//列族1，笔记本信息
	public static final String NOTEBOOK_FAMLIY_NOTEBOOKINFO="nbinfo";
	//列1，笔记本名字
	public static final String NOTEBOOK_NOTEBOOKINFO_CLU_NOTEBOOKNAME="nbn";
	//列2：创建笔记本时间
	public static final String NOTEBOOK_NOTEBOOKINFO_CLU_CREATETIME="ct";
	//列3：笔记本状态
	public static final String NOTEBOOK_NOTEBOOKINFO_CLU_STATUS="st";
	//列4：笔记本下笔记信息列表
	public static final String NOTEBOOK_NOTEBOOKINFO_CLU_NOTELIST="nl";
	/**笔记信息**/
	//表名
	public static final String NOTE_TABLE_NAME="note";
	//列族1：笔记信息
	public static final String NOTE_FAMLIY_NOTEINFO="noteinfo";
	//列1：笔记名字
	public static final String NOTE_NOTEINFO_CLU_NOTENAME="nn";
	//列2：创建时间
	public static final String NOTE_NOTEINFO_CLU_CREATETIME="ct";
	//列3：笔记状态
	public static final String NOTE_NOTEINFO_CLU_STATUS="st";
	//列族2：笔记内容
	public static final String NOTE_FAMLIY_CONTENTINFO="ci";
	//列1：笔记内容
	public static final String NOTE_CONTENTINFO_CLU_CONTENT="c";
	
	/**笔记rowKey前缀**/
	public static final String NOTE_PREFIX = "note"+Constants.ROWKEY_SEPARATOR;
	
	/**lucene配置*/
	public static String LUCENE_DIR;//lucene库文件夹
	public static String FILE_DIR;//content文件夹
	public static  int LUCENE_PAGE_COUNT;//lucene分页，每页大小
	
	/**redis配置*/
	public static  String REDIS_IP ;
	public static  int REDIS_PORT ;
	public static  int REDIS_TIMOUT ;
	//public static  String REDIS_AUTH ;
	
	/**user信息**/
	public static final String USER_INFO="userinfo" ;
	
	/**特殊笔记列表*/
	//回收站
	public static final String RECYCLE= Constants.ROWKEY_SEPARATOR+"0000000000000" ;
	//收藏
	public static final String STAR=    Constants.ROWKEY_SEPARATOR+"0000000000001" ;
	//活动
	public static final String ACTIVITY=Constants.ROWKEY_SEPARATOR+"0000000000002" ;
	/**分隔符*/
	public static final String STRING_SEPARATOR = "|" ;
	//rowkey的分隔符
	public static final String ROWKEY_SEPARATOR = "_" ;
	
}
