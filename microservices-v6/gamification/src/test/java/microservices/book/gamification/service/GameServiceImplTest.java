package microservices.book.gamification.service;

import microservices.book.gamification.client.MultiplicationResultAttemptClient;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import microservices.book.gamification.domain.*;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;

public class GameServiceImplTest {

  private GameServiceImpl gameService;

  @Mock
  private ScoreCardRepository scoreCardRepository;

  @Mock
  private BadgeCardRepository badgeCardRepository;

  @Mock
  private MultiplicationResultAttemptClient multiplicationClient;

  @Before
  public void setUp() {
    // initMocks 를 호출해 Mockito 가 어노테이션을 처리하도록 지시
    MockitoAnnotations.initMocks(this);
    gameService = new GameServiceImpl(scoreCardRepository, badgeCardRepository, multiplicationClient);

    // given - 기본적으로 행운의 숫자를 포함하지 않는 답안
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
            "john_doe", 20, 70, 1400, true);
    given(multiplicationClient.retrieveMultiplicationResultAttemptById(anyLong()))
            .willReturn(attempt);
  }

  @Test
  public void processFirstCorrectAttemptTest() {
    // given
    Long userId = 1L;
    Long attemptId = 8L;
    int totalScore = 10;
    ScoreCard scoreCard = new ScoreCard(userId, attemptId);
    given(scoreCardRepository.getTotalScoreForUser(userId))
            .willReturn(totalScore);
    // 이 리파지토리는 방금 얻은 점수 카드를 반환
    given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
            .willReturn(Collections.singletonList(scoreCard));
    given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
            .willReturn(Collections.emptyList());


    // when
    GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

    // assert - 점수 카드 하나와 첫 번째 정답 배지를 획득
    assertThat(iteration.getScore()).isEqualTo(scoreCard.getScore());
    assertThat(iteration.getBadges()).containsOnly(Badge.FIRST_WON);
  }

  @Test
  public void processCorrectAttemptForScoreBadgeTest() {
    // given
    Long userId = 1L;
    Long attemptId = 29L;
    int totalScore = 100;
    BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
    given(scoreCardRepository.getTotalScoreForUser(userId))
            .willReturn(totalScore);
    // 이 리파지토리는 방금 얻은 점수 카드를 반환
    given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
            .willReturn(createNScoreCards(10, userId));
    // 첫 번째 정답 배지는 이미 존재
    given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
            .willReturn(Collections.singletonList(firstWonBadge));


    // when
    GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

    // assert - 점수 카드 하나와 브론즈 배지를 획득
    assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
    assertThat(iteration.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATOR);
  }

  @Test
  public void processCorrectAttemptForLuckyNumberBadgeTest() {
    // given
    Long userId = 1L;
    Long attemptId = 29L;
    int totalScore = 10;
    BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
    given(scoreCardRepository.getTotalScoreForUser(userId))
            .willReturn(totalScore);
    // 이 리파지토리는 방금 얻은 점수 카드를 반환
    given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
            .willReturn(createNScoreCards(1, userId));
    // 첫 번째 정답 배지는 이미 존재
    given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
            .willReturn(Collections.singletonList(firstWonBadge));
    // 행운의 숫자가 포함된 답안
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
            "john_doe", 42, 10, 420, true);
    given(multiplicationClient.retrieveMultiplicationResultAttemptById(attemptId))
            .willReturn(attempt);

    // when
    GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

    // assert - 스코어 카드 하나와 행운의 숫자 배지를 획득
    assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
    assertThat(iteration.getBadges()).containsOnly(Badge.LUCKY_NUMBER);
  }

  @Test
  public void processWrongAttemptTest() {
    // given
    Long userId = 1L;
    Long attemptId = 8L;
    int totalScore = 10;
    ScoreCard scoreCard = new ScoreCard(userId, attemptId);
    given(scoreCardRepository.getTotalScoreForUser(userId))
            .willReturn(totalScore);
    // 이 리파지토리는 방금 얻은 점수 카드를 반환
    given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
            .willReturn(Collections.singletonList(scoreCard));
    given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
            .willReturn(Collections.emptyList());


    // when
    GameStats iteration = gameService.newAttemptForUser(userId, attemptId, false);

    // assert - 하나도 점수를 얻지 못함
    assertThat(iteration.getScore()).isEqualTo(0);
    assertThat(iteration.getBadges()).isEmpty();
  }

  @Test
  public void retrieveStatsForUserTest() {
    // given
    Long userId = 1L;
    int totalScore = 1000;
    BadgeCard badgeCard = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);
    given(scoreCardRepository.getTotalScoreForUser(userId))
            .willReturn(totalScore);
    given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
            .willReturn(Collections.singletonList(badgeCard));

    // when
    GameStats stats = gameService.retrieveStatsForUser(userId);

    // assert - 점수 카드 하나와 첫 번째 정답 배지를 획득
    assertThat(stats.getScore()).isEqualTo(totalScore);
    assertThat(stats.getBadges()).containsOnly(Badge.SILVER_MULTIPLICATOR);
  }

  private List<ScoreCard> createNScoreCards(int n, Long userId) {
    return IntStream.range(0, n)
            .mapToObj(i -> new ScoreCard(userId, (long) i))
            .collect(Collectors.toList());
  }
}