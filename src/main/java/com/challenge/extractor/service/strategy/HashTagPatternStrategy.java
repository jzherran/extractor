package com.challenge.extractor.service.strategy;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NoArgsConstructor;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@NoArgsConstructor
public class HashTagPatternStrategy implements MatchPatternStrategy {

  private static final String HASH_TAG_PATTERN =
      "((?!(#[a-fA-F0-9]{3})(\\W|$)|(#[a-fA-F0-9]{6})(\\W|$))( #[a-zäáàëéèíìöóòúùñçA-Z0-9-]+))";

  @Override
  public Set<String> findMatches(final String fullPage) {
    final Pattern pattern = Pattern.compile(HASH_TAG_PATTERN);
    final Matcher matcher = pattern.matcher(fullPage);
    final Set<String> matches = new TreeSet<>();

    while (matcher.find()) {
      matches.add(matcher.group(0));
    }

    return matches;
  }
}
