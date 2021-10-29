package com.roisoftstudio.model.expresions;

import com.roisoftstudio.model.Range;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
public abstract class Expression {

  Range range;

  public abstract List<Integer> getValues();
}