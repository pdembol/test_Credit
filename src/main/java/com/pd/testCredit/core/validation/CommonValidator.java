package com.pd.testCredit.core.validation;

import com.pd.testCredit.core.exceptions.TooLateException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
public class CommonValidator{

    public CommonValidator() {
    }

    public void validateTime(){
        LocalDateTime localNow = LocalDateTime.now(TimeZone.getTimeZone("Europe/Warsaw").toZoneId());
        if(localNow.getHour()<6){
            throw new TooLateException();
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

    public void validateTime(String fieldName, Integer value, Integer min, String message, Errors errors ){
        if(value<min){
            errors.rejectValue(fieldName, "form."+fieldName+".trailed", message);
        }
    }

}
