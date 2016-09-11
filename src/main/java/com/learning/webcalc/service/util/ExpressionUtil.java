package com.learning.webcalc.service.util;

import java.util.List;

import static java.util.Arrays.asList;

public class ExpressionUtil
{

    private static final List<String> LEFT_ASSOCIATIVE_OPERATORS = asList("+", "-", "*", "/");

    private static final List<String> RIGHT_ASSOCIATIVE_OPERATORS = asList("^");

    public static boolean isOperator(char character)
    {
        return isOperator(Character.toString(character));
    }

    public static boolean isOperator(Object operator)
    {
        return isLeftAssociativeOperator(operator) || isRightAssociativeOperator(operator);
    }

    public static boolean isLeftAssociativeOperator(Object token)
    {
        return LEFT_ASSOCIATIVE_OPERATORS.contains(token);
    }

    public static boolean isRightAssociativeOperator(Object token)
    {
        return RIGHT_ASSOCIATIVE_OPERATORS.contains(token);
    }

    public static int getImportance(Object operator)
    {
        if (operator.equals("+") || operator.equals("-")) // TODO: use enums for operators?
        {
            return 1;
        }
        if (operator.equals("*") || operator.equals("/"))
        {
            return 2;
        }
        if (operator.equals("^"))
        {
            return 3;
        }
        throw new UnsupportedOperationException();
    }

}