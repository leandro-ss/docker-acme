package br.com.acme.ang.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    private static final Logger logger = LoggerFactory.getLogger(AmazonConfig.class);

	@Value("${aws.secretKey}")
	private String secretKey;
 
	@Value("${aws.accessKeyId}")
	private String accessKeyId;
	
	@Value("${aws.region}")
	private String region;

	@Bean
	public AmazonS3 amazonS3() {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(secretKey, accessKeyId);
		AmazonS3 result = AmazonS3ClientBuilder.standard()
								.withRegion(Regions.fromName(region))
		                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                                .build();
                                
        logger.debug("amazonS3 - result: {}");
		
		return result;
	}
}