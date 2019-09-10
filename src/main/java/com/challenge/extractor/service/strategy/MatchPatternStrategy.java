package com.challenge.extractor.service.strategy;

import java.util.Set;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-00
 */
public interface MatchPatternStrategy {

  Set<String> findMatches(String fullPage);
}
