package com.cp.task;

import com.cp.task.services.ParserProcessor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 07.08.2018
 */
@Component
public class Main {

    @Autowired
    private ParserProcessor parserProcessor;

    public static void main(String[] args) throws IOException, ParseException {
        try {
            LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
            ApplicationContext ctx =
                    new AnnotationConfigApplicationContext(ApplicationConfig.class);

            ctx.getBean(Main.class).start(args);
        } catch (Exception e) {
            System.out.println(String.format("Error has occur: %s", e.getMessage()));
        }
    }

    private void start(String[] files) throws IOException, ParseException {
        parserProcessor.process(files);
    }
}
