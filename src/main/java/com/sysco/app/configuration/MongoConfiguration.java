package com.sysco.app.configuration;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sysco.app.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("${dbName}")
    private String databaseName;
    @Value("${port}")
    private int clientPort;
    @Value("${host}")
    private String clientHost;

    protected String getDatabaseName() {
        return databaseName;
    }

    public MongoClient mongoClient() {
        return new MongoClient(clientHost, clientPort);
    }
}

