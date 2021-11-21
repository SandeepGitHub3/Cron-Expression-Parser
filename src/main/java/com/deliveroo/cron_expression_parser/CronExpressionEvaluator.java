package com.deliveroo.cron_expression_parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.deliveroo.cron_expression_parser.Constants.ASTERIX;
import static com.deliveroo.cron_expression_parser.Constants.COMMA;
import static com.deliveroo.cron_expression_parser.Constants.FWD_SLASH;
import static com.deliveroo.cron_expression_parser.Constants.HYPHEN;
import static com.deliveroo.cron_expression_parser.Constants.INCORRECT_CRON_EXPRESSION_ERROR_MSG;
import static com.deliveroo.cron_expression_parser.Constants.SPACE;

public class CronExpressionEvaluator {

    private static String planValueExpressionParser(String cronExpressionString, int startRange, int maxRange) {
        int cronSchedule = Integer.parseInt(cronExpressionString);

        if (cronSchedule < startRange || cronSchedule > maxRange) {
            throw new CronParserValidationException("Incorrect range");
        }
        return String.valueOf(cronSchedule);
    }

    private static String evaluateAstrixExpression(int startRange, int maxRange) {
        StringBuilder cronScheduleString = new StringBuilder();
        while (startRange <= maxRange) {
            cronScheduleString.append(startRange++).append(SPACE);
        }
        return cronScheduleString.deleteCharAt(cronScheduleString.length() - 1).toString();
    }

    private static String evaluateHyphenContainingExpression(String hyphenExpressionString, int maxRange) {
        int start = Integer.parseInt(hyphenExpressionString.substring(0, hyphenExpressionString.indexOf(HYPHEN)));
        int end = Integer.parseInt(hyphenExpressionString.substring(hyphenExpressionString.indexOf(HYPHEN) + 1));

        if (end > maxRange) {
            throw new CronParserValidationException("Incorrect range");
        }
        StringBuilder cronScheduleString = new StringBuilder();

        while (start <= end && start <= maxRange) {
            cronScheduleString.append(start++).append(SPACE);
        }

        return cronScheduleString.deleteCharAt(cronScheduleString.length() - 1).toString();
    }

    private static String evaluateCommaContainingExpression(String commaString, int maxRange) {
        StringBuilder cronScheduleString = new StringBuilder();
        List<Integer> cronScheduleList = Arrays.stream(commaString.split(COMMA))
                .map(Integer::parseInt)
                .filter(cronSchedule -> cronSchedule <= maxRange) //Here I am simply filtering value exceeding Max range. We can choose to throw an error in such cases as well
                .collect(Collectors.toList());

        cronScheduleList.forEach(cronSchedule -> cronScheduleString.append(cronSchedule).append(SPACE));
        return cronScheduleString.deleteCharAt(cronScheduleString.length() - 1).toString();
    }

    private static String evaluateForwardSlashContainingExpression(String inputString, int maxRange) {
        int start;
        int interval;
        StringBuilder cronScheduleString = new StringBuilder();

        int slashIndex = inputString.indexOf(FWD_SLASH);
        char startChar = inputString.charAt(0);

        if (startChar == '*') {
            start = 0;
        } else {
            start = Integer.parseInt(inputString.substring(0, slashIndex));
        }

        interval = Integer.parseInt(inputString.substring(slashIndex + 1));

        while (start < maxRange) {
            cronScheduleString.append(start).append(SPACE);
            start = start + interval;
        }

        return cronScheduleString.deleteCharAt(cronScheduleString.length() - 1).toString();
    }

    public String evaluateCronExpression(String cronExpressionString, int minRange, int maxRange) {
        try {
            if (cronExpressionString.contains(FWD_SLASH)) {
                return evaluateForwardSlashContainingExpression(cronExpressionString, maxRange);
            } else if (cronExpressionString.contains(COMMA)) {
                return evaluateCommaContainingExpression(cronExpressionString, maxRange);
            } else if (cronExpressionString.contains(HYPHEN)) {
                return evaluateHyphenContainingExpression(cronExpressionString, maxRange);
            } else if (cronExpressionString.equals(ASTERIX)) {
                return evaluateAstrixExpression(minRange, maxRange);
            } else {
                return planValueExpressionParser(cronExpressionString, minRange, maxRange);
            }
        } catch (Exception e) {
            return INCORRECT_CRON_EXPRESSION_ERROR_MSG.apply(minRange, maxRange);
        }
    }
}
