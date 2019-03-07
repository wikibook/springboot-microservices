package microservices.book.gateway.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;

public class RibbonConfiguration {

  @Bean
  public IPing ribbonPing(final IClientConfig config) {
    // BOOT2: /health 에서 /actuator/health 로 변경
    return new PingUrl(false, "/actuator/health");
  }

  @Bean
  public IRule ribbonRule(final IClientConfig config) {
    return new AvailabilityFilteringRule();
  }

}
