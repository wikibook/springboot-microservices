package microservices.book.gamification.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import microservices.book.gamification.client.MultiplicationResultAttemptDeserializer;

/**
 * User 가 Multiplication 을 계산한 답안을 정의한 클래스
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = MultiplicationResultAttemptDeserializer.class)
public final class MultiplicationResultAttempt {

  private final String userAlias;

  private final int multiplicationFactorA;
  private final int multiplicationFactorB;
  private final int resultAttempt;

  private final boolean correct;

  // JSON/JPA 를 위한 빈 생성자
  MultiplicationResultAttempt() {
    userAlias = null;
    multiplicationFactorA = -1;
    multiplicationFactorB = -1;
    resultAttempt = -1;
    correct = false;
  }

}
