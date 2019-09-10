package com.challenge.extractor.crosscutting.constant;

import com.challenge.extractor.service.strategy.HashTagPatternStrategy;
import com.challenge.extractor.service.strategy.MatchPatternStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerate for supported patterns.
 *
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PatternSupported {
  HASH_TAG(new HashTagPatternStrategy()),
  MENTION(), // TODO This should be use the new MatchPatternStrategy implemented for twitter mention
  PROPER_NAME(); // TODO This should be use the new MatchPatternStrategy implemented for proper name

  private MatchPatternStrategy strategy;
}
