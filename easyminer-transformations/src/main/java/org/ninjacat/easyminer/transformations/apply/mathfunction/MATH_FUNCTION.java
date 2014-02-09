package org.ninjacat.easyminer.transformations.apply.mathfunction;

import java.util.ArrayList;
import java.util.List;

public enum MATH_FUNCTION {

    PLUS("+"), MINUS("-"), MULTIPLY("*"), DEVIDE("/");

    private final List<String> functionSymbol;

    private MATH_FUNCTION(String... functionSymbol) {
        this.functionSymbol = new ArrayList<String>();
        for (String symbol : functionSymbol) {
            this.functionSymbol.add(symbol);
        }
    }
}
