package com.roisoftstudio.model.expresions;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DivisionExpression extends Expression {

  String dividend;
  int divisor;

  @Override
  public List<Integer> getValues() {
    List<Integer> values = new ArrayList<>();
    for (int i = range.getFrom(); i <= range.getTo(); i+= divisor) {
      values.add(i);
    }
    return values;
  }
}
