package com.challenge.extractor.config;

import com.challenge.extractor.shell.InputReader;
import com.challenge.extractor.shell.PromptColor;
import com.challenge.extractor.shell.ShellHelper;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Parser;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.jline.JLineShellAutoConfiguration;

/**
 * Configuration for spring shell bean.
 *
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Configuration
public class SpringShellConfig {

  @Bean
  public ShellHelper shellHelper(final @Lazy Terminal terminal) {
    return new ShellHelper(terminal);
  }

  @Bean
  public InputReader inputReader(
      final @Lazy Terminal terminal,
      final @Lazy Parser parser,
      final JLineShellAutoConfiguration.CompleterAdapter completer,
      final @Lazy History history,
      final ShellHelper shellHelper) {
    LineReaderBuilder lineReaderBuilder =
        LineReaderBuilder.builder()
            .terminal(terminal)
            .completer(completer)
            .history(history)
            .highlighter(
                (LineReader reader, String buffer) ->
                    new AttributedString(
                        buffer, AttributedStyle.BOLD.foreground(PromptColor.WHITE.getValue())))
            .parser(parser);

    LineReader lineReader = lineReaderBuilder.build();
    lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
    return new InputReader(lineReader, shellHelper);
  }
}
