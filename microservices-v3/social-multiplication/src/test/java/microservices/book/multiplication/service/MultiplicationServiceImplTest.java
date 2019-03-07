package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class MultiplicationServiceImplTest {

  private MultiplicationServiceImpl multiplicationServiceImpl;

  @Mock
  private RandomGeneratorService randomGeneratorService;

  @Before
  public void setUp() {
    // initMocks 를 호출해 Mockito 가 어노테이션을 처리하도록 지시
    MockitoAnnotations.initMocks(this);
    multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService);
  }

  @Test
  public void createRandomMultiplicationTest() {
    // given (randomGeneratorService 가 처음에 50, 나중에 30을 반환하도록 설정)
    given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

    // when
    Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

    // then
    assertThat(multiplication.getFactorA()).isEqualTo(50);
    assertThat(multiplication.getFactorB()).isEqualTo(30);
  }

  @Test
  public void checkCorrectAttemptTest() {
    // given
    Multiplication multiplication = new Multiplication(50, 60);
    User user = new User("john_doe");
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000);

    // when
    boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

    // then
    assertThat(attemptResult).isTrue();
  }

  @Test
  public void checkWrongAttemptTest() {
    // given
    Multiplication multiplication = new Multiplication(50, 60);
    User user = new User("john_doe");
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010);

    // when
    boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

    // then
    assertThat(attemptResult).isFalse();
  }
}