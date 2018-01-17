package com.sysco.app.configuration;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.sysco.app.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {

    protected String getDatabaseName() {
        return "springTest";
    }

    public MongoClient mongoClient() {
        return new MongoClient("127.0.0.1",27017);
    }


}

