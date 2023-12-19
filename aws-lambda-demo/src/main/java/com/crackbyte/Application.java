package com.crackbyte;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.crackbyte.controller.DemoController;
import com.crackbyte.controller.HelloController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
// We use direct @Import instead of @ComponentScan to speed up cold starts
// @ComponentScan(basePackages = "com.crackbyte.controller")
@Import({HelloController.class, DemoController.class})
public class Application {
    public static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static void createHandler() {
        try {
            if (handler == null) {
                handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
            }
            // For applications that take longer than 10 seconds to start, use the async builder:
             handler = new SpringBootProxyHandlerBuilder<AwsProxyRequest>()
                                .defaultProxy()
                                .asyncInit()
                                .springBootApplication(Application.class)
                                .buildAndInitialize();
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

}
