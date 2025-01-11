package com.gh.extractor.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.internal.securitytoken.RoleInfo;
import com.amazonaws.auth.profile.internal.securitytoken.STSProfileCredentialsServiceProvider;
import com.amazonaws.services.location.AmazonLocation;
import com.amazonaws.services.location.AmazonLocationClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.textract.AmazonTextractClient;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

@Configuration
public class AWSConfig {

	@Value("${AWS_ROLE_ARN:}")
	private String roleARN;

	@Value("${AWS_WEB_IDENTITY_TOKEN_FILE:}")
	private String webIdentityTokenFile;

	@Value("${SPRING_PROFILES_ACTIVE:}")
	private String roleSession;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Bean
	public AWSCredentialsProvider awsCredentialsProvider() {

        /*
        Amazon EKS injects a web identity token file and roleArn into container environment; however,
        if developing locally, revert to default provider chain that looks for env vars and/or creds file
         */
		if (StringUtils.hasLength(roleARN) && StringUtils.hasLength(webIdentityTokenFile)) {
			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setRoleArn(roleARN);
			roleInfo.setRoleSessionName(roleSession);
			roleInfo.setWebIdentityTokenFilePath(webIdentityTokenFile);
			return new STSProfileCredentialsServiceProvider(roleInfo);
		}
		return new DefaultAWSCredentialsProviderChain();
	}

	@Bean
	public AmazonTextractClient generateTextractClient() {
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setMaxErrorRetry(5);
		return (AmazonTextractClient) AmazonTextractClientBuilder.standard().withClientConfiguration(clientConfiguration)
				.withCredentials(awsCredentialsProvider()).withRegion(region).build();
	}

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(amazonSQSAsync());
	}

	@Primary
	@Bean
	public AmazonSQSAsync amazonSQSAsync() {
		return AmazonSQSAsyncClientBuilder.standard().withRegion(region)
				.withCredentials(awsCredentialsProvider())
				.build();
	}

    @Bean
    public AWSSecretsManager secretsManagerClient() {
        return AWSSecretsManagerClientBuilder.standard()
                .withCredentials(awsCredentialsProvider()).withRegion(region).build();
    }

    @Primary
    @Bean
    public AmazonSNSClient amazonSNSClient() {
        return (AmazonSNSClient) AmazonSNSClientBuilder
                .standard().withRegion(region)
                .withCredentials(awsCredentialsProvider()).build();
    }
	@Bean
	@Primary
	public AmazonS3 amazonS3Client() {
		return AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider())
				.withRegion(region).build();
	}
	@Bean
	@Primary
	public AmazonLocation amazonLocationClient() {
		return AmazonLocationClient.builder().withCredentials(awsCredentialsProvider())
				.withRegion(region).build();
	}
}
