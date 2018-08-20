package com.cp.task.services.impl;

import com.cp.task.enums.FileExtensionEnum;
import com.cp.task.enums.OrderFieldsEnum;
import com.cp.task.services.OrderParser;
import com.cp.task.services.OutputService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
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

    @Autowired
    OutputService outputService;

    @Override
    public List<EnumMap<OrderFieldsEnum, Object>> parse(String fileName) {
        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            List<EnumMap<OrderFieldsEnum, Object>> ordersParsedData = new ArrayList<EnumMap<OrderFieldsEnum, Object>>();
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

            return ordersParsedData;

        } catch (Exception e) {
            outputService.printExceptionMessage(String.format("Error while parsing file \"%s\"", fileName), e);
            return null;
        }
    }

    @Override
    public boolean checkFileExtension(String fileName) {
        return !(fileName == null || fileName.equals("")) && fileName.endsWith(FileExtensionEnum.csv.name());
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
