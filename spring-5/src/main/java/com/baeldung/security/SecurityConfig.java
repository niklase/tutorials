package com.baeldung.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    //@Autowired
    private BasePermissionEvaluator permissionEvaluator;



    @Autowired
    public void registerGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // in reality DB auth is required
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
                .password("admin").roles("USER", "ADMIN");
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        return handler;
    }


    @Bean
    public AuthenticationManager authenticationManager(){
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        };
    }


    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails noone = userBuilder.username("noone").password("noone").roles("NOONE").build();
        return new MapReactiveUserDetailsService(noone);

    }

    @Bean
    public ReactivePermissionEvaluator<PaymentTransactionItemQuery> reactiveItemQueryPermissionEvaluator(@Autowired TestRepository repository){

        return new PaymentTransactionItemQueryPermissionEvaluator(repository);
    }


    @Bean
    public ReactivePermissionEvaluator<PaymentTransactionCollectionQuery> reactiveCollectionQueryPermissionEvaluator(){

        return new PaymentTransactionCollectionQueryPermissionEvaluator();
    }



}
