package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;

public interface MultiplicationService {

  /**
   * 두 개의 무작위 인수를 인수를 담은 {@link Multiplication} 객체를 생성한다.
   * 무작위로 생성되는 숫자의 범위는 11~99.
   *
   * @return 무작위 인수를 담은 {@link Multiplication} 객체
   */
  Multiplication createRandomMultiplication();

}
