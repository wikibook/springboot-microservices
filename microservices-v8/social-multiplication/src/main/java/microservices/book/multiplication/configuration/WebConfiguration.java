package microservices.book.multiplication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

  /**
   * CORS(Cross-Origin Resource Sharing) 설정
   * 자세한 정보 : http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cors.html
   *
   * @param registry
   */
  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**");
  }

}
