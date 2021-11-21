package com.deliveroo.cron_expression_parser;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.deliveroo.cron_expression_parser.Constants.COMMAND;
import static com.deliveroo.cron_expression_parser.Constants.DAY_OF_MONTH;
import static com.deliveroo.cron_expression_parser.Constants.DAY_OF_WEEK;
import static com.deliveroo.cron_expression_parser.Constants.HOUR;
import static com.deliveroo.cron_expression_parser.Constants.MINUTE;
import static com.deliveroo.cron_expression_parser.Constants.MONTH;
import static com.deliveroo.cron_expression_parser.Constants.OUTPUT_FORMAT_PADDING_CHAR;
import static com.deliveroo.cron_expression_parser.Constants.OUTPUT_FORMAT_PADDING_LENGTH;
import static com.deliveroo.cron_expression_parser.Constants.SPACE;
import static java.lang.System.exit;

public class CronExpressionParser {

    public static void main(String[] args) {
        validateInput(args);
        Map<String, String> evaluatedCronExpressionMap = evaluateCronExpression(args);
        printOutput(evaluatedCronExpressionMap);
    }

    private static void printOutput(Map<String, String> evaluatedCronExpressionMap) {
        System.out.println(Strings.padEnd(MINUTE, OUTPUT_FORMAT_PADDING_LENGTH, OUTPUT_FORMAT_PADDING_CHAR) + evaluatedCronExpressionMap.get(MINUTE));
        System.out.println(Strings.padEnd(HOUR, OUTPUT_FORMAT_PADDING_LENGTH, OUTPUT_FORMAT_PADDING_CHAR) + evaluatedCronExpressionMap.get(HOUR));
        System.out.println(Strings.padEnd(DAY_OF_MONTH, OUTPUT_FORMAT_PADDING_LENGTH, OUTPUT_FORMAT_PADDING_CHAR) + evaluatedCronExpressionMap.get(DAY_OF_MONTH));
        System.out.println(Strings.padEnd(MONTH, OUTPUT_FORMAT_PADDING_LENGTH, OUTPUT_FORMAT_PADDING_CHAR) + evaluatedCronExpressionMap.get(MONTH));
        System.out.println(Strings.padEnd(DAY_OF_WEEK, OUTPUT_FORMAT_PADDING_LENGTH, OUTPUT_FORMAT_PADDING_CHAR) + evaluatedCronExpressionMap.get(DAY_OF_WEEK));
        System.out.println(Strings.padEnd(COMMAND, OUTPUT_FORMAT_PADDING_LENGTH, OUTPUT_FORMAT_PADDING_CHAR) + evaluatedCronExpressionMap.get(COMMAND));
    }

    private static void validateInput(String[] args) {
        CronExpressionValidator cronExpressionValidator = new CronExpressionValidator();
        try {
            cronExpressionValidator.validateInput(args);
        } catch (CronParserValidationException cronParserValidationException) {
            System.out.println(cronParserValidationException.getMessage());
            exit(0);
        }
    }

    private static Map<String, String> evaluateCronExpression(String[] args) {
        CronExpressionEvaluator cronExpressionEvaluator = new CronExpressionEvaluator();
        String cronExpressionInputString = args[0];
        String[] cronExpressionArray = cronExpressionInputString.split(SPACE);
        return ImmutableMap.<String, String>builder()
                .put(MINUTE, cronExpressionEvaluator.evaluateCronExpression(cronExpressionArray[0], 0, 60))
                .put(HOUR, cronExpressionEvaluator.evaluateCronExpression(cronExpressionArray[1], 0, 12))
                .put(DAY_OF_MONTH, cronExpressionEvaluator.evaluateCronExpression(cronExpressionArray[2], 1, 31)) //TODO Based on the month we can decide maxRange if its 30 or 31
                .put(MONTH, cronExpressionEvaluator.evaluateCronExpression(cronExpressionArray[3], 1, 12))
                .put(DAY_OF_WEEK, cronExpressionEvaluator.evaluateCronExpression(cronExpressionArray[4], 1, 7))
                .put(COMMAND, cronExpressionArray[5])
                .build();
    }

}
