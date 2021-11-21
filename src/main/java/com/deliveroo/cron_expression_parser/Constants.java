package com.deliveroo.cron_expression_parser;

import java.util.function.BiFunction;

public class Constants {

    public static final String CORRECT_USAGE_GUIDE_STRING = "Sample usage is:  $./CronExpressionParser.sh \"*/15 0 1,15 * 1-5 <your command>\"";

    public static final String SPACE = " ";
    public static final String FWD_SLASH = "/";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String ASTERIX = "*";

    public static final String MINUTE = "Minute";
    public static final String HOUR = "Hour";
    public static final String DAY_OF_MONTH = "Day Of Month";
    public static final String MONTH = "Month";
    public static final String DAY_OF_WEEK = "Day Of Week";
    public static final String COMMAND = "Command";
    public static final int OUTPUT_FORMAT_PADDING_LENGTH = 14;
    public static final char OUTPUT_FORMAT_PADDING_CHAR = ' ';

    public static final BiFunction<Integer, Integer, String> INCORRECT_CRON_EXPRESSION_ERROR_MSG = (minRange, maxRange) -> "Incorrect input. Integer range allowed is " + minRange + HYPHEN + maxRange +
            " Allowed Special Characters: " + FWD_SLASH + SPACE + COMMA + SPACE + HYPHEN + SPACE + ASTERIX;

}
