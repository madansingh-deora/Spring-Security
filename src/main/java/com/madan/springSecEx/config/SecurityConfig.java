package com.madan.springSecEx.config;

import com.madan.springSecEx.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(customizer ->customizer.disable());
        httpSecurity.authorizeHttpRequests(request->request.anyRequest().authenticated());
        httpSecurity.formLogin(Customizer.withDefaults()); //for browers
        httpSecurity.httpBasic(Customizer.withDefaults()); //for postman
        //httpSecurity.sessionManagement(session->
                //session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //this will create a new session on every hit so broswer with form login will not work, will have to remove browser formlogin
        return httpSecurity.build();
    }

   /* @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user1= User
                .withDefaultPasswordEncoder()
                .username("nisha")
                .password("password")
                .roles("USER")
                .build();

        UserDetails user2= User
                .withDefaultPasswordEncoder()
                .username("ADM")
                .password("password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user2,user1);
    }*/
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
