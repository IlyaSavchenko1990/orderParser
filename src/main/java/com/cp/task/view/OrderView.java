package com.cp.task.view;

import com.cp.task.enums.CurrencyEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * This class is used to print parsed order data
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 08.08.2018
 */
public class OrderView {

    private int id;//идентификатор ордера
    private int amount;//сумма ордера
    private CurrencyEnum currency;//валюта суммы ордера
    private String comment;//комментарий по ордеру
    private String filename;//имя исходного файла
    private int line;//номер строки исходного файла
    private String result;//результат парсинга записи исходного файла

    public OrderView(String filename) {
        this.filename = filename;
    }

    public OrderView(String filename, int line) {
        this.filename = filename;
        this.line = line;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
