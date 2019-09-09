package com.challenge.extractor.service.concurrency;

import com.challenge.extractor.crosscutting.concurrency.ExceptionAwareSupplier;
import com.challenge.extractor.crosscutting.concurrency.Job;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Slf4j
@RequiredArgsConstructor(staticName = "newInstance")
public class ProcessParallelManager {

  private final ExecutorService executorService;
  private List<Job> procedures = new ArrayList<>();

  public void addJob(final String jobKey, final ExceptionAwareSupplier<Boolean> supplier) {

    Job procedure =
        Job.builder()
            .key(jobKey)
            .task(CompletableFuture.supplyAsync(supplier, executorService))
            .build();

    procedures.add(procedure);
  }

  public int[] process() throws ExecutionException, InterruptedException {
    final CompletableFuture[] completableFutures =
        procedures.stream()
            .map(Job::getTask)
            .collect(Collectors.toList())
            .toArray(new CompletableFuture[procedures.size()]);

    return CompletableFuture.allOf(completableFutures)
        .thenApplyAsync(
            processRef -> {
              int[] casesExecuted = new int[2];

              procedures.forEach(
                  p -> {
                    log.debug(p.getKey());
                    if (p.getTask().join()) {
                      casesExecuted[0]++;
                    } else {
                      casesExecuted[1]++;
                    }
                  });
              return casesExecuted;
            })
        .get();
  }
}
