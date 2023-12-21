package org.example.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Class BaseConfig
 * @Auther Mark
 * @Date 2020/4/27 18:33
 * @Description 基础配置
 */
@Configuration
@EnableScheduling
@EnableAsync
public class BaseConfig implements WebMvcConfigurer, EnvironmentAware {

    @Value("${cors.mappings.allowed.origins}")
    private String corsMappingsAllowedOrigins;


    @Value("${tenant.enable}")
    private boolean tenantEnable;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        Binder bind = Binder.get(environment);
        Map<String, String> springProp = bind.bind("spring", Bindable.mapOf(String.class, String.class)).get();
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            ErrorPage errorPage = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            factory.addErrorPages(errorPage);
        };
    }


    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                StandardCharsets.UTF_8);
        return converter;
    }

    /**
     * CorsFilter过滤器方式
     *
     * @param
     * @return
     * @throws
     * @author zhaoxu
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration corsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        String[] originsList = corsMappingsAllowedOrigins.split(",");
        for (String origin : originsList) {
            corsConfiguration.addAllowedOrigin(origin);
        }
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

}
