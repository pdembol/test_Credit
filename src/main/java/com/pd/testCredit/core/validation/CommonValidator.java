package com.pd.testCredit.core.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * Component which stores basic validation methods
 */

@Component
public class CommonValidator{

    public CommonValidator() {
    }

    public void validateTimeAndAmount(String fieldName, Integer value, Integer max, String message, Errors errors ){
        LocalDateTime localNow = LocalDateTime.now(TimeZone.getTimeZone("Europe/Warsaw").toZoneId());
        if(localNow.getHour()<6 && value.equals(max)){
            errors.rejectValue(fieldName, "form."+fieldName+".tooLateAndTooMuch", message);
        }
    }

    public void validateMax(String fieldName, Integer value, Integer max, String message, Errors errors ){
        if(value>max){
            errors.rejectValue(fieldName, "form."+fieldName+".exceeded", message);
        }
    }

    public void validateMin(String fieldName, Integer value, Integer min, String message, Errors errors ){
        if(value<min){
            errors.rejectValue(fieldName, "form."+fieldName+".trailed", message);
        }
    }

}
