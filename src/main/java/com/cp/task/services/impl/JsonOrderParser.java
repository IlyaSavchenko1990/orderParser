package com.cp.task.services.impl;

import com.cp.task.enums.ErrorMessagesEnum;
import com.cp.task.enums.FileExtensionEnum;
import com.cp.task.enums.OrderFieldsEnum;
import com.cp.task.services.OrderParser;
import com.cp.task.services.OutputService;
import com.cp.task.view.OrderView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;

/**
 * Parse order files with "json" extension to tranitional format
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 07.08.2018
 */
@Service
public class JsonOrderParser implements OrderParser {

    @Autowired
    private OutputService outputService;

    @Override
    public List<EnumMap<OrderFieldsEnum, Object>> parse(String fileName) {
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader(fileName));
        } catch (Exception e) {
            outputService.printExceptionMessage(String.format("Error while parsing file \"%s\"", fileName), e);
            return null;
        }

        List<EnumMap<OrderFieldsEnum, Object>> ordersParsedData = new ArrayList<EnumMap<OrderFieldsEnum, Object>>();
        if (obj instanceof JSONObject) {
            ordersParsedData.add(parse(fileName, (JSONObject) obj, 1));

        } else if (obj instanceof JSONArray) {
            Iterator iterator = ((JSONArray) obj).iterator();
            int linePos = 0;
            while (iterator.hasNext()) {
                ++linePos;
                ordersParsedData.add(parse(fileName, (JSONObject) iterator.next(), linePos));
            }
        }

        return ordersParsedData;
    }

    @Override
    public boolean checkFileExtension(String fileName) {
        return !(fileName == null || fileName.equals("")) && fileName.endsWith(FileExtensionEnum.json.name());
    }

    private EnumMap<OrderFieldsEnum, Object> parse(String fileName, JSONObject jsonObject, int objPos) {
        return new EnumMap<OrderFieldsEnum, Object>(OrderFieldsEnum.class) {{
            put(OrderFieldsEnum.fileName, fileName);
            put(OrderFieldsEnum.line, objPos);
            put(OrderFieldsEnum.orderId, jsonObject.get(OrderFieldsEnum.orderId.name()));
            put(OrderFieldsEnum.amount, jsonObject.get(OrderFieldsEnum.amount.name()));
            put(OrderFieldsEnum.currency, jsonObject.get(OrderFieldsEnum.currency.name()));
            put(OrderFieldsEnum.comment, jsonObject.get(OrderFieldsEnum.comment.name()));
        }};
    }
}
