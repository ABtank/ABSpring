package ru.geekbrains.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.geekbrains.server.auth.AuthService;
import ru.geekbrains.server.auth.AuthServiceJdbcImpl;
import ru.geekbrains.server.persistance.NewSpringConfig;
import ru.geekbrains.server.persistance.UserRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

@Configuration
@Import(NewSpringConfig.class)
@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = "ru.geekbrains.server")
public class SpringConfig {

    @Value("${database.driver.class}")
    private String driverClassName;
    @Value("${database.url}")
    private String url;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;

    @Bean
    public ChatServer chatServer(){
        return new ChatServer();
    }

    @Bean
    public AuthService authService (UserRepository userRepository){
        return new AuthServiceJdbcImpl(userRepository);
    }

    @Bean
    public UserRepository userRepository(DataSource dataSource) throws SQLException {
        return new UserRepository(dataSource.getConnection());
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(url);
        return ds;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ClientHandler clientHandler(String login, Socket socket) throws IOException {
        return new ClientHandler(login,socket,chatServer());
    }
}
