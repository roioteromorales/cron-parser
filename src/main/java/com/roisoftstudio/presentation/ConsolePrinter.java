package com.roisoftstudio.presentation;

import com.roisoftstudio.model.CronExpression;
import org.springframework.stereotype.Service;

@Service
public class ConsolePrinter {

  public void showWelcome() {
    System.out.println("Welcome to Roi's Cron Expression Parser v0.0.1\n");
  }

  public void showHelp() {
    System.out.println("\n"
        + "The input should be a string that contains a cron expression and a command to run like this one: \n"
        + "\"*/15 0 1,15 * 1-5 /usr/bin/find\"\n"
        + "\n"
        + "The fields with acceptable values are in this order separated by space: \n"
        + " - minute: 0-59\n"
        + " - hour: 0-23\n"
        + " - day of month: 1-31\n"
        + " - month: 1-12\n"
        + " - day of week: 1-7\n"
        + "\n"
        + "Accepting:\n"
        + " [*/number] - repeating every number of the range. Eg: */15 in minutes (every 15 minutes)\n"
        + " [number] - the specific number in the range. Eg: 0 in hours (on the hour 0)\n"
        + " [number,number] - in every number in the list. Eg: 1,15 in days (every 1st and 15th of the month)*\n"
        + " [*] - all the range. Eg: * in months (in every month)\n"
        + " [number-number] - all the range between the range provided inclusively. Eg: 1-5 in day of the week (from Monday to Friday inclusively)\n"
        + ""
    );
  }

  public void showExpression(CronExpression cronExpression) {
    System.out.println("minute         " + cronExpression.getMinute().getValues());
    System.out.println("hour           " + cronExpression.getHour().getValues());
    System.out.println("day of month   " + cronExpression.getDayOfMonth().getValues());
    System.out.println("month          " + cronExpression.getMonth().getValues());
    System.out.println("day of week    " + cronExpression.getDayOfWeek().getValues());
    System.out.println("command        " + cronExpression.getCommand());
  }
}
