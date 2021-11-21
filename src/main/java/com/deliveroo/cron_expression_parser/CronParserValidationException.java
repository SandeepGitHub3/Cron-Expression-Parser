package com.deliveroo.cron_expression_parser;

public class CronParserValidationException extends RuntimeException {

    public CronParserValidationException(final String message) {
        super(message);
    }
}
