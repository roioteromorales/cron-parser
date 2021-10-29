package com.roisoftstudio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.roisoftstudio.model.CronExpression;
import com.roisoftstudio.model.ExpressionFieldType;
import com.roisoftstudio.model.expresions.NumberExpression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CronExpressionAdapterTest {

  @Mock
  ExpressionParser parser;

  @InjectMocks
  CronExpressionAdapter adapter;

  @Test
  void givenANullInput_shouldThrowException() {
    assertThatThrownBy(() -> adapter.toCronExpression(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Input is blank or null.");
  }

  @Test
  void givenAnEmptyString_shouldThrowException() {
    assertThatThrownBy(() -> adapter.toCronExpression(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Input is blank or null.");
  }

  @Test
  void givenAnInputWithIncorrectNumberOfFields_shouldThrowException() {
    assertThatThrownBy(() -> adapter.toCronExpression("23 3"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Input doest not contain 6 arguments");
  }

  @Test
  void givenAValidInput_shouldGenerateACronExpression() {
    NumberExpression expression1 = NumberExpression.builder().value(1).build();
    when(parser.toExpression("1", ExpressionFieldType.MINUTE))
        .thenReturn(expression1);
    NumberExpression expression2 = NumberExpression.builder().value(2).build();
    when(parser.toExpression("2", ExpressionFieldType.HOUR))
        .thenReturn(expression2);
    NumberExpression expression3 = NumberExpression.builder().value(3).build();
    when(parser.toExpression("3", ExpressionFieldType.DAY_OF_MONTH))
        .thenReturn(expression3);
    NumberExpression expression4 = NumberExpression.builder().value(4).build();
    when(parser.toExpression("4", ExpressionFieldType.MONTH))
        .thenReturn(expression4);
    NumberExpression expression5 = NumberExpression.builder().value(5).build();
    when(parser.toExpression("5", ExpressionFieldType.DAY_OF_WEEK))
        .thenReturn(expression5);

    CronExpression expression = adapter.toCronExpression("1 2 3 4 5 command");

    assertThat(expression.getMinute()).isEqualTo(expression1);
    assertThat(expression.getHour()).isEqualTo(expression2);
    assertThat(expression.getDayOfMonth()).isEqualTo(expression3);
    assertThat(expression.getMonth()).isEqualTo(expression4);
    assertThat(expression.getDayOfWeek()).isEqualTo(expression5);
    assertThat(expression.getCommand()).isEqualTo("command");
  }
}