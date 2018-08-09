package com.cp.task.enums;

/**
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 08.08.2018
 */
public enum CurrencyEnum {
    RUR, EUR, USD;

    public static CurrencyEnum findByName(String name) {
        for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (currencyEnum.name().equals(name)) return currencyEnum;
        }

        return null;
    }
}
