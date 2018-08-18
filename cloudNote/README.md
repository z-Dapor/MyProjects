项目名称：仿有道云笔记
开发环境：整体架构：Spring SpringMvc Hadoop Hbase Redis Jdk1.8 Tomcat8.0
数据存储：Hadoop2.6.0, Hbase0.98，Redis3.0
前台：fckeditor,JQuery 
项目描述：项目含有对用户笔记本及其下的笔记的基本增删改查操作;在此基础上增添了 收藏,回收站功能;
个人职责：编写 Hbase 与 Redis 相关联的增删改查等操作；设计 Hbase 表与 Redis 数据
项目总结：在设计 Hbase 的表结构时,学习到了 Hbase 的 RowKey 设计规则，看了很多文章;熟悉了 Hbase
client 操作的 Api;在更新操作中设计了事物处理机制。