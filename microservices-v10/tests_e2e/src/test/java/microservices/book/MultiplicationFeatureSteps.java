package microservices.book;

import cucumber.api.java.Before;
import cucumber.api.java.ko.그러면;
import cucumber.api.java.ko.먼저;
import microservices.book.testutils.MultiplicationApplication;
import microservices.book.testutils.beans.AttemptResponse;
import microservices.book.testutils.beans.Stats;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiplicationFeatureSteps {

  private MultiplicationApplication app;
  private AttemptResponse lastAttemptResponse;
  private Stats lastStatsResponse;

  public MultiplicationFeatureSteps() {
    this.app = new MultiplicationApplication();
  }

  @Before
  public void cleanUp() {
    app.deleteData();
  }

  @먼저("^사용자 ([^\\s]+)가 (\\d+)개의 ([^\\s]+) 답안을 제출한다$")
  public void 사용자가_정답을_제출한다(final String userAlias,
                            final int attempts,
                            final String rightOrWrong) throws Throwable {
    int attemptsSent = IntStream.range(0, attempts)
            .mapToObj(i -> app.sendAttempt(userAlias, 10, 10,
                    "정답".equals(rightOrWrong) ? 100 : 258))
            // 마지막 답안을 나중에 사용하도록 저장
            .peek(response -> lastAttemptResponse = response)
            .mapToInt(response -> response.isCorrect() ? 1 : 0)
            .sum();

    assertThat(attemptsSent).isEqualTo("정답".equals(rightOrWrong) ? attempts : 0)
            .withFailMessage("답안을 애플리케이션으로 전송 시 에러");
  }

  @그러면("^사용자는 답안이 ([^\\s]+)이라는 응답을 받는다$")
  public void 사용자는_응답을_받는다(final String rightOrWrong) throws Throwable {
    assertThat(lastAttemptResponse.isCorrect())
            .isEqualTo("정답".equals(rightOrWrong))
            .withFailMessage("기대한 응답 "
                    + rightOrWrong + " 답안");
  }

  @그러면("^사용자는 (\\d+)점을 얻는다$")
  public void 사용자는_점수를_얻는다(final int points) throws Throwable {
    long attemptId = lastAttemptResponse.getId();
    Thread.currentThread().sleep(2000);
    int score = app.getScoreForAttempt(attemptId).getScore();
    assertThat(score).isEqualTo(points);
  }

  @그러면("^사용자는 ([^\\s]+) 배지를 얻는다$")
  public void 사용자는_배지를_얻는다(final String badgeType) throws Throwable {
    long userId = lastAttemptResponse.getUser().getId();
    Thread.currentThread().sleep(200);
    lastStatsResponse = app.getStatsForUser(userId);
    List<String> userBadges = lastStatsResponse.getBadges();
    assertThat(userBadges).contains(badgeType);
  }

  @그러면("^사용자는 배지를 얻지 못한다$")
  public void 사용자는_배지를_얻지_못한다() throws Throwable {
    long userId = lastAttemptResponse.getUser().getId();
    Stats stats = app.getStatsForUser(userId);
    List<String> userBadges = stats.getBadges();
    if (stats.getScore() == 0) {
      assertThat(stats.getBadges()).isNullOrEmpty();
    } else {
      assertThat(userBadges).isEqualTo(lastStatsResponse.getBadges());
    }
  }

  @먼저("^사용자는 (\\d+)점을 가지고 있다$")
  public void 사용자는_점수를_가지고_있다(final int points) throws Throwable {
    long userId = lastAttemptResponse.getUser().getId();
    int statPoints = app.getStatsForUser(userId).getScore();
    assertThat(points).isEqualTo(statPoints);
  }

  public AttemptResponse getLastAttemptResponse() {
    return lastAttemptResponse;
  }

  public Stats getLastStatsResponse() {
    return lastStatsResponse;
  }

  public MultiplicationApplication getApp() {
    return app;
  }

}
