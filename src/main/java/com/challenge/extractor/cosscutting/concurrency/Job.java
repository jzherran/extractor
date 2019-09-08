package com.challenge.extractor.cosscutting.concurrency;

import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Getter
@Builder
@AllArgsConstructor
public class Job {

  private String key;
  private CompletableFuture<Boolean> task;
}
