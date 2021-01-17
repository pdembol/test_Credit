package com.pd.testCredit.core.utils;

/**
 * Class which helps doing maths - in this case rounds floats to 2 decimal points
 */
public class MathUtils {

    public static float roundToTwoDecimals(float d) {
        return (float) (Math.round(d * 100.00) / 100.00);
    }

}
