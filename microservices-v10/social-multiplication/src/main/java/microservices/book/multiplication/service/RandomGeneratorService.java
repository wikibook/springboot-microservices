package microservices.book.multiplication.service;

public interface RandomGeneratorService {

  /**
   * @return 무작위로 만든 11 이상 99 이하의 숫자
   */
  int generateRandomFactor();

}
