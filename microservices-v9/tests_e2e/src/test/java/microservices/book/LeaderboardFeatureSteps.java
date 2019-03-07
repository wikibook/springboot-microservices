package microservices.book;

import cucumber.api.java.ko.그러면;
import microservices.book.testutils.beans.LeaderBoardPosition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LeaderboardFeatureSteps {

  private MultiplicationFeatureSteps mSteps;

  public LeaderboardFeatureSteps(final MultiplicationFeatureSteps mSteps) {
    this.mSteps = mSteps;
  }

  @그러면("^사용자 ([^\\s]+)가 리더보드에서 (\\d+)등이 된다$")
  public void 사용자가_리더보드에서_등수에_오른다(final String user, final int position) throws Throwable {
    Thread.currentThread().sleep(500);
    List<LeaderBoardPosition> leaderBoard = mSteps.getApp().getLeaderboard();
    assertThat(leaderBoard).isNotEmpty();
    long userId = leaderBoard.get(position - 1).getUserId();
    String userAlias = mSteps.getApp().getUser(userId).getAlias();
    assertThat(userAlias).isEqualTo(user);
  }
}