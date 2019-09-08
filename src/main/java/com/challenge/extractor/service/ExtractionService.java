package com.challenge.extractor.service;

import com.challenge.extractor.service.concurrency.ProcessParallelManager;
import com.challenge.extractor.service.generator.HashTagGenerator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Slf4j
@Service
@AllArgsConstructor
public class ExtractionService {

  // Regex to validate a valid URL site.
  private static final String URL_REGEX =
      "^(http://|https://)?(www.)?([a-zA-Z0-9-]+)(.[a-z]{2,3})+(/)?.*$";

  private ExecutorService executorService;
  private HashTagGenerator hashTagGenerator;

  public Pair<String, int[]> basicExtraction(final String fileLocation)
      throws IOException, ExecutionException, InterruptedException {
    // All sites to parser and analyse in terms of pattern matcher.
    final List<String> urls = getListFromFile(fileLocation);

    // Component to parallel all calls and processing of HTML bodies.
    final ProcessParallelManager processParallelManager =
        ProcessParallelManager.newInstance(executorService);

    // Manage of output path depends on system.
    final String outputPath =
        fileLocation.substring(
            0, fileLocation.lastIndexOf(SystemUtils.IS_OS_WINDOWS ? '\\' : '/') + 1);

    // Result composed for output path and total of sites analyzed.
    final Pair<String, int[]> result = MutablePair.of(outputPath, new int[2]);

    for (String url : urls) {
      if (url.matches(URL_REGEX)) {
        processParallelManager.addJob(url, () -> hashTagGenerator.generate(url, outputPath));
      } else {
        log.warn("{} not is a valid URL", url);
        result.getRight()[1]++;
      }
    }

    final int[] processResult = processParallelManager.process();
    result.getRight()[0] += processResult[0];
    result.getRight()[1] += processResult[1];

    return result;
  }

  private List<String> getListFromFile(final String path) throws IOException {
    final List<String> list = new ArrayList<>();

    try (FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr)) {

      // Read line by line.
      String line;
      while ((line = br.readLine()) != null) {
        list.add(line);
      }
    }

    return list;
  }
}
