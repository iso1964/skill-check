package q006.value;

import java.math.BigDecimal;
import java.util.Stack;
import java.math.RoundingMode;

/**
 * 割り算を行うクラス
 */
public class DivideValue implements IValue {
    @Override
    public void execute(Stack<BigDecimal> values) {
        // スタックから2つの値を抜き出し、割り算した結果をスタックに積む
        BigDecimal right = values.pop();  // 0 devideは「java.lang.ArithmeticException: / by zero」
        BigDecimal left = values.pop();
        values.push(left.divide(right, 4, RoundingMode.HALF_UP));  // 循環結果は小数点以下４桁で制限
    }
}
