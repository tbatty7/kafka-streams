package com.battybuilds.kafkastreams;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class Junit5MongoTest {

    @DisplayName("Given, When, Then statement")
    @Test
    public void dummy(@Autowired MongoTemplate mongoTemplate) {
        
        // given
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        DBObject objectToSave = builder.add("key", "value").get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                .containsOnly("value");
        System.out.println("-----------All-----------");
        System.out.println(mongoTemplate.findAll(DBObject.class, "collection"));
        System.out.println("-----------One-----------");
        Document document = (Document) mongoTemplate.findAll(DBObject.class, "collection").get(0);
        System.out.println(document);
        System.out.println("-----------Value-----------");
        System.out.println(document.get("key"));
    }
}
