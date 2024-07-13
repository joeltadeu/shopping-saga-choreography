package com.shopping.lib.commons.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "database_sequence")
@NoArgsConstructor
public class DatabaseSequence {

  @Id
  private String id;
  private long seq;
}
