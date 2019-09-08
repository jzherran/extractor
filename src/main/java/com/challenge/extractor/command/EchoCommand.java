package com.challenge.extractor.command;

import com.challenge.extractor.shell.ShellHelper;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * @author jzherran
 * @version 0.0.1
 * @since 2019-09-07
 */
@ShellComponent
@AllArgsConstructor
public class EchoCommand {

  private ShellHelper shellHelper;

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String echo(@ShellOption({"-N", "--name"}) String name) {
    String message = String.format("Hello %s!", name);
    shellHelper.print(message.concat(" (Default style message)"));
    shellHelper.printError(message.concat(" (Error style message)"));
    shellHelper.printWarning(message.concat(" (Warning style message)"));
    shellHelper.printInfo(message.concat(" (Info style message)"));
    shellHelper.printSuccess(message.concat(" (Success style message)"));

    String output = shellHelper.getSuccessMessage(message);
    return output.concat(" You are running spring shell extractor application.");
  }
}
