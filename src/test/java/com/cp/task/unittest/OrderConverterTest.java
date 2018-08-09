package com.cp.task.unittest;

import com.cp.task.ApplicationConfig;
import com.cp.task.enums.OrderFieldsEnum;
import com.cp.task.services.OrderViewConverter;
import com.cp.task.view.OrderView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * OrderView converter unit test
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 09.08.2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class OrderConverterTest {

    @Autowired
    private OrderViewConverter converter;

    private List<EnumMap<OrderFieldsEnum, Object>> parsedObjectsData;
    private int filesCount = 3;
    private Integer[] ordersIds;
    private Integer[] amounts;
    private String[] filesNames;
    private String[] comments;
    private String[] currencyList;

    @Before
    public void initFiles() {
        ordersIds = new Integer[]{1, 2, 15};
        amounts = new Integer[]{100, 15000, 50000};
        filesNames = new String[]{"order1.json", "order2.json", "order3.csv"};
        comments = new String[]{"comment1", "comment2", "comment3"};
        currencyList = new String[]{"RUR", "USD", "FAIL"};

        parsedObjectsData = new ArrayList<EnumMap<OrderFieldsEnum, Object>>();
        for (int i = 0; i < filesCount; i++) {
            EnumMap<OrderFieldsEnum, Object> map = new EnumMap<OrderFieldsEnum, Object>(OrderFieldsEnum.class);

            map.put(OrderFieldsEnum.fileName, filesNames[i]);
            map.put(OrderFieldsEnum.amount, amounts[i]);
            map.put(OrderFieldsEnum.comment, comments[i]);
            map.put(OrderFieldsEnum.orderId, ordersIds[i]);
            map.put(OrderFieldsEnum.currency, currencyList[i]);
            map.put(OrderFieldsEnum.line, i);

            parsedObjectsData.add(map);
        }
    }

    @Test
    public void testConverter() {
        List<OrderView> convertedViews = converter.convert(parsedObjectsData);
        assertNotNull("converted objects is null!", convertedViews);
        assertEquals("list size must be 3!", 3, convertedViews.size());

        for (int i = 0; i < filesCount; i++) {
            OrderView orderView = convertedViews.get(i);
            assertEquals("converted value not equal to original!", orderView.getFilename(), filesNames[i]);
            assertEquals("converted value not equal to original!", orderView.getId(), (int) ordersIds[i]);
            assertEquals("converted value not equal to original!", orderView.getAmount(), (int) amounts[i]);
            assertEquals("converted value not equal to original!", orderView.getComment(), comments[i]);

            if (i == 2) {
                assertNull("currency must be null!", orderView.getCurrency());
                assertNotEquals("result must != OK", orderView.getCurrency());
            } else {
                assertEquals("converted value not equal to original!", orderView.getCurrency().name(), currencyList[i]);
                assertEquals("result must == OK", "OK", orderView.getResult());
            }
        }
    }
}
