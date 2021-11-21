package com.deliveroo.cron_expression_parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.deliveroo.cron_expression_parser.Constants.CORRECT_USAGE_GUIDE_STRING;

public class CronExpressionValidatorTest {
    private final CronExpressionValidator cronExpressionValidator = new CronExpressionValidator();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void throwExceptionForNullInput() {
        exceptionRule.expect(CronParserValidationException.class);
        exceptionRule.expectMessage("Null or empty input. " + CORRECT_USAGE_GUIDE_STRING);
        cronExpressionValidator.validateInput(null);
    }

    @Test
    public void throwExceptionForArrayWithMoreThanOneElement() {
        exceptionRule.expect(CronParserValidationException.class);
        exceptionRule.expectMessage("Incorrect input. " + CORRECT_USAGE_GUIDE_STRING);
        String[] args = new String[2];
        cronExpressionValidator.validateInput(args);
    }

    @Test
    public void throwExceptionForArrayIncorrectCronExpressionString() {
        exceptionRule.expect(CronParserValidationException.class);
        exceptionRule.expectMessage("Incorrect input. " + CORRECT_USAGE_GUIDE_STRING);
        String[] args = new String[1];
        args[0] = "*/15 0 1,15 * 1-5 /usr/bin/find dummy";
        cronExpressionValidator.validateInput(args);
    }
}