package com.farah.taskmanagement.userservice.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDataInitializer {

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.save(new User("U100", "Farah AbuAssi", "farah@example.com", "PROJECT_MANAGER", true));
                userRepository.save(new User("U200", "Lama Alsaqqa", "lama@example.com", "TEAM_MEMBER", true));
                userRepository.save(new User("U300", "Archived User", "archived@example.com", "TEAM_MEMBER", false));
            }
        };
    }
}
