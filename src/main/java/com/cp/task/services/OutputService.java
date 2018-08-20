package com.cp.task.services;

import com.cp.task.enums.ErrorMessagesEnum;
import com.cp.task.view.OrderView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Prints data and errors to stdout
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 08.08.2018
 */
@Service
public class OutputService {

    public void printOrdersParserResult(List<OrderView> ordersList) {
        try {
            for (OrderView orderView : ordersList) {
                print(new ObjectMapper().writeValueAsString(orderView) + "\r");
            }
        } catch (JsonProcessingException e) {
            print(String.format("OrderView serialization error = %s", e.getMessage()));
        }
    }

    public void printExceptionMessage(String exceptionNote, Throwable throwable) {
        print((exceptionNote != null ? exceptionNote + "; " : "") + String.format("Error: %s", throwable));
    }

    public void filesIsEmpty() {
        print(ErrorMessagesEnum.emptyElements.getErrorMessage());
    }

    public void unsupportedFileFormat(String fileName) {
        print(ErrorMessagesEnum.unsupportedFileFormat.getErrorMessage() + " File name: " + fileName);
    }

    public void wrongFileNameFormat(String fileName) {
        print(ErrorMessagesEnum.wrongFileNameFormat.getErrorMessage() + " File name: " + fileName);
    }

    public void wrongFileFormat(String fileName) {
        print(ErrorMessagesEnum.wrongFormat.getErrorMessage() + " File name: " + fileName);
    }

    public void fileNotFound(String fileName) {
        print(ErrorMessagesEnum.fileNotFound.getErrorMessage() + " File name: " + fileName);
    }

    private void print(String outputMessage) {
        System.out.println(outputMessage);
    }
}
