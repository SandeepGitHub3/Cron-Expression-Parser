package com.deliveroo.cron_expression_parser;

import org.junit.Test;

import static com.deliveroo.cron_expression_parser.Constants.INCORRECT_CRON_EXPRESSION_ERROR_MSG;
import static org.junit.Assert.assertEquals;

public class CronExpressionEvaluatorTest {
    private final CronExpressionEvaluator cronExpressionEvaluator = new CronExpressionEvaluator();

    @Test
    public void testCronExpressionContainingFwdSlash() {
        assertEquals("0 15 30 45", cronExpressionEvaluator.evaluateCronExpression("*/15", 0, 60));
    }

    @Test
    public void testCronExpressionContainingFwdSlashContainingSeedValue() {
        assertEquals("30 45", cronExpressionEvaluator.evaluateCronExpression("30/15", 0, 60));
    }

    @Test
    public void testCronExpressionContainingComma() {
        assertEquals("1 15", cronExpressionEvaluator.evaluateCronExpression("1,15", 0, 60));
    }

    @Test
    public void testCronExpressionContainingCommaFilterOutOfRangeValues() {
        assertEquals("1", cronExpressionEvaluator.evaluateCronExpression("1,75", 0, 60));
    }

    @Test
    public void testCronExpressionContainingCommaIncorrectFormat() {
        assertEquals(INCORRECT_CRON_EXPRESSION_ERROR_MSG.apply(0, 60), cronExpressionEvaluator.evaluateCronExpression("1,,15", 0, 60));
    }

    @Test
    public void testCronExpressionContainingHyphen() {
        assertEquals("1 2 3 4 5", cronExpressionEvaluator.evaluateCronExpression("1-5", 0, 60));
    }

    @Test
    public void testCronExpressionContainingHyphenExceedingRange() {
        assertEquals(INCORRECT_CRON_EXPRESSION_ERROR_MSG.apply(0, 60), cronExpressionEvaluator.evaluateCronExpression("55-65", 0, 60));
    }

    @Test
    public void testCronExpressionContainingAstrixOnly() {
        assertEquals("1 2 3 4 5", cronExpressionEvaluator.evaluateCronExpression("*", 1, 5));
    }

    @Test
    public void testCronExpressionWithPlainValues() {
        assertEquals("4", cronExpressionEvaluator.evaluateCronExpression("4", 1, 5));
    }

    @Test
    public void testCronExpressionWithPlainValuesLessThanRange() {
        assertEquals(INCORRECT_CRON_EXPRESSION_ERROR_MSG.apply(1, 5), cronExpressionEvaluator.evaluateCronExpression("0", 1, 5));
    }

    @Test
    public void testCronExpressionWithPlainValueMoreThanRange() {
        assertEquals(INCORRECT_CRON_EXPRESSION_ERROR_MSG.apply(1, 5), cronExpressionEvaluator.evaluateCronExpression("8", 1, 5));
    }
}