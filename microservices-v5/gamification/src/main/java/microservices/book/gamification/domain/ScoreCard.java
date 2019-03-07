package microservices.book.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 점수와 답안을 연결하는 클래스
 * 사용자와 점수가 등록된 시간의 타임스탬프를 포함
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class ScoreCard {

  // 명시되지 않은 경우 이 카드에 할당되는 기본 점수
  public static final int DEFAULT_SCORE = 10;

  @Id
  @GeneratedValue
  @Column(name = "CARD_ID")
  private final Long cardId;

  @Column(name = "USER_ID")
  private final Long userId;

  @Column(name = "ATTEMPT_ID")
  private final Long attemptId;

  @Column(name = "SCORE_TS")
  private final long scoreTimestamp;

  @Column(name = "SCORE")
  private final int score;

  // JSON/JPA 를 위한 빈 생성자
  public ScoreCard() {
    this(null, null, null, 0, 0);
  }

  public ScoreCard(final Long userId, final Long attemptId) {
    this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
  }

}
