package microservices.book.gateway.configuration;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomFallbackProvider implements FallbackProvider {
  @Override
  public String getRoute() {
    // 헷갈릴 수 있는데 serviceId 프로퍼티이지 경로가 아님
    return "multiplication";
  }

  @Override
  public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
    return new ClientHttpResponse() {
      @Override
      public HttpStatus getStatusCode() throws IOException {
        return HttpStatus.OK;
      }

      @Override
      public int getRawStatusCode() throws IOException {
        return HttpStatus.OK.value();
      }

      @Override
      public String getStatusText() throws IOException {
        return HttpStatus.OK.toString();
      }

      @Override
      public void close() {

      }

      @Override
      public InputStream getBody() throws IOException {
        return new ByteArrayInputStream("{\"factorA\":\"죄송합니다, 서비스가 중단되었습니다!\",\"factorB\":\"?\",\"id\":null}".getBytes());
      }

      @Override
      public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccessControlAllowCredentials(true);
        headers.setAccessControlAllowOrigin("*");
        return headers;
      }
    };
  }
}
