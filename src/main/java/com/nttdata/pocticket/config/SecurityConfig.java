package com.nttdata.pocticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
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

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
//        http.authorizeHttpRequests(configurer -> configurer
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        //.loginPage("/login")
//                        .loginProcessingUrl("/authenticatedTheUser")
//                        .defaultSuccessUrl("/home")
//                        .permitAll()
//                );
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .authorizeHttpRequests(
//                        request -> request
//                                .requestMatchers("/").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**");
    }

}
