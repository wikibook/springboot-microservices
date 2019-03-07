package microservices.book.multiplication.controller;

import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자가 POST 로 답안을 전송하도록 REST API 를 제공하는 클래스
 */
@RestController
@RequestMapping("/results")
final class MultiplicationResultAttemptController {

  private final MultiplicationService multiplicationService;

  @Autowired
  MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
    this.multiplicationService = multiplicationService;
  }

  @PostMapping
  ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {
    boolean isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt);
    MultiplicationResultAttempt attemptCopy = new MultiplicationResultAttempt(
            multiplicationResultAttempt.getUser(),
            multiplicationResultAttempt.getMultiplication(),
            multiplicationResultAttempt.getResultAttempt(),
            isCorrect
    );
    return ResponseEntity.ok(attemptCopy);
  }

  @GetMapping
  ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(@RequestParam("alias") String alias) {
    return ResponseEntity.ok(
            multiplicationService.getStatsForUser(alias)
    );
  }

  @GetMapping("/{resultId}")
  ResponseEntity<MultiplicationResultAttempt> getResultById(final @PathVariable("resultId") Long resultId) {
    return ResponseEntity.ok(
            multiplicationService.getResultById(resultId)
    );
  }

}
