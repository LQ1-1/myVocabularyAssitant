package com.vocabularyassitant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VocabularyAssitantApplication {

    public static void main(String[] args) {
        SpringApplication.run(VocabularyAssitantApplication.class, args);
    }

}
