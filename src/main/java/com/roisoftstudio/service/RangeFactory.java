package com.roisoftstudio.service;


import static java.lang.String.format;

import com.roisoftstudio.model.ExpressionFieldType;
import com.roisoftstudio.model.Range;
import com.roisoftstudio.service.exception.RangeTypeException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RangeFactory {

  public static Range fromType(ExpressionFieldType type) {
    switch (type) {
      case MINUTE:
      case HOUR:
        return Range.builder().from(0).to(59).build();
      case DAY_OF_MONTH:
        return Range.builder().from(1).to(31).build();//VALIDATE DAY OF MONTH MAX FOR SPECIFIC MONTHS?
      case MONTH:
        return Range.builder().from(1).to(12).build();
      case DAY_OF_WEEK:
        return Range.builder().from(1).to(7).build();
      default:
        throw new RangeTypeException(format("Cannot create a range from type [%s]", type));
    }
  }
}
