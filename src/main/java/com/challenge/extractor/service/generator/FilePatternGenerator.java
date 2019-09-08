package com.challenge.extractor.service.generator;

import com.challenge.extractor.cosscutting.constant.PatternSupported;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
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
@Component
public interface FilePatternGenerator {

  default String getFullPage(final String url) throws IOException {
    final StringBuilder fullResponseBuilder = new StringBuilder();
    final HttpClient instance =
        HttpClientBuilder.create().setConnectionTimeToLive(1, TimeUnit.MINUTES).build();
    final HttpResponse response = instance.execute(new HttpGet(url));
    final BufferedReader in =
        new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      fullResponseBuilder.append(inputLine);
    }

    in.close();
    return fullResponseBuilder
        .toString()
        .replaceAll("(?:\\r\\n|\\r|\\n)", StringUtils.SPACE)
        .replaceAll("<script.*?</script>", StringUtils.SPACE)
        .replaceAll("<style.*?</style>", StringUtils.SPACE)
        .replaceAll("<([^>]+)>", StringUtils.SPACE);
  }

  Boolean generate(String url, String outputPath) throws IOException;

  boolean match(PatternSupported pattern);
}
