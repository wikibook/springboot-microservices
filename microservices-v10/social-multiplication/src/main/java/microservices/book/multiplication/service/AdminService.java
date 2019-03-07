package microservices.book.multiplication.service;

/**
 * 애플리케이션 관리자 전용 오퍼레이션을 위한 서비스
 * 운영 환경에서는 절대 사용하지 말고 테스트 용도로만 사용할 것
 */
public interface AdminService {

  /**
   * 데이터베이스 내용을 모두 삭제
   */
  void deleteDatabaseContents();
}
