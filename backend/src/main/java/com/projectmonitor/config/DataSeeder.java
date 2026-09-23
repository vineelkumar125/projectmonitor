//package com.projectmonitor.config;
//import com.projectmonitor.entity.*; import com.projectmonitor.repository.UserRepository; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder;
//@Configuration public class DataSeeder { @Bean CommandLineRunner seed(UserRepository users,PasswordEncoder encoder){return args->{if(users.findByEmail("admin@projectmonitor.com").isEmpty())users.save(new User("Admin","admin@projectmonitor.com",encoder.encode("admin123"),Role.ADMIN));};}}
package com.projectmonitor.config;

import com.projectmonitor.entity.Role;
import com.projectmonitor.entity.User;
import com.projectmonitor.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(
            UserRepository users,
            PasswordEncoder encoder) {

        return args -> {

            if (users.findByEmail("admin@projectmonitor.com").isEmpty()) {

                User admin = new User(
                        "Admin",
                        "admin@projectmonitor.com",
                        encoder.encode("admin123"),
                        Role.ADMIN
                );

                users.save(admin);
            }
        };
    }
}