package de.aittr.g_31_2_shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        x -> x
                                .requestMatchers(HttpMethod.GET, "/product").permitAll()
                                .requestMatchers(HttpMethod.GET, "/product/count").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/product/total").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/product/avg").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/product/{id}").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/product/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/product/del_by_name/{name}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/product/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/customer").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/customer/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/customer").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/register/admin").hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());

        return http.build();
    }

}