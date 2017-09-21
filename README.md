# Searcher

## ES Server Config

- Cluster name: elasticsearch_demo
- Port: 9200 -- for http; 9300 -- for Java client;
- Elasticsearch version 5.5.2
- Other configs can be found at `/etc/elasticsearch/`

## Other Related Server

- [Version Support Matrix](https://www.elastic.co/support/matrix#show_compatibility)
- [Config File Backup Repo](http://192.168.1.100:81/zzt/logstash)

### Logstash
- Version: 5.6.0
- Logstash config: `/etc/logstash/{logstash.yml, log4j2.properties}`
- Pipeline config: `/etc/logstash/conf.d/xxx.conf`

### FileBeats
- Version: 5.5.2
- Config: `/etc/filebeat/filebeat.yml`

### Kibana
- Version: 5.5.2
- Port: 5601
- Config: `/etc/kibana/kibana.yml`
