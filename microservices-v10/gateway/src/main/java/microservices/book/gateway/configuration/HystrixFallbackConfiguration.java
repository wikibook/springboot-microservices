package microservices.book.gateway.configuration;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixFallbackConfiguration {

  @Bean
  public FallbackProvider fallbackProvider() {
    return new CustomFallbackProvider();
  }
}
