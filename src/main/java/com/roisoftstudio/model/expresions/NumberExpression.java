package com.roisoftstudio.model.expresions;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NumberExpression extends Expression {

  int value;

  @Override
  public List<Integer> getValues() {
    return List.of(value);
  }
}
