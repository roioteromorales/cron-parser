package com.roisoftstudio.service;

import static com.roisoftstudio.model.SpecialCharacters.ASTERISK_CHAR;
import static com.roisoftstudio.model.SpecialCharacters.DIVISION_CHAR;
import static com.roisoftstudio.model.SpecialCharacters.LIST_CHAR;
import static com.roisoftstudio.model.SpecialCharacters.RANGE_CHAR;
import static com.roisoftstudio.service.RangeFactory.fromType;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import com.roisoftstudio.model.ExpressionFieldType;
import com.roisoftstudio.model.Range;
import com.roisoftstudio.model.expresions.AsteriskExpression;
import com.roisoftstudio.model.expresions.DivisionExpression;
import com.roisoftstudio.model.expresions.Expression;
import com.roisoftstudio.model.expresions.ListExpression;
import com.roisoftstudio.model.expresions.NumberExpression;
import com.roisoftstudio.model.expresions.RangeExpression;
import com.roisoftstudio.service.exception.ExpressionParsingException;
import com.roisoftstudio.service.exception.OutOfRangeException;
import org.springframework.stereotype.Service;

@Service
public class ExpressionParser {

  public Expression toExpression(String input, ExpressionFieldType type) {
    if (input.equals(ASTERISK_CHAR)) {
      return AsteriskExpression.builder().value(ASTERISK_CHAR).range(fromType(type)).build();
    }
    if (isNumeric(input)) {
      return NumberExpression.builder().value(parseInt(input)).range(fromType(type)).build();
    }
    if (input.contains(RANGE_CHAR)) {
      return toRangeExpression(input, type);
    }
    if (input.contains(DIVISION_CHAR)) {
      return toDivisionExpression(input, type);
    }
    if (input.contains(LIST_CHAR)) {
      return toListExpression(input, type);
    }

    throw new ExpressionParsingException(format("Cannot parse [%s] into any Expression.", input));
  }

  private Expression toListExpression(String input, ExpressionFieldType type) {
    try {
      return ListExpression.builder()
          .items(stream(input.split(LIST_CHAR))
              .map(Integer::parseInt)
              .collect(toList()))
          .range(fromType(type))
          .build();
    } catch (Exception e) {
      throw new ExpressionParsingException(format("Cannot parse [%s] into List Expression.", input), e);
    }
  }

  private Expression toDivisionExpression(String input, ExpressionFieldType type) {
    try {
      String[] split = input.split(DIVISION_CHAR);
      int divisor = parseInt(split[1]);
      Range range = fromType(type);
      if (isOutsideRange(range, divisor)) {
        throw new OutOfRangeException(format("%s is out of valid range for type %s", input, type));
      }
      return DivisionExpression.builder()
          .dividend(validateNumberOrAsterisk(split[0]))
          .divisor(divisor)
          .range(range).build();
    } catch (Exception e) {
      throw new ExpressionParsingException(format("Cannot parse [%s] into Division Expression.", input), e);
    }
  }

  private RangeExpression toRangeExpression(String input, ExpressionFieldType type) {
    try {
      String[] split = input.split(RANGE_CHAR);
      if(split.length != 2){
        throw new ExpressionParsingException(format("Cannot parse [%s] into Range Expression.", input));
      }
      Range typeRange = fromType(type);
      int from = parseInt(split[0]);
      int to = parseInt(split[1]);
      if (isOutsideRange(typeRange, from) || isOutsideRange(typeRange, to)) {
        throw new OutOfRangeException(format("%s is out of valid range for type %s", input, type));
      }
      return RangeExpression.builder()
          .range(Range.builder().from(from).to(to).build()).build();
    } catch (Exception e) {
      throw new ExpressionParsingException(format("Cannot parse [%s] into Range Expression.", input), e);
    }
  }

  private boolean isOutsideRange(Range typeRange, int from) {
    return from < typeRange.getFrom() || from > typeRange.getTo();
  }

  private String validateNumberOrAsterisk(String input) {
    if (ASTERISK_CHAR.equals(input)) {
      return input;
    }
    if (isNumeric(input)) {
      return input;
    }
    throw new ExpressionParsingException(format("Cannot parse [%s] into Number or Asterisk.", input));
  }
}
