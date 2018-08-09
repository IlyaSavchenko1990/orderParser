package com.cp.task.enums;

/**
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 08.08.2018
 */
public enum OrderFieldsEnum {
    orderId(0),
    amount(1),
    currency(2),
    comment(3),
    line(4),
    result(5),
    fileName(6);
    private int fieldIndex;

    OrderFieldsEnum(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }


    public int getFieldIndex() {
        return fieldIndex;
    }
}
