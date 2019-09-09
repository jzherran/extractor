package com.challenge.extractor.service.generator;

import com.challenge.extractor.crosscutting.constant.PatternSupported;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Slf4j
@Component
@AllArgsConstructor
public class HashTagGenerator implements FilePatternGenerator {

  private static final String HASH_TAG_PATTERN =
      "((?!(#[a-fA-F0-9]{3})(\\W|$)|(#[a-fA-F0-9]{6})(\\W|$))( #[a-zäáàëéèíìöóòúùñçA-Z0-9-]+))";

  @Override
  public boolean match(final PatternSupported pattern) {
    return PatternSupported.HASH_TAG.equals(pattern);
  }

  @Override
  public Boolean generate(final String url, final String outputPath) {
    try {
      final Pattern pattern = Pattern.compile(HASH_TAG_PATTERN);
      final String fullPage = getFullPage(url);
      final Matcher matcher = pattern.matcher(fullPage);
      final Set<String> matches = new TreeSet<>();

      log.debug("site: {} - length: {}", url, fullPage.length());

      while (matcher.find()) {
        matches.add(matcher.group(0));
      }

      if (!matches.isEmpty()) {
        final String newFilePath =
            String.format("%s%s.txt", outputPath, url.replaceAll("(://|\\.|/)", "_"));

        try (PrintWriter writer = new PrintWriter(newFilePath, "UTF-8")) {
          matches.forEach(writer::println);
        }
      }

      log.debug("{} matches found in url {}", matches.size(), url);
      return Boolean.TRUE;
    } catch (Exception e) {
      log.error("{} is an unknown host", e.getMessage());
    }
    return Boolean.FALSE;
  }
}
