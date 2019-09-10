package com.challenge.extractor.service.generator;

import com.challenge.extractor.crosscutting.constant.PatternSupported;
import com.challenge.extractor.service.strategy.MatchPatternStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Slf4j
@Component
@AllArgsConstructor
public class FileGenerator {

  public Boolean generate(
      final String url, final String outputPath, final List<PatternSupported> patterns) {
    try {
      final String fullPage = getFullPage(url);

      for (PatternSupported pattern : patterns) {
        final MatchPatternStrategy strategy = pattern.getStrategy();

        if (Objects.isNull(strategy)) {
          log.debug("pattern {} not implemented", pattern.name());
          continue;
        }

        final Set<String> matches = strategy.findMatches(fullPage);

        log.debug("site: {} - length: {}", url, fullPage.length());

        if (!matches.isEmpty()) {
          final String newFilePath =
              String.format(
                  "%s[%s][%s].txt", outputPath, pattern.name(), url.replaceAll("(://|\\.|/)", "_"));

          try (PrintWriter writer = new PrintWriter(newFilePath, "UTF-8")) {
            matches.forEach(writer::println);
          }
        }

        log.debug("{} matches found in url {} for pattern {}", matches.size(), url, pattern.name());
      }

      return Boolean.TRUE;
    } catch (Exception e) {
      log.error("{} is an unknown host", e.getMessage());
    }
    return Boolean.FALSE;
  }

  private String getFullPage(final String url) throws IOException {
    final StringBuilder fullResponseBuilder = new StringBuilder();
    final HttpClient instance =
        HttpClientBuilder.create().setConnectionTimeToLive(1, TimeUnit.MINUTES).build();
    final HttpResponse response = instance.execute(new HttpGet(url));
    final BufferedReader br =
        new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

    fullResponseBuilder.append(br.lines().collect(Collectors.joining()));

    br.close();
    return fullResponseBuilder
        .toString()
        .replaceAll("(?:\\r\\n|\\r|\\n)", StringUtils.SPACE)
        .replaceAll("<script.*?</script>", StringUtils.SPACE)
        .replaceAll("<style.*?</style>", StringUtils.SPACE)
        .replaceAll("<([^>]+)>", StringUtils.SPACE);
  }
}
