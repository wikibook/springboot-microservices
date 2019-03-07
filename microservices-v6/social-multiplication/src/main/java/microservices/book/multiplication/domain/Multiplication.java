package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 애플리케이션에서 곱셈을 나타내는 클래스 (a * b)
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class Multiplication {

  @Id
  @GeneratedValue
  @Column(name = "MULTIPLICATION_ID")
  private Long id;

  // 두 인수
  private final int factorA;
  private final int factorB;

  // JSON/JPA 를 위한 빈 생성자
  Multiplication() {
    this(0, 0);
  }
}
