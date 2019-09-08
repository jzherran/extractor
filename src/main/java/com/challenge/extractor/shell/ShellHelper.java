package com.challenge.extractor.shell;

import lombok.Getter;
import lombok.Setter;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;

/**
 * Helper class for manage color for each type of message supported in this shell component.
 *
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Getter
@Setter
public class ShellHelper {

  @Value("${shell.out.info}")
  private String infoColor;

  @Value("${shell.out.success}")
  private String successColor;

  @Value("${shell.out.warning}")
  private String warningColor;

  @Value("${shell.out.error}")
  private String errorColor;

  private Terminal terminal;

  public ShellHelper(final Terminal terminal) {
    this.terminal = terminal;
  }

  private String getColored(final String message, final PromptColor color) {
    return (new AttributedStringBuilder())
        .append(message, AttributedStyle.DEFAULT.foreground(color.getValue()))
        .toAnsi();
  }

  private void print(final String message, final PromptColor color) {
    if (color != null) {
      terminal.writer().println(getColored(message, color));
    } else {
      terminal.writer().println(message);
    }
    terminal.flush();
  }

  public String getErrorMessage(final String message) {
    return getColored(message, PromptColor.valueOf(errorColor));
  }

  public String getInfoMessage(final String message) {
    return getColored(message, PromptColor.valueOf(infoColor));
  }

  public String getSuccessMessage(final String message) {
    return getColored(message, PromptColor.valueOf(successColor));
  }

  public String getWarningMessage(final String message) {
    return getColored(message, PromptColor.valueOf(warningColor));
  }

  public void print(final String message) {
    print(message, null);
  }

  public void printSuccess(final String message) {
    print(message, PromptColor.valueOf(successColor));
  }

  public void printInfo(final String message) {
    print(message, PromptColor.valueOf(infoColor));
  }

  public void printWarning(final String message) {
    print(message, PromptColor.valueOf(warningColor));
  }

  public void printError(final String message) {
    print(message, PromptColor.valueOf(errorColor));
  }
}
