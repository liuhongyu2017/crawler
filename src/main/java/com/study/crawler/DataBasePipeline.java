package com.study.crawler;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 持久化
 *
 * @author lhy
 * @version 1.0 2020/4/5
 */
public class DataBasePipeline {

  private final MongoDatabase mongoDatabase;

  public DataBasePipeline(MongoDatabase mongoDatabase) {
    this.mongoDatabase = mongoDatabase;
  }

  public void process(ResultItems resultItems, Task task) {
    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("tcmap");
    Document document = new Document();
    document.putAll(resultItems.getAll());
    mongoCollection.insertOne(document);
  }
}
