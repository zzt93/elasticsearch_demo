# Searcher

## ES Server Config

- Cluster name: elasticsearch_demo
- Elasticsearch version 2.4.4
- Other configs can be found at `/etc/elasticsearch/`

## Other Related Server

- [Version Support Matrix](https://www.elastic.co/support/matrix#show_compatibility)
- [Config File Backup Repo](http://192.168.1.100:81/zzt/logstash)

### Logstash
- Version: 5.5.0
- Logstash config: `/etc/logstash/{logstash.yml, log4j2.properties}`
- Pipeline config: `/etc/logstash/conf.d/xxx.conf`

### FileBeats
- Version: 5.5.0
- Config: `$FILEBEAT_HOME/filebeat.yml`

### Kibana
- Version: 4.6.2
- Config: ``