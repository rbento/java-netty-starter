
/* Copyright (c) 2023 Rodrigo Bento */

package rbento.starter.app;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AdderTest {

    @Test
    @DisplayName("Test no arguments returns zero")
    void testNoArguments() {
        Adder adder = new Adder();
        Assertions.assertThat(0).isEqualTo(adder.add());
    }

    @Test
    @DisplayName("Test null argument returns zero")
    void testNullArgument() {
        Adder adder = new Adder();
        Assertions.assertThat(0).isEqualTo(adder.add(null));
    }

    @CsvSource({
        "0,    0,   0",
        "0,    1,   1",
        "1,    0,   1",
        "1,    1,   2",
        "1,    2,   3",
        "44,  56, 100",
        "99,  99, 198",
        "-3,  -6, -9",
        "-10,  20, 10"
    })
    @ParameterizedTest(name = "{0} + {1} = {2}")
    @DisplayName("Test adding two numbers")
    void testAddTwoNumbers(int first, int second, int expectedResult) {
        Adder adder = new Adder();
        Assertions.assertThat(expectedResult).isEqualTo(adder.add(first, second));
    }
}
