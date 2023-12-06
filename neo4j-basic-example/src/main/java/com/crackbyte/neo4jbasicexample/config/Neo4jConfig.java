package com.crackbyte.neo4jbasicexample.config;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

@Getter
@ApplicationScoped
public class Neo4jConfig {
    private static final Logger log = LoggerFactory.getLogger(Neo4jConfig.class);
    private String uri;
    private String username;
    private String password;
    private String[] packages;
    private SessionFactory sessionFactory = null;

    public Neo4jConfig() {
        Properties properties = new Properties();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if(declaredField.getName().equals("sessionFactory")) continue;
            declaredField.setAccessible(true);
            String name = String.join(".", "neo4j", declaredField.getName());
            String value = properties.getProperty(name, "");
            if (value.startsWith("$")) {
                value = System.getenv(value.replaceAll("[${}]", ""));
            }
            try {
                if (name.equals("neo4j.packages")) {
                    declaredField.set(this, value.split(","));
                } else {
                    declaredField.set(this, value);
                }
            } catch (IllegalAccessException e) {
                log.error("{}", e.getMessage());
            }
        }
    }

    @PostConstruct
    public void init() {
        Configuration config = new Configuration.Builder()
                .uri(this.getUri())
                .credentials(this.getUsername(), this.getPassword())
                .build();
        this.sessionFactory = new SessionFactory(config, this.getPackages());
    }

}
