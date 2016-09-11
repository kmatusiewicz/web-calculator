package com.learning.webcalc.service;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.learning.webcalc.service.util.ExpressionUtil.isOperator;
import static java.lang.Character.isDigit;

@Component
public class ExpressionProcessor
{

    private final NumberFormat numberFormat;

    private final char decimalSeparator;

    public ExpressionProcessor()
    {
        numberFormat = NumberFormat.getInstance();
        DecimalFormatSymbols symbols = ((DecimalFormat)numberFormat).getDecimalFormatSymbols();
        decimalSeparator = symbols.getDecimalSeparator();
    }

    public String clean(String expression)
    {
        return expression
                .replaceAll("\\s", "")
                .replaceAll("\\{", "(")
                .replaceAll("\\}", ")")
                .replaceAll("\\[", "(")
                .replaceAll("\\]", ")");
    }

    public List<Object> tokenize(String expression) throws ParseException
    {
        StringBuilder number = new StringBuilder();
        List<Object> tokens = new ArrayList<>();

        for (char c : expression.toCharArray())
        {
            if (isDigit(c) || isDecimalSeparator(c) || isMinusSignAsPartOfNegativeValue(c, number, tokens))
            {
                number.append(c);
            }
            else if (isOperator(c) || isParenthesis(c))
            {
                if (number.length() > 0)
                {
                    tokens.add(toDouble(number.toString()));
                    number = new StringBuilder();
                }
                tokens.add(Character.toString(c)); // TODO: keep character
            }
            else
            {
                throw new UnsupportedOperationException();
            }
        }
        if (number.length() > 0)
        {
            tokens.add(toDouble(number.toString()));
        }
        return tokens;
    }

    private boolean isDecimalSeparator(char character)
    {
        return character == decimalSeparator;
    }

    private boolean isMinusSignAsPartOfNegativeValue(char character, StringBuilder number, List<Object> tokens)
    {
        return character == '-' && number.length() == 0 && beginningOfValue(tokens);
    }

    private boolean beginningOfValue(List<Object> tokens)
    {
        return tokens.isEmpty() || tokens.get(tokens.size()-1).equals("(");
    }

    private boolean isParenthesis(char character)
    {
        return character == '(' || character == ')';
    }

    private double toDouble(String value) throws ParseException
    {
        return numberFormat.parse(value).doubleValue();
    }

}