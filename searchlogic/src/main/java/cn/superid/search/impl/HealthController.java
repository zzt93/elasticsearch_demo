package cn.superid.search.impl;

import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzt
 */
@RestController
@RequestMapping("/")
public class HealthController {


  @GetMapping("/health")
  public Health health() {
    return Health.up().withDetail("description", "Searcher").build();
  }

}
