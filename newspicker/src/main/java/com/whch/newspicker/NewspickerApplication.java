package com.whch.newspicker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class NewspickerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewspickerApplication.class, args);
    }

}
