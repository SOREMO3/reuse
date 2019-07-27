package kr.co.userinsight.model.meta;

import java.io.Serializable;

public enum FieldType implements Serializable {

    STRING,
    I18NSTRING,
    TIME,
    DATETIME,
    DATE,
    DOUBLE,
    MODEL,
    INTEGER,
    LONG,
    FLOAT,
    BOOLEAN,
    MONTHYEAR,
    CURRENCY_KHR,
    CURRENCY_USD,
    CURRENCY_KHR_USD,
    AGE,
    PERCENTAGE,
    TOTAL_AMOUNT,
    DIFF_DAY_PLUS_ONE_DAY,
    YEAR,
    STATUS_RECORD;
}
