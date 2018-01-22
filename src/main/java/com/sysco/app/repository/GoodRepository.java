package com.sysco.app.repository;

import com.sysco.app.model.Good;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("goodRepository")
public interface GoodRepository extends MongoRepository<Good, String> {
}
