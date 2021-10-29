package com.roisoftstudio.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Range {

  int from;
  int to;
}
