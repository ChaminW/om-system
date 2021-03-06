package com.sysco.app.configuration;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sysco.app.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("${DB_NAME}")
    private String databaseName;
    @Value("${PORT}")
    private int clientPort;
    @Value("${HOST}")
    private String clientHost;

    protected String getDatabaseName() {
        return databaseName;
    }

    public MongoClient mongoClient() {
        return new MongoClient(clientHost, clientPort);
    }
}

