package com.roisoftstudio.model.expresions;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ListExpression extends Expression {

  @Singular
  List<Integer> items;

  @Override
  public List<Integer> getValues() {
    return items;
  }
}
