package com.roisoftstudio;

import static java.lang.String.format;

import com.roisoftstudio.presentation.ConsolePrinter;
import com.roisoftstudio.service.CronExpressionAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired
  public ConsolePrinter printer;
  @Autowired
  public CronExpressionAdapter cronAdapter;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  private static void validateParams(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException(format("Invalid number of parameters [%s]", args.length));
    }
  }

  @Override
  public void run(String... args) throws Exception {
    printer.showWelcome();

    try {
      validateParams(args);
      String input = args[0];
      printer.showExpression(cronAdapter.toCronExpression(input));
    } catch (Exception e) {
      System.out.println("[ERROR] There was an error: " + e.getMessage());
      printer.showHelp();
    }
  }
}
