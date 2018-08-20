package com.cp.task.services;

import com.cp.task.enums.OrderFieldsEnum;
import com.cp.task.view.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * Process all cycle of parsing, converting and printing files
 * All parts of overall process executes in different threads to
 * improve performance, especially when amount of files is quite large
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 08.08.2018
 */
@Component
public class ParserProcessor {

    @Autowired
    private List<OrderParser> parsers;

    @Autowired
    private OutputService outputService;

    @Autowired
    private OrderViewConverter converter;

    public void process(String[] files) {
        if (files.length < 1) {
            outputService.filesIsEmpty();
            return;
        }

        ExecutorService pool = new ThreadPoolExecutor(
                3,
                25,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        try {
            for (String fileName : files) {
                if (!new File(fileName).exists()) {
                    outputService.fileNotFound(fileName);
                    continue;
                }

                Future<List<EnumMap<OrderFieldsEnum, Object>>> parseFuture = pool.submit(
                        new ParseTask(fileName));
                Future<List<OrderView>> convertFuture = pool.submit(
                        new ConvertTask(parseFuture));

                pool.submit(new PrintTask(convertFuture));
            }
        } finally {
            pool.shutdown();
        }
    }

    private class ParseTask implements Callable<List<EnumMap<OrderFieldsEnum, Object>>> {
        private String fileName;

        ParseTask(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public List<EnumMap<OrderFieldsEnum, Object>> call() {
            for (OrderParser parser : parsers) {
                if (parser.checkFileExtension(fileName)) {
                    return parser.parse(fileName);
                }
            }

            outputService.unsupportedFileFormat(fileName);
            return null;
        }
    }

    private class ConvertTask implements Callable<List<OrderView>> {
        private Future<List<EnumMap<OrderFieldsEnum, Object>>> future;

        public ConvertTask(Future<List<EnumMap<OrderFieldsEnum, Object>>> future) {
            this.future = future;
        }

        @Override
        public List<OrderView> call() throws Exception {
            List<EnumMap<OrderFieldsEnum, Object>> ordersToConvert = future.get();
            if (ordersToConvert == null) return null;

            return converter.convert(ordersToConvert);
        }
    }

    private class PrintTask implements Runnable {
        private Future<List<OrderView>> future;

        public PrintTask(Future<List<OrderView>> future) {
            this.future = future;
        }

        @Override
        public void run() {
            try {
                List<OrderView> ordersToPrint = future.get();
                if (ordersToPrint == null) return;

                outputService.printOrdersParserResult(ordersToPrint);

            } catch (InterruptedException e) {
                System.out.println("printing of orders failed due to thread exception!");
                future.cancel(true);
            } catch (ExecutionException e) {
                System.out.println(String.format("error while printing! Error: %s", e.getMessage()));
            }
        }
    }
}
