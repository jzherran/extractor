package com.challenge.extractor.shell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jline.reader.LineReader;

/**
 * Component to manage interaction between user and shell.
 *
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@AllArgsConstructor
public class InputReader {

  private LineReader lineReader;
  private ShellHelper shellHelper;

  public String prompt(final String prompt) {
    String answer = "";

    answer = lineReader.readLine(prompt + ": ");

    return answer;
  }

  public String promptWithOptions(
      final String prompt, final String defaultValue, final List<String> optionsAsList) {
    String answer;
    List<String> allowedAnswers = new ArrayList<>(optionsAsList);
    if (!StringUtils.isAllBlank(defaultValue)) {
      allowedAnswers.add(StringUtils.EMPTY);
    }
    do {
      answer =
          lineReader.readLine(
              String.format("%s %s: ", prompt, formatOptions(defaultValue, optionsAsList)));
    } while (!allowedAnswers.contains(answer) && !StringUtils.EMPTY.equals(answer));

    if (StringUtils.isEmpty(answer) && allowedAnswers.contains(StringUtils.EMPTY)) {
      return defaultValue;
    }
    return answer;
  }

  private List<String> formatOptions(final String defaultValue, final List<String> optionsAsList) {
    List<String> result = new ArrayList<>();
    for (String option : optionsAsList) {
      String val = option;
      if (StringUtils.EMPTY.equals(option) || option == null) {
        val = "''";
      }
      if (Objects.equals(defaultValue, option)
          || (Objects.equals(defaultValue, StringUtils.EMPTY) && option == null)) {
        val = shellHelper.getInfoMessage(val);
      }
      result.add(val);
    }
    return result;
  }

  public String selectFromList(
      final String headingMessage,
      final String promptMessage,
      final Map<String, String> options,
      final boolean ignoreCase,
      final String defaultValue) {
    String answer;
    Set<String> allowedAnswers = new HashSet<>(options.keySet());

    if (defaultValue != null && !defaultValue.equals(StringUtils.EMPTY)) {
      allowedAnswers.add(StringUtils.EMPTY);
    }

    shellHelper.print(String.format("%s: ", headingMessage));

    do {
      for (Map.Entry<String, String> option : options.entrySet()) {
        String defaultMarker = null;
        if (option.getKey().equals(defaultValue)) {
          defaultMarker = "*";
        }
        if (defaultMarker != null) {
          shellHelper.printInfo(
              String.format("%s [%s] %s ", defaultMarker, option.getKey(), option.getValue()));
        } else {
          shellHelper.print(String.format("  [%s] %s", option.getKey(), option.getValue()));
        }
      }
      answer = lineReader.readLine(String.format("%s: ", promptMessage));
    } while (!containsString(allowedAnswers, answer, ignoreCase)
        && !StringUtils.EMPTY.equals(answer));

    if (StringUtils.isEmpty(answer) && allowedAnswers.contains(StringUtils.EMPTY)) {
      return defaultValue;
    }
    return answer;
  }

  private boolean containsString(final Set<String> l, final String s, final boolean ignoreCase) {
    if (!ignoreCase) {
      return l.contains(s);
    }
    for (final String value : l) {
      if (value.equalsIgnoreCase(s)) {
        return true;
      }
    }
    return false;
  }
}
