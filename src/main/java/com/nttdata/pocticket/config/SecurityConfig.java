package com.nttdata.pocticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    String encodedPassword = encoder.encode("password");

    @Bean   //Procura as autentififaçoes na base de dados
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, 1 as enabled FROM people WHERE username=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT ? as username, 'ROLE_USER' as authority");

        return jdbcUserDetailsManager;
    }

}
