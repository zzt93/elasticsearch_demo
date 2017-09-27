package cn.superid.search.impl;

import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;

import java.net.InetAddress;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author zzt
 */
@Configuration
public class ESConfig {
  private static final Logger logger = LoggerFactory.getLogger(ESConfig.class);
  private static final String COMMA = ",";
  private static final String COLON = ":";


  private final ElasticsearchProperties properties;
  @Value("${esUser}")
  private String esUser;

  @Autowired
  public ESConfig(ElasticsearchProperties properties) {
    this.properties = properties;
  }

  @Bean
  public TransportClient transportClient() throws Exception {
    PreBuiltXPackTransportClient client = new PreBuiltXPackTransportClient(settings());
    String clusterNodes = properties.getClusterNodes();
    Assert.hasText(clusterNodes, "[Assertion failed] clusterNodes settings missing.");
    for (String clusterNode : split(clusterNodes, COMMA)) {
      String hostName = substringBeforeLast(clusterNode, COLON);
      String port = substringAfterLast(clusterNode, COLON);
      Assert.hasText(hostName, "[Assertion failed] missing host name in 'clusterNodes'");
      Assert.hasText(port, "[Assertion failed] missing port in 'clusterNodes'");
      logger.info("adding transport node : " + clusterNode);
      client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port)));
    }
    return client;
  }

  private Settings settings() {
    Builder put = Settings.builder()
        .put("cluster.name", properties.getClusterName());
    if (StringUtils.isEmpty(esUser)) {
      logger.error("Not config elastic user and password in environment variable, set 'ES_USER'");
      return put.build();
    }
    return put
        .put("xpack.security.user", esUser)
//        .put("client.transport.sniff", clientTransportSniff)
//        .put("client.transport.ignore_cluster_name", clientIgnoreClusterName)
//        .put("client.transport.ping_timeout", clientPingTimeout)
//        .put("client.transport.nodes_sampler_interval", clientNodesSamplerInterval)
        .build();
  }
}
