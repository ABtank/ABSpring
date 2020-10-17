package ru.geek.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geek.persist.repo.UserRepository;

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
        }
    }



    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth,
                              UserDetailsService userDetailsService,
                              PasswordEncoder passwordEncoder) throws Exception {
        //-----DAO auth----
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //извлекать из бд инфу о Users
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(provider);

        //    -------In-memory-------
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder.encode("123"))
                .roles("ADMIN");
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder.encode("123"))
                .roles("USER");
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserAuthService(userRepository);
    }

    //    -----Basic авторизация в REST сервисе-----
//    @Configuration
//    @Order(1)
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