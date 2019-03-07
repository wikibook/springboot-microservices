package microservices.book.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 리더보드 내 위치를 나타내는 객체
 * 사용자와 전체 점수를 연결
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class LeaderBoardRow {

  private final Long userId;
  private final Long totalScore;

  // JSON/JPA 를 위한 빈 생성자
  public LeaderBoardRow() {
    this(0L, 0L);
  }
}
