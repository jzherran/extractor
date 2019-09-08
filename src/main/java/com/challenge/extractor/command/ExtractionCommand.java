package com.challenge.extractor.command;

import com.challenge.extractor.service.ExtractionService;
import com.challenge.extractor.shell.ShellHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@Slf4j
@ShellComponent
@AllArgsConstructor
@SuppressWarnings("unused")
public class ExtractionCommand {

  private ShellHelper shellHelper;
  private ExtractionService extractionService;

  @ShellMethod("Extract specific data from a list of URL loaded in a file")
  public void extract(@ShellOption({"-fl", "--file-location"}) final String fileLocation) {
    try {
      final Pair<String, int[]> dataResult = extractionService.basicExtraction(fileLocation);
      shellHelper.printSuccess(
          String.format(
              "Show the inputs in this path %s with the matches found in all sites",
              dataResult.getKey()));
      shellHelper.printInfo(String.format("Cases success :: %d", dataResult.getValue()[0]));
      shellHelper.printWarning(String.format("Cases failed :: %d", dataResult.getValue()[1]));
    } catch (Exception e) {
      shellHelper.printError("Error in basic extraction for the file selected");
    }
  }
}
