package microservices.book.multiplication.service;

public interface RandomGeneratorService {

  /**
   * @return 무작위로 생성한 11 이상 99 이하의 인수
   */
  int generateRandomFactor();

}
