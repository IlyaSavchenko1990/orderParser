package com.cp.task;

import org.springframework.context.annotation.*;

/**
 * Spring application config class
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 07.08.2018
 */
@Configuration
@ComponentScan
public class ApplicationConfig {

    @Bean
    public Main main() {
        return new Main();
    }
}
