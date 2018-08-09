package com.cp.task.services.impl;

import com.cp.task.enums.ErrorMessagesEnum;
import com.cp.task.enums.FileExtensionEnum;
import com.cp.task.enums.OrderFieldsEnum;
import com.cp.task.services.OrderParser;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Parse order files with "csv" extension to tranitional format
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 07.08.2018
 */
@Service
public class CSVOrderParser implements OrderParser {

    @Override
    public List<EnumMap<OrderFieldsEnum, Object>> parse(String fileName) {
        CSVReader reader;
        List<EnumMap<OrderFieldsEnum, Object>> ordersParsedData = new ArrayList<EnumMap<OrderFieldsEnum, Object>>();
        try {
            reader = new CSVReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            ordersParsedData.add(new EnumMap<OrderFieldsEnum, Object>(OrderFieldsEnum.class) {{
                put(OrderFieldsEnum.result, ErrorMessagesEnum.fileNotFound.getErrorMessage());
                put(OrderFieldsEnum.fileName, fileName);
            }});
            return ordersParsedData;
        }

        try {
            String[] line;
            int linePos = 0;
            while ((line = reader.readNext()) != null) {
                ++linePos;
                if (line.length < 4) {
                    EnumMap<OrderFieldsEnum, Object> orderData =
                            new EnumMap<OrderFieldsEnum, Object>(OrderFieldsEnum.class);
                    orderData.put(OrderFieldsEnum.fileName, fileName);
                    orderData.put(OrderFieldsEnum.line, linePos);
                    orderData.put(OrderFieldsEnum.result, String.format(
                            "incorrect csv order line format: lines=%s, must be 4!", line.length));
                    ordersParsedData.add(orderData);
                    continue;
                }
                ordersParsedData.add(parse(fileName, line, linePos));
            }
        } catch (IOException e) {
            ordersParsedData.add(new EnumMap<OrderFieldsEnum, Object>(OrderFieldsEnum.class) {{
                put(OrderFieldsEnum.result, ErrorMessagesEnum.wrongFormat.getErrorMessage());
                put(OrderFieldsEnum.fileName, fileName);
            }});
        }

        return ordersParsedData;
    }

    @Override
    public boolean checkExtension(String fileExtension) {
        return FileExtensionEnum.csv.name().equals(fileExtension);
    }

    private EnumMap<OrderFieldsEnum, Object> parse(String fileName, String[] line, int objPos) {
        return new EnumMap<OrderFieldsEnum, Object>(OrderFieldsEnum.class) {{
            put(OrderFieldsEnum.fileName, fileName);
            put(OrderFieldsEnum.line, objPos);
            put(OrderFieldsEnum.orderId, line[OrderFieldsEnum.orderId.getFieldIndex()]);
            put(OrderFieldsEnum.amount, line[OrderFieldsEnum.amount.getFieldIndex()]);
            put(OrderFieldsEnum.currency, line[OrderFieldsEnum.currency.getFieldIndex()]);
            put(OrderFieldsEnum.comment, line[OrderFieldsEnum.comment.getFieldIndex()]);
        }};
    }
}
