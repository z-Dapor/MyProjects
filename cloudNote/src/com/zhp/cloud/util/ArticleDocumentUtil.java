package com.zhp.cloud.util;

import com.zhp.cloud.note.bean.Article;
import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dapor
 */
public class ArticleDocumentUtil {
	private static Logger logger = LoggerFactory.getLogger(ArticleDocumentUtil.class);
	/**
	 * 把Document转为Article
	*/
	public static Article documentToArticle(Document doc) {
	Article article = null;
	try {
		logger.debug("documentToArticle start!");
		article = new Article();
		article.setId(doc.get("id"));
		article.setTitle(doc.get("title"));
		article.setContent(doc.get("content"));
		article.setTime(doc.get("time"));
		article.setTc100(doc.get("tc100"));
	} catch (NumberFormatException e) {
		logger.error("documentToArticle error!"+doc.toString(),e);
	}
	return article; 
	}
}
