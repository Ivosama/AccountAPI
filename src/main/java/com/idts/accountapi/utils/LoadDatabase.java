package com.idts.accountapi.utils;

import com.idts.accountapi.dao.AccountRepository;
import com.idts.accountapi.dao.UserRepository;
import com.idts.accountapi.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private final static Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            //Users
            logger.info("Preloading " + userRepository.save(new User("Mathias")));
            logger.info("Preloading " + userRepository.save(new User("Poul")));
            logger.info("Preloading " + userRepository.save(new User("Peter")));
            logger.info("Preloading " + userRepository.save(new User("Tom")));
        };
    }
}
