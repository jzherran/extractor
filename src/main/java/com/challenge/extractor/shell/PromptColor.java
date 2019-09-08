package com.challenge.extractor.shell;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumerate to manage the supported colors in this shell.
 *
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Getter
@AllArgsConstructor
public enum PromptColor {
  BLACK(0),
  RED(1),
  GREEN(2),
  YELLOW(3),
  BLUE(4),
  MAGENTA(5),
  CYAN(6),
  WHITE(7),
  BRIGHT(8);

  private final int value;
}
