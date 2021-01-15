package com.pd.testCredit.core.exceptions;

import com.pd.testCredit.core.utils.MessagesUtils;

public class TooLateException extends RuntimeException {
    public TooLateException() {
        super(MessagesUtils.msg("error.tooLate"));
    }

}
