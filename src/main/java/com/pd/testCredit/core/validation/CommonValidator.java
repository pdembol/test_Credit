package com.pd.testCredit.core.validation;

import com.pd.testCredit.core.utils.MessagesUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Component which stores basic validation methods
 */
@Component
public class CommonValidator {

    private Clock clock;

    public CommonValidator(Clock clock) {
        this.clock = clock;
    }

    public void validateTimeAndAmount(String fieldName, Integer value, Integer max, Errors errors) {
        LocalDateTime localNow = LocalDateTime.now(clock);
        if (null == value) {
            errors.rejectValue(fieldName, "form." + fieldName + ".isRequired",
                    MessagesUtils.msg("error.notEmpty"));
        } else if (localNow.getHour() < 6 && value.equals(max)) {
            errors.rejectValue(fieldName, "form." + fieldName + ".tooLateAndTooMuch",
                    MessagesUtils.msg("error.tooLateAndTooMuch"));
        }
    }

    public static void validateMax(String fieldName, Integer value, Integer max, String message, Errors errors) {
        if (null == value) {
            errors.rejectValue(fieldName, "form." + fieldName + ".isRequired",
                    MessagesUtils.msg("error.notEmpty"));
        } else if (value > max) {
            errors.rejectValue(fieldName, "form." + fieldName + ".exceeded",
                    message);
        }
    }

    public static void validateMin(String fieldName, Integer value, Integer min, String message, Errors errors) {
        if (null == value) {
            errors.rejectValue(fieldName, "form." + fieldName + ".isRequired",
                    MessagesUtils.msg("error.notEmpty"));
        } else if (value < min) {
            errors.rejectValue(fieldName, "form." + fieldName + ".trailed",
                    message);
        }
    }

}
