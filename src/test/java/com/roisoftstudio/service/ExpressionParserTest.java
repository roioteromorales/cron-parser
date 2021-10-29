package com.roisoftstudio.service;

import static com.roisoftstudio.model.ExpressionFieldType.DAY_OF_WEEK;
import static com.roisoftstudio.service.RangeFactory.fromType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.roisoftstudio.model.ExpressionFieldType;
import com.roisoftstudio.model.expresions.AsteriskExpression;
import com.roisoftstudio.model.expresions.DivisionExpression;
import com.roisoftstudio.model.expresions.Expression;
import com.roisoftstudio.model.expresions.ListExpression;
import com.roisoftstudio.model.expresions.NumberExpression;
import com.roisoftstudio.model.expresions.RangeExpression;
import com.roisoftstudio.service.exception.ExpressionParsingException;
import com.roisoftstudio.service.exception.OutOfRangeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExpressionParserTest {

  public static final ExpressionFieldType TYPE = DAY_OF_WEEK;
  @InjectMocks
  ExpressionParser parser;

  @Test
  void givenAnAsteriskCharInput_shouldReturnAsteriskExpression() {

    Expression expression = parser.toExpression("*", TYPE);

    assertThat(expression).isInstanceOf(AsteriskExpression.class);
    AsteriskExpression asteriskExpression = (AsteriskExpression) expression;
    assertThat(asteriskExpression.getValue()).isEqualTo("*");
    assertThat(asteriskExpression.getRange()).isEqualTo(fromType(TYPE));
  }

  @Test
  void givenANumericCharInput_shouldReturnNumberExpression() {

    Expression expression = parser.toExpression("0", TYPE);

    assertThat(expression).isInstanceOf(NumberExpression.class);
    NumberExpression numberExpression = (NumberExpression) expression;
    assertThat(numberExpression.getValue()).isEqualTo(0);
    assertThat(numberExpression.getRange()).isEqualTo(fromType(TYPE));
  }

  @Test
  void givenAnInputWithRangeChar_shouldReturnRangeExpression() {

    Expression expression = parser.toExpression("1-5", TYPE);

    assertThat(expression).isInstanceOf(RangeExpression.class);
    RangeExpression rangeExpression = (RangeExpression) expression;
    assertThat(rangeExpression.getRange().getFrom()).isEqualTo(1);
    assertThat(rangeExpression.getRange().getTo()).isEqualTo(5);
  }

  @Test
  void givenAnInputWithRangeCharAndInvalidFormat_shouldThrowParsingException() {
    assertThatThrownBy(() -> parser.toExpression("1-", TYPE))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("1-3-1", TYPE))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("1--1", TYPE))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("-", TYPE))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("-1-", TYPE))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("1-*", TYPE))
        .isInstanceOf(ExpressionParsingException.class);
  }

  @Test
  void givenAnInputWithWithRangeCharAndAnInvalidRange_shouldThrowOutOfRangeException() {
    assertThatThrownBy(() -> parser.toExpression("0-1", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class)
        .hasCauseInstanceOf(OutOfRangeException.class);
    assertThatThrownBy(() -> parser.toExpression("1-9", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class)
        .hasCauseInstanceOf(OutOfRangeException.class);
  }

  @Test
  void givenAnInputWithDivisionChar_shouldReturnDivisionExpression() {

    Expression expression = parser.toExpression("1/5", TYPE);

    assertThat(expression).isInstanceOf(DivisionExpression.class);
    DivisionExpression divisionExpression = (DivisionExpression) expression;
    assertThat(divisionExpression.getDividend()).isEqualTo("1");
    assertThat(divisionExpression.getDivisor()).isEqualTo(5);
    assertThat(divisionExpression.getRange()).isEqualTo(fromType(TYPE));
  }

  @Test
  void givenAnInputWithDivisionCharAndOutOfRangeDivisor_shouldThrowOutOfRangeException() {
    assertThatThrownBy(() -> parser.toExpression("1/0", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class)
        .hasCauseInstanceOf(OutOfRangeException.class);
    assertThatThrownBy(() -> parser.toExpression("1/9", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class)
        .hasCauseInstanceOf(OutOfRangeException.class);
  }

  @Test
  void givenAnInputWithDivisionCharAndInvalidDividend_shouldExpressionParsingException() {
    assertThatThrownBy(() -> parser.toExpression("f/1", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("/1", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("/", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("//", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("**/1", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class);
  }

  @Test
  void givenAListCharInput_shouldReturnListExpression() {

    Expression expression = parser.toExpression("1,2,3", TYPE);

    assertThat(expression).isInstanceOf(ListExpression.class);
    ListExpression listExpression = (ListExpression) expression;
    assertThat(listExpression.getItems()).containsExactlyInAnyOrder(1,2,3);
    assertThat(listExpression.getRange()).isEqualTo(fromType(TYPE));
  }

  @Test
  void givenAListCharInputWithInvalidItems_shouldThrowException() {
    assertThatThrownBy(() -> parser.toExpression("1,f", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class);
    assertThatThrownBy(() -> parser.toExpression("1,,2", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class);
  }

  @Test
  void givenAnInvalidInput_shouldThrowException() {
    assertThatThrownBy(() -> parser.toExpression("f", DAY_OF_WEEK))
        .isInstanceOf(ExpressionParsingException.class);
  }

}