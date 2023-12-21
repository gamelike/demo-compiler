//package com.topsec.judge.config;
//
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.timelimiter.TimeLimiterConfig;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
//import org.springframework.cloud.client.circuitbreaker.Customizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//
///**
// * @author violet
// */
//@Configuration
//public class CircuitConfig {
//
//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
//        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
//                .timeoutDuration(Duration.ofSeconds(30))
//                .build();
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .timeLimiterConfig(timeLimiterConfig)
//                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
//                .build());
//    }
//
//}
