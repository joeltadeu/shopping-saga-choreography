package com.shopping.lib.commons.service;

import com.shopping.lib.commons.entity.DatabaseSequence;
import java.util.Objects;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {
  private final MongoOperations mongoOperations;

  public SequenceService(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  public long generateSequence(String seqName) {

    DatabaseSequence counter =
        mongoOperations.findAndModify(
            Query.query(Criteria.where("_id").is(seqName)),
            new Update().inc("seq", 1),
            FindAndModifyOptions.options().returnNew(true).upsert(true),
            DatabaseSequence.class);
    return !Objects.isNull(counter) ? counter.getSeq() : 1;
  }
}
