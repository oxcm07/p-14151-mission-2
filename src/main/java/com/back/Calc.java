package com.back;

import java.util.Arrays;

public class Calc {
    public static int run(String exp) {
        exp = SubParenthesis(exp);  //-( ) -> ( ) * -1
        exp = Parenthesis(exp);  //( )제거
        exp = exp.replace(" - ", " + -");   //A - B -> A + -B

        if (exp.contains("(")) {
            String[] expBits = MultiParenthesis(exp, '+');   //( ) + A 분리

            if (expBits != null) {
                return Arrays.stream(expBits)
                        .map(Calc::run)
                        .reduce((a, b) -> a + b)
                        .orElse(0);
            }

            expBits = MultiParenthesis(exp, '*');    //( ) * A 분리

            if (expBits != null) {
                return Arrays.stream(expBits)
                        .map(Calc::run)
                        .reduce((a, b) -> a * b)
                        .orElse(0);
            }
        }

        if (exp.contains(" * ") && exp.contains(" + ")) {
            String[] expBits = exp.split(" \\+ ");

            return Arrays.stream(expBits)
                    .map(Calc::run)
                    .reduce((a, b) -> a + b)
                    .orElse(0);
        }

        if (exp.contains(" * ")) {
            String[] expBits = exp.split(" \\* ");

            return Arrays.stream(expBits)
                    .map(Integer::parseInt)
                    .reduce((a, b) -> a * b)
                    .orElse(0);
        }

        String[] expBits = exp.split(" \\+ ");

        return Arrays.stream(expBits)
                .map(Integer::parseInt)
                .reduce((a, b) -> a + b)
                .orElse(0);
    }

    private static String Parenthesis(String exp) {

        if (exp.startsWith("(") && exp.endsWith(")")) {
            return Parenthesis(exp.substring(1, exp.length() - 1));
        }

        return exp;
    }

    private static String[] MultiParenthesis(String exp, char oper) {
        int count = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
            } else if (count == 0 && c == oper) {
                return new String[]{exp.substring(0, i - 1), exp.substring(i + 2)};
            }

        }
        return null;
    }

    private static String SubParenthesis(String exp) {
        if (exp.startsWith("-(") && exp.endsWith(")")) {
            return exp.substring(1) + " * -1";
        }
        return exp;
    }
}