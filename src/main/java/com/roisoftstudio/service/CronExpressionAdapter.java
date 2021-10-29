package com.roisoftstudio.service;

import static com.roisoftstudio.model.ExpressionFieldType.DAY_OF_MONTH;
import static com.roisoftstudio.model.ExpressionFieldType.DAY_OF_WEEK;
import static com.roisoftstudio.model.ExpressionFieldType.HOUR;
import static com.roisoftstudio.model.ExpressionFieldType.MINUTE;
import static com.roisoftstudio.model.ExpressionFieldType.MONTH;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.split;

import com.roisoftstudio.model.CronExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CronExpressionAdapter {

  public static final char WHITESPACE_SEPARATOR = ' ';
  private final ExpressionParser parser;

  public CronExpression toCronExpression(String input) {
    validateNotNull(input);
    String[] split = split(input, WHITESPACE_SEPARATOR);
    validateLength(input, split);

    return CronExpression.builder()
        .minute(parser.toExpression(split[0], MINUTE))
        .hour(parser.toExpression(split[1], HOUR))
        .dayOfMonth(parser.toExpression(split[2], DAY_OF_MONTH))
        .month(parser.toExpression(split[3], MONTH))
        .dayOfWeek(parser.toExpression(split[4], DAY_OF_WEEK))
        .command(parseCommand(split[5]))
        .build();
  }

  private void validateNotNull(String input) {
    if (isBlank(input)) {
      throw new IllegalArgumentException("Input is blank or null.");
    }
  }

  private void validateLength(String input, String[] split) {
    if (split.length != 6) {
      throw new IllegalArgumentException(
          format("Input doest not contain 6 arguments. Length: %s Input: %s", split.length, input));
    }
  }

  private String parseCommand(String command) {
    return command;
  }


}
