
/* Copyright (c) 2023 Rodrigo Bento */

package rbento.starter.app;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class Adder {
    public int add(int... candidates) {
        int[] integers = ArrayUtils.nullToEmpty(candidates);
        return Arrays.stream(integers).reduce(0, (a, b) -> a + b);
    }
}
