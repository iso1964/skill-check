package q006;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
 
class calcRPNTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/calcRPNメソッド応答バリエーション.txt", numLinesToSkip = 2, delimiter = '|')
    void calcRPNメソッドバリエーション(String input, String expected) {
        assertEquals(expected, q006.Q006.calcRPN(input));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/calcRPNメソッド例外バリエーション.txt", numLinesToSkip = 2, delimiter = '|')
    void calcRPNメソッド例外バリエーション(String input, String expectedExceptionName) {
        Exception thrown = assertThrows(RuntimeException.class, ()->q006.Q006.calcRPN(input));  // ラムダ式記載して戻り値判定でなくする
        assertEquals(expectedExceptionName, thrown.getClass().getName());
    }
}
