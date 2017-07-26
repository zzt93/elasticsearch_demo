package cn.superid.search.impl.save;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author zzt
 */
public interface SaveSink {

  String INPUT = "search-index-input";

  @Input(INPUT)
  SubscribableChannel input();
}
