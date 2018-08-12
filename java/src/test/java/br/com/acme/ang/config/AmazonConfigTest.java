package br.com.acme.ang.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/amazon.properties")
public class AmazonConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(AmazonConfigTest.class);

    @Autowired
    protected Environment env;

    private static final String AWS_ACCESSKEYID = "aws.accessKeyId";
    private static final String AWS_SECRETKEY = "aws.secretKey";
    private static final String AWS_REGION = "aws.region";

    @Before
    public void prepare() throws IOException {
        logger.debug("swap properties from Spring Context to JVM System");

        System.setProperty(AWS_ACCESSKEYID, env.getProperty(AWS_ACCESSKEYID));
        logger.info("value to AWS_ACCESSKEYID:{}", System.getProperty(AWS_ACCESSKEYID));
        System.setProperty(AWS_SECRETKEY, env.getProperty(AWS_SECRETKEY));
        logger.info("value to AWS_SECRETKEY:{}", System.getProperty(AWS_SECRETKEY));
        System.setProperty(AWS_REGION, env.getProperty(AWS_REGION));
        logger.info("value to AWS_REGION:{}", System.getProperty(AWS_REGION));
    }

    @Test
    public void verifyProperty() {
        assertEquals(env.getProperty(AWS_ACCESSKEYID), System.getProperty(AWS_ACCESSKEYID));
    }

    @Test
    public void verifyAmazonClient() {
        assertFalse(amazonS3().listBuckets().isEmpty());
    }

    @Bean
    public AmazonS3 amazonS3() {
        logger.debug("amazonS3 - AmazonS3ClientBuilder");

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider()).withRegion("sa-east-1").build();
    }
}