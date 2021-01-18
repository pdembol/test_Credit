package com.pd.testCredit.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class FormErrors {
    private List<SimpleFieldError> errors;

    public FormErrors(Errors errors) {
        List<SimpleFieldError> list = new ArrayList<>();

        for (ObjectError error : errors.getAllErrors()) {
            String fieldName = null;
            if (error instanceof FieldError) {
                fieldName = ((FieldError) error).getField();
                list.add(new SimpleFieldError(fieldName, error.getDefaultMessage()));
            }
        }
        this.errors = list;
    }

}
