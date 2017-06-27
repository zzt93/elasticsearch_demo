package com.superid.query.user.affair;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by zzt on 17/6/27.
 */
@Document(indexName = "affair", type = "affair", refreshInterval = "1s")
public class Affair {


}
