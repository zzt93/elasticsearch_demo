package cn.superid.search.impl.entities.user.member;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author zzt
 */
@Document(indexName = "member-#{suffix.toString()}", type = "user", refreshInterval = "1s", shards = 1, createIndex = false)
public class Member {

}
