package com.challenge.extractor.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Component
public class ExtractorPromptProvider implements PromptProvider {

  @Override
  public AttributedString getPrompt() {
    return new AttributedString(
        "EXTRACTOR:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
  }
}
