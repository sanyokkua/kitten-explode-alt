package ua.kostenko.expkitten.explodingkitten.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.kostenko.expkitten.explodingkitten.api.GameStatePersistence;
import ua.kostenko.expkitten.explodingkitten.engine.processors.CatCardProcessor;
import ua.kostenko.expkitten.explodingkitten.engine.processors.Processor;
import ua.kostenko.expkitten.explodingkitten.engine.repositories.GameStatePersistenceInMemory;

@Configuration
public class LoadProperties {

    @Bean
    public GameStatePersistence gameStatePersistence() {
        return new GameStatePersistenceInMemory();
    }

    @Bean
    public Processor gameProcessor() {
        return new CatCardProcessor();
    }
}
