package br.com.acme.ang.config;


import com.amazonaws.services.s3.AmazonS3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/amazon.properties")
public class AmazonConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(AmazonConfigTest.class);

    @Autowired
    private AmazonConfig config;

    @Test
    public void amazonS3Test() {

        AmazonS3 result = config.amazonS3();
        logger.debug("amazonS3Test - result: {}",result);
    }
}