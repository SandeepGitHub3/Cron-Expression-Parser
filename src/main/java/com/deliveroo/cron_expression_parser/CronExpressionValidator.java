package com.deliveroo.cron_expression_parser;

import static com.deliveroo.cron_expression_parser.Constants.CORRECT_USAGE_GUIDE_STRING;
import static com.deliveroo.cron_expression_parser.Constants.SPACE;

public class CronExpressionValidator {

    public void validateInput(String[] args) {

        if (args == null || args.length == 0) {
            throw new CronParserValidationException("Null or empty input. " + CORRECT_USAGE_GUIDE_STRING);
        }

        if (args.length > 1 || args[0].split(SPACE).length != 6) {
            throw new CronParserValidationException("Incorrect input. " + CORRECT_USAGE_GUIDE_STRING);
        }
    }
}
