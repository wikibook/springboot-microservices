package microservices.book.gamification.controller;

import microservices.book.gamification.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@WebMvcTest(AdminController.class)
public class AdminControllerEnabledTest {

  @MockBean
  private AdminService adminService;

  @Autowired
  private MockMvc mvc;

  /**
   * 테스트 프로파일이 설정된 경우 컨트롤러가 예상한대로 동작하는지 확인하는 테스트
   * (클래스 어노테이션 참고)
   *
   * @throws Exception 에러가 발생한 경우
   */
  @Test
  public void deleteDatabaseTest() throws Exception {
    // when
    MockHttpServletResponse response = mvc.perform(
            post("/gamification/admin/delete-db")
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    verify(adminService).deleteDatabaseContents();
  }
}