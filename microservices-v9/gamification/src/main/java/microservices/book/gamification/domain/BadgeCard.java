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
 * 배지와 사용자를 연결하는 클래스
 * 사용자가 배지를 획득한 순간의 타임스탬프를 포함
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class BadgeCard {

  @Id
  @GeneratedValue
  @Column(name = "BADGE_ID")
  private final Long badgeId;

  private final Long userId;
  private final long badgeTimestamp;
  private final Badge badge;

  // JSON/JPA 를 위한 빈 생성자
  public BadgeCard() {
    this(null, null, 0, null);
  }

  public BadgeCard(final Long userId, final Badge badge) {
    this(null, userId, System.currentTimeMillis(), badge);
  }

}
