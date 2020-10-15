package ru.geek.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfiguration {

    @Configuration
    @Order(2)
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/").anonymous()
                    .antMatchers("/user/**").hasRole("ADMIN")
                    .antMatchers("/product/**").hasRole("USER")
                    .and()
                    .formLogin()
                    .and()
                    .logout().logoutSuccessUrl("/");
            ;
        }
    }


    //    -------In-memory-------
    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{bcrypt}$2y$12$gQ.hKca7it/OYRBOutDTd.etvqh.wSCOu0awA8Ne38LDjfBlz8K4S")
                .roles("ADMIN");
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{bcrypt}$2y$12$gQ.hKca7it/OYRBOutDTd.etvqh.wSCOu0awA8Ne38LDjfBlz8K4S")
                .roles("USER");
    }

    //    -----Basic авторизация в REST сервисе-----
    @Configuration
    @Order(1)
    public static class ApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/api/**").hasRole("ADMIN")
                    .and()
                    .httpBasic()
//                    обработчик ошибок
                    .authenticationEntryPoint((request, response, exception) -> {
                        response.setContentType("application/json");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().println("{\"error\": \"" + exception.getMessage() + "\" }");

                    })
                    .and()
                    .csrf().disable()  //не нужна в REST сервиве
                    .sessionManagement()// так как сессия создаваться не будет
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // указываем что не создаем сессию
        }
    }
}