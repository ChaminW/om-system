package com.sysco.app.configuration;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sysco.app.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {

    private final static String databaseName = "omapp-db";
    private final static int clientPort = 27017;
    private final static String clientHost = "127.0.0.1";

    protected String getDatabaseName() {
        return databaseName;
    }

    public MongoClient mongoClient() {
        return new MongoClient(clientHost, clientPort);
    }


}

