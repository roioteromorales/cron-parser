package com.roisoftstudio.service;

import static com.roisoftstudio.model.ExpressionFieldType.DAY_OF_MONTH;
import static com.roisoftstudio.model.ExpressionFieldType.DAY_OF_WEEK;
import static com.roisoftstudio.model.ExpressionFieldType.HOUR;
import static com.roisoftstudio.model.ExpressionFieldType.MINUTE;
import static com.roisoftstudio.model.ExpressionFieldType.MONTH;
import static com.roisoftstudio.service.RangeFactory.fromType;
import static org.assertj.core.api.Assertions.assertThat;

import com.roisoftstudio.model.Range;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RangeFactoryTest {

  @Test
  void givenMinuteType_shouldReturnCorrectRange() {
    Range range = fromType(MINUTE);

    assertThat(range.getFrom()).isEqualTo(0);
    assertThat(range.getTo()).isEqualTo(59);
  }

  @Test
  void givenHourType_shouldReturnCorrectRange() {
    Range range = fromType(HOUR);

    assertThat(range.getFrom()).isEqualTo(0);
    assertThat(range.getTo()).isEqualTo(59);
  }

  @Test
  void givenDayOfMonthType_shouldReturnCorrectRange() {
    Range range = fromType(DAY_OF_MONTH);

    assertThat(range.getFrom()).isEqualTo(1);
    assertThat(range.getTo()).isEqualTo(31);
  }

  @Test
  void givenMonthType_shouldReturnCorrectRange() {
    Range range = fromType(MONTH);

    assertThat(range.getFrom()).isEqualTo(1);
    assertThat(range.getTo()).isEqualTo(12);
  }

  @Test
  void givenDayOfWeekType_shouldReturnCorrectRange() {
    Range range = fromType(DAY_OF_WEEK);

    assertThat(range.getFrom()).isEqualTo(1);
    assertThat(range.getTo()).isEqualTo(7);
  }
}