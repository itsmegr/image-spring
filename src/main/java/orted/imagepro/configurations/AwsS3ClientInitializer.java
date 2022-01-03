package orted.imagepro.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AwsS3ClientInitializer {

    @Value("${aws.access.key.id}")
    private  String awsAccessKeyId;

    @Value("${aws.secret.access.key}")
    private  String awsSecretAccessKey;

    @Value("${aws.s3.region}")
    private  String awsS3Region;

    @Bean
    public AmazonS3 getAmazonS3Client() {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);
        // Get Amazon S3 client and return the S3 client object
        AmazonS3 s3 =  AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(awsS3Region)
                .build();
        System.out.println("Connected to aws");
        return s3;
    }

}
