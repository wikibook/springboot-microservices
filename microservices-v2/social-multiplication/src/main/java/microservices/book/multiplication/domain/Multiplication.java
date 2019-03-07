package microservices.book.multiplication.domain;

/**
 * 애플리케이션에서 곱셈을 나타내는 클래스
 */
public class Multiplication {

  // 인수
  private int factorA;
  private int factorB;

  // A * B 의 결과
  private int result;

  public Multiplication(int factorA, int factorB) {
    this.factorA = factorA;
    this.factorB = factorB;
    this.result = factorA * factorB;
  }

  public int getFactorA() {
    return factorA;
  }

  public int getFactorB() {
    return factorB;
  }

  public int getResult() {
    return result;
  }

  @Override
  public String toString() {
    return "Multiplication{" +
            "인수A=" + factorA +
            ", 인수B=" + factorB +
            ", 결과(A*B)=" + result +
            '}';
  }

}
