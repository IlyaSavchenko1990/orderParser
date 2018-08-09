package com.cp.task.unittest;

import com.cp.task.ApplicationConfig;
import com.cp.task.enums.OrderFieldsEnum;
import com.cp.task.services.OutputService;
import com.cp.task.services.impl.CSVOrderParser;
import com.cp.task.view.OrderView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.EnumMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * CSV file order parser unit test
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 08.08.2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class CSVOrderParserTest {

    @Autowired
    private CSVOrderParser parser;

    private File orderCsvFile;

    @Before
    public void initFiles() {
        ClassLoader classLoader = getClass().getClassLoader();
        orderCsvFile = new File(classLoader.getResource("order3.csv").getFile());
    }

    @Test
    public void testCSVOrderFile() {
        List<EnumMap<OrderFieldsEnum, Object>> parsedOrders = parser.parse(orderCsvFile.getAbsolutePath());

        assertTrue("orders list is empty!", parsedOrders != null && !parsedOrders.isEmpty());
        assertTrue("orders list size must be == 6!", parsedOrders.size() == 6);

        assertNotNull("order id is null!", parsedOrders.get(0).get(OrderFieldsEnum.orderId));
        assertNotNull("file name is null!", parsedOrders.get(0).get(OrderFieldsEnum.fileName));
        assertNotNull("file name is null!", parsedOrders.get(0).get(OrderFieldsEnum.fileName));
        assertNotNull("comment is null!", parsedOrders.get(0).get(OrderFieldsEnum.amount));

        assertNotNull("file name is null!", parsedOrders.get(5).get(OrderFieldsEnum.fileName));
        assertNull("comment must be null!", parsedOrders.get(5).get(OrderFieldsEnum.comment));
        assertEquals("order id must be equal to \"AB\"!", "AB", parsedOrders.get(4).get(OrderFieldsEnum.orderId));
    }
}
