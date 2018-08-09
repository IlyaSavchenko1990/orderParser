package com.cp.task.enums;

/**
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 09.08.2018
 */
public enum ErrorMessagesEnum {
    emptyElements("elements is empty!"),
    fileNotFound("file not found!"),
    wrongFormat("parsing error - wrong format!"),
    wrongFileNameFormat("wrong file name format!"),
    fieldIsNull("field is null!"),
    fieldNotValid("field not valid!");

    private String errorMessage;

    ErrorMessagesEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
