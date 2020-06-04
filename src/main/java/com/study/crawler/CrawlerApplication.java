package com.study.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class CrawlerApplication {

  public static void main(String[] args) {
    MongoCollection<Document> collection = getMongoDB().getCollection("tcmap");

    Bson projections = Projections.include("_id", "navigation", "name");

    FindIterable<Document> documents = collection.find(Filters.size("navigation", 3));

    for (Document document : documents) {
      ObjectId id = document.getObjectId("_id");
      List<Map> navigations = document.getList("navigation", Map.class);

      FindIterable<Document> parent = collection
          .find(
              Filters.eq("navigation", navigations.stream().limit(2).collect(Collectors.toList())))
          .projection(projections);
      if (parent.first() != null) {
        document.put("parentId", Objects.requireNonNull(parent.first()).getObjectId("_id"));
        collection.replaceOne(Filters.eq("_id", id), document);
      } else {
        document.remove(id);
      }
    }
  }

  private static MongoDatabase getMongoDB() {
    List<ServerAddress> adds = new ArrayList<>();
    ServerAddress serverAddress = new ServerAddress("10.1.199.51", 27017);
    adds.add(serverAddress);
    MongoCredential mongoCredential =
        MongoCredential.createScramSha256Credential("admin", "admin", "956697621ok".toCharArray());
    MongoClientOptions options = MongoClientOptions.builder()
        // 设置连接超时时间为10s
        .connectTimeout(1000 * 10)
        // 设置最长等待时间为10s
        .maxWaitTime(1000 * 10).build();
    return new MongoClient(adds, mongoCredential, options).getDatabase("crawler");
  }
}
