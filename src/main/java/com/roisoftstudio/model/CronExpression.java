package com.roisoftstudio.model;

import com.roisoftstudio.model.expresions.Expression;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CronExpression {

  Expression minute;
  Expression hour;
  Expression dayOfMonth;
  Expression month;
  Expression dayOfWeek;

  String command;
}
