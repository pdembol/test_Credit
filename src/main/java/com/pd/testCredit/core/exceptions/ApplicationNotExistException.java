package com.pd.testCredit.core.exceptions;

import com.pd.testCredit.core.utils.MessagesUtils;

import java.util.UUID;

/**
 * Exception thrown when requested data not exists in database
 */
public class ApplicationNotExistException extends RuntimeException {
    public ApplicationNotExistException(UUID id) {
        super(MessagesUtils.msg("error.notExist", id));
    }

}
