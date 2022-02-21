package com.intrum.creditmanagementservice.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v3/api-docs",
                        "/v3/api-docs/swagger-config",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/h2-console/**",
                        "/console/**",
                        "/").permitAll()
                .anyRequest().authenticated()
                .and().csrf()
                .ignoringAntMatchers("/customers/**", "/debt-cases/**", "/users/**", "/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .httpBasic();
    }
}