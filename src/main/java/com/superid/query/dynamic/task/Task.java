package com.superid.query.dynamic.task;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by zzt on 17/6/21.
 */
@Document(indexName = "task", type = "task", refreshInterval = "1s", createIndex = false)
public class Task {
}
