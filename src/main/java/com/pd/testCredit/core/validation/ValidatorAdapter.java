package com.pd.testCredit.core.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public abstract class ValidatorAdapter<T> implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(this.isSupported());
    }

    @Override
    public void validate(Object object, Errors errors) {
        T t = (T) object;
        this.checkValid(t, errors);
    }


    public abstract Class<T> isSupported();


    public abstract void checkValid(T o, Errors errors);

}
