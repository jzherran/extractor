package com.challenge.extractor.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Configuration
public class InfrastructureConfiguration {

  @Bean
  public ExecutorService executorService() {
    return Executors.newCachedThreadPool();
  }
}
