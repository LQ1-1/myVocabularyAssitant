package com.vocabularyassitant;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import com.vocabularyassitant.service.EnglishVocabulary.EnglishVocabularyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VocabularyAssitantApplication {
    private static final Logger log = LoggerFactory.getLogger(VocabularyAssitantApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VocabularyAssitantApplication.class, args);
    }

    @Bean
    public CommandLineRunner startupWordSampler(EnglishVocabularyService englishVocabularyService) {
        return args -> {
            log.info("Startup word sampler: begin");
            try {
                for (int i = 1; i <= 3; i++) {
                    EnglishVocabulary vocabulary = englishVocabularyService.getRandomEnglishVocabulary();
                    if (vocabulary == null) {
                        log.warn("Startup word sampler: no vocabulary found at sampleIndex={}", i);
                        continue;
                    }
                    log.info("Startup word sampler: sampleIndex={}, vId={}, vWord={}, vZhMeaning={}",
                            i, vocabulary.getvId(), vocabulary.getvWord(), vocabulary.getvZh_meaning());
                }
            } catch (Exception e) {
                log.warn("Startup word sampler skipped because vocabulary query failed: {}", e.getMessage());
            }
            log.info("Startup word sampler: end");
        };
    }

}
