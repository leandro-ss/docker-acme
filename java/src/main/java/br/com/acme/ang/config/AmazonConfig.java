package br.com.acme.ang.config;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    private static final Logger logger = LoggerFactory.getLogger(AmazonConfig.class);

    @Bean
    public AmazonS3 amazonS3() {
        logger.debug("amazonS3 - AmazonS3ClientBuilder");

        return AmazonS3ClientBuilder.standard()
            .withCredentials(new EnvironmentVariableCredentialsProvider())
            .build();
    }
}