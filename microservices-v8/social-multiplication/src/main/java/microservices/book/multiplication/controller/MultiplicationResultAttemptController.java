package microservices.book.multiplication.controller;

import lombok.extern.slf4j.Slf4j;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자가 POST 로 답안을 전송하도록 REST API 를 제공하는 클래스
 */
@Slf4j
@RestController
@RequestMapping("/results")
final class MultiplicationResultAttemptController {

  private final MultiplicationService multiplicationService;

  private final int serverPort;

  @Autowired
  MultiplicationResultAttemptController(
          final MultiplicationService multiplicationService,
          @Value("${server.port}") int serverPort) {
    this.multiplicationService = multiplicationService;
    this.serverPort = serverPort;
  }

  @PostMapping
  ResponseEntity<MultiplicationResultAttempt> postResult(
          final @RequestBody MultiplicationResultAttempt
                  multiplicationResultAttempt) {
    return ResponseEntity.ok(
            multiplicationService.checkAttempt(multiplicationResultAttempt)
    );
  }

  @GetMapping
  ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(
          final @RequestParam("alias") String alias) {
    return ResponseEntity.ok(
            multiplicationService.getStatsForUser(alias)
    );
  }

  @GetMapping("/{resultId}")
  ResponseEntity<MultiplicationResultAttempt> getResultById(final @PathVariable("resultId") Long resultId) {
    log.info("조회 결과 {} 조회한 서버 @ {}", resultId, serverPort);
    return ResponseEntity.ok(
            multiplicationService.getResultById(resultId)
    );
  }

}
