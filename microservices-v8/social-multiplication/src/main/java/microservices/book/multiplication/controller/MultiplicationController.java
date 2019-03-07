package microservices.book.multiplication.controller;

import lombok.extern.slf4j.Slf4j;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 곱셈 애플리케이션의 REST API 를 구현한 클래스
 */
@Slf4j
@RestController
@RequestMapping("/multiplications")
final class MultiplicationController {

  private final MultiplicationService multiplicationService;

  private final int serverPort;

  @Autowired
  public MultiplicationController(final MultiplicationService multiplicationService, @Value("${server.port}") int serverPort) {
    this.multiplicationService = multiplicationService;
    this.serverPort = serverPort;
  }

  @GetMapping("/random")
  Multiplication getRandomMultiplication() {
    log.info("무작위 곱셈을 생성한 서버 @ {}", serverPort);
    return multiplicationService.createRandomMultiplication();
  }

}
