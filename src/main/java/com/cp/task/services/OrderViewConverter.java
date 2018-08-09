package com.cp.task.services;

import com.cp.task.enums.CurrencyEnum;
import com.cp.task.enums.ErrorMessagesEnum;
import com.cp.task.enums.OrderFieldsEnum;
import com.cp.task.view.OrderView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Converts parsed data in transitional format into OrderView class
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 09.08.2018
 */
@Component
public class OrderViewConverter {

    public List<OrderView> convert(List<EnumMap<OrderFieldsEnum, Object>> parsedObjectsData) {
        ArrayList<OrderView> orderViews = new ArrayList<>();
        for (EnumMap<OrderFieldsEnum, Object> parsedData : parsedObjectsData) {
            OrderView orderView = new OrderView(
                    (String) parsedData.get(OrderFieldsEnum.fileName),
                    (Integer) parsedData.get(OrderFieldsEnum.line));

            StringBuilder result = new StringBuilder();
            orderView.setId(getIntValue(OrderFieldsEnum.orderId, parsedData, result));
            orderView.setAmount(getIntValue(OrderFieldsEnum.amount, parsedData, result));
            orderView.setCurrency(getCurrency(parsedData, result));
            orderView.setComment(getStringValue(OrderFieldsEnum.comment, parsedData, result));
            orderView.setResult(result.length() > 0 ? result.toString() : "OK");

            orderViews.add(orderView);
        }

        return orderViews;
    }

    private int getIntValue(OrderFieldsEnum fieldsName,
                            EnumMap<OrderFieldsEnum, Object> parsedData, StringBuilder result) {
        Object fieldValue = parsedData.get(fieldsName);
        if (fieldValue == null) {
            checkResult(result);
            result.append(fieldsName.name()).append(" ").append(ErrorMessagesEnum.fieldIsNull.getErrorMessage());
            return -1;
        } else if (fieldValue instanceof Integer) {
            return (int) fieldValue;
        } else if (fieldValue instanceof Long) {
            return ((Long) fieldValue).intValue();
        } else if ((fieldValue instanceof String) && StringUtils.isNumeric((CharSequence) fieldValue)) {
            return new Integer((String) fieldValue);
        } else {
            checkResult(result);
            result.append(fieldsName.name()).append(" ")
                    .append(ErrorMessagesEnum.fieldNotValid.getErrorMessage()).append(": ").append(fieldValue);
            return -1;
        }
    }

    private String getStringValue(OrderFieldsEnum fieldsName,
                                  EnumMap<OrderFieldsEnum, Object> parsedData, StringBuilder result) {
        Object fieldValue = parsedData.get(fieldsName);
        if (fieldValue == null) {
            checkResult(result);
            result.append(fieldsName.name()).append(" ").append(ErrorMessagesEnum.fieldIsNull.getErrorMessage());
            return null;
        } else if (fieldValue instanceof String) {
            return (String) fieldValue;
        } else {
            return fieldValue + "";
        }
    }

    private CurrencyEnum getCurrency(EnumMap<OrderFieldsEnum, Object> parsedData, StringBuilder result) {
        String currencyString = getStringValue(OrderFieldsEnum.currency, parsedData, result);
        if (currencyString == null) return null;

        CurrencyEnum currency = CurrencyEnum.findByName(currencyString);
        if (currency == null) {
            checkResult(result);
            result.append(OrderFieldsEnum.currency.name()).append(" ")
                    .append(ErrorMessagesEnum.fieldNotValid.getErrorMessage()).append(": ").append(currencyString);
        }

        return currency;
    }

    private void checkResult(StringBuilder result) {
        if (result.length() > 0) result.append(":").append(" ");
    }
}
