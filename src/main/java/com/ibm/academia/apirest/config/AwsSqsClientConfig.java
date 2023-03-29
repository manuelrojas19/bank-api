package com.ibm.academia.apirest.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import java.net.URI;

@Configuration
public class AwsSqsClientConfig {

  private final String endpoint;

  private final String region;

  public AwsSqsClientConfig(
      @Value("${spring.cloud.aws.sqs.endpoint}") String endpoint,
      @Value("${spring.cloud.aws.sqs.region}") String region) {
    this.endpoint = endpoint;
    this.region = region;
  }

  @Bean
  @SneakyThrows
  public SqsClient sqsClient() {
    return SqsClient.builder()
        .region(Region.of(region))
        .endpointOverride(new URI(endpoint))
        .credentialsProvider(getAwsCredentialsProvider())
        .build();
  }

  private AwsCredentialsProvider getAwsCredentialsProvider() {
    var credentialsProvider = EnvironmentVariableCredentialsProvider.create();
    return AwsCredentialsProviderChain.builder()
        .addCredentialsProvider(credentialsProvider)
        .build();
  }
}
