package org.example;

import java.util.ArrayList;
import java.util.Stack;

public class AirportFilter {
    private static String ops = "=><&|!";

    private ArrayList<String> postfixFilter = null;

    private static boolean isOperand(char symbol) {
        return symbol == '=' || symbol == '>' || symbol == '<' || symbol == '&' || symbol == '|' || symbol == '(' || symbol == ')';
    }

    private static int priority(char op) {
        switch (op) {
            case '(':
                return -1;
            case '|':
                return 1;
            case '&':
                return 2;
            case '=':
            case '>':
            case '<':
            case '!':
                return 3;
            case ')':
                return 4;
            default:
                return 0;
        }
    }

    private static int readString(StringBuilder sb, String exp, int i) {
        while (++i < exp.length()) {
            char cur = exp.charAt(i);
            if (cur == '\'') {
                break;
            }
            sb.append(cur);
        }
        return i;
    }

    private static ArrayList<String> parse(String filter) {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filter.length(); ++i) {
            char cur = filter.charAt(i);

            if (cur == ' ') {
                continue;
            }
            if (cur == '\'') {
                i = readString(sb, filter, i);
                result.add(sb.toString());
                sb = new StringBuilder();
            }
            else if (isOperand(cur)) {
                if (sb.length() != 0) {
                    result.add(sb.toString());
                    sb = new StringBuilder();
                }
                if (cur == '|') {
                    ++i;
                }
                else if (cur == '<' && filter.charAt(i + 1) == '>') {
                    ++i;
                    cur = '!';
                }
                result.add(String.valueOf(cur));
            }
            else {
                sb.append(cur);
            }
        }
        if (sb.length() != 0)
            result.add(sb.toString());
        return result;
    }

    private static ArrayList<String> toPostfixFilter(ArrayList<String> parsedFilter) {
        ArrayList<String> result = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for (String cur : parsedFilter) {
            if (ops.contains(cur)) {
                if (operators.empty())
                    operators.push(cur);
                else {
                    while (!operators.empty()) {
                        String opTop = operators.pop();
                        if (opTop.equals("(")) {
                            operators.push(opTop);
                            break;
                        } else if (priority(opTop.charAt(0)) < priority(cur.charAt(0))) {
                            operators.push(opTop);
                            break;
                        } else if (priority(opTop.charAt(0)) >= priority(cur.charAt(0)))
                            result.add(opTop);
                    }
                    operators.push(cur);
                }
            }
            else if (cur.equals("(")) {
                operators.push(cur);
            } else if (cur.equals(")")) {
                while (!operators.empty()) {
                    String opTop = operators.pop();
                    if (!opTop.equals("("))
                        result.add(opTop);
                    else
                        break;
                }
            }
            else
                result.add(cur);
        }

        while (!operators.empty()) {
            String opTop = operators.pop();
            result.add(opTop);
        }

        return result;
    }

    private static char valueType(String value) {
        if (value.equalsIgnoreCase("column[1]") || value.equalsIgnoreCase("column[9]")) {
            return 'i'; // Integer
        }
        if (value.equalsIgnoreCase("column[7]") || value.equalsIgnoreCase("column[8]") || value.equalsIgnoreCase("column[10]")) {
            return 'f'; // Float
        }
        if (value.equalsIgnoreCase("column[2]") || value.equalsIgnoreCase("column[3]") || value.equalsIgnoreCase("column[4]") ||
                value.equalsIgnoreCase("column[5]") || value.equalsIgnoreCase("column[6]") || value.equalsIgnoreCase("column[11]") ||
                value.equalsIgnoreCase("column[12]") || value.equalsIgnoreCase("column[13]") || value.equalsIgnoreCase("column[14]")) {
            return 's'; // String
        }
        return 'b'; // Boolean
    }

    private static Integer valueToInteger(Airport airport, String value) {
        if (value.equalsIgnoreCase("column[1]")) {
            return airport.column1;
        }
        if (value.equalsIgnoreCase("column[9]")) {
            return airport.column9;
        }
        return 0;
    }

    private static Float valueToFloat(Airport airport, String value) {
        if (value.equalsIgnoreCase("column[7]")) {
            return airport.column7;
        }
        if (value.equalsIgnoreCase("column[8]")) {
            return airport.column8;
        }
        if (value.equalsIgnoreCase("column[10]")) {
            return airport.column10;
        }
        return 0.0f;
    }

    private static String valueToString(Airport airport, String value) {
        if (value.equalsIgnoreCase("column[2]")) {
            return airport.column2;
        }
        if (value.equalsIgnoreCase("column[3]")){
            return airport.column3;
        }
        if (value.equalsIgnoreCase("column[4]")) {
            return airport.column4;
        }
        if (value.equalsIgnoreCase("column[5]")) {
            return airport.column5;
        }
        if (value.equalsIgnoreCase("column[6]")) {
            return airport.column6;
        }
        if (value.equalsIgnoreCase("column[11]")) {
            return airport.column11;
        }
        if (value.equalsIgnoreCase("column[12]")) {
            return airport.column12;
        }
        if (value.equalsIgnoreCase("column[13]")) {
            return airport.column13;
        }
        if (value.equalsIgnoreCase("column[14]")) {
            return airport.column14;
        }
        return null;
    }

    private static boolean valueToBoolean(String value) {
        return value.equalsIgnoreCase("false") ? false : true;
    }
    private static String calculateEqual(Airport airport, String v2, String v1) {
        boolean res = true;
        switch (valueType(v1)) {
            case 'i':
                res = valueToInteger(airport, v1) == Integer.parseInt(v2);
                break;
            case 'f':
                res = valueToFloat(airport, v1).equals(Float.parseFloat(v2));
                break;
            case 's':
                res = v2.equalsIgnoreCase(valueToString(airport, v1));
                break;
            default:
                res = valueToBoolean(v1) == valueToBoolean(v2);
                break;
        }
        return res ? "TRUE" : "FALSE";
    }
    private static String calculateNotEqual(Airport airport, String v2, String v1) {
        boolean res = true;
        switch (valueType(v1)) {
            case 'i':
                res = valueToInteger(airport, v1) != Integer.parseInt(v2);
                break;
            case 'f':
                res = !valueToFloat(airport, v1).equals(Float.parseFloat(v2));
                break;
            case 's':
                res = !v2.equalsIgnoreCase(valueToString(airport, v1));
                break;
            default:
                res = valueToBoolean(v1) != valueToBoolean(v2);
                break;
        }
        return res ? "TRUE" : "FALSE";
    }
    private static String calculateMore(Airport airport, String v2, String v1) {
        boolean res = true;
        switch (valueType(v1)) {
            case 'i':
                res = valueToInteger(airport, v1) > Integer.parseInt(v2);
                break;
            case 'f':
                res = valueToFloat(airport, v1) > Float.parseFloat(v2);
                break;
            case 's':
                res = v2.compareTo(valueToString(airport, v1)) < 0;
                break;
            default:
                res = true;
                break;
        }
        return res ? "TRUE" : "FALSE";
    }
    private static String calculateLess(Airport airport, String v2, String v1) {
        boolean res = true;
        switch (valueType(v1)) {
            case 'i':
                res = valueToInteger(airport, v1) < Integer.parseInt(v2);
                break;
            case 'f':
                res = valueToFloat(airport, v1) < Float.parseFloat(v2);
                break;
            case 's':
                res = v2.compareTo(valueToString(airport, v1)) > 0;
                break;
            default:
                res = true;
                break;
        }
        return res ? "TRUE" : "FALSE";
    }
    private static String calculateAnd(Airport airport, String v2, String v1) {
        boolean res = true;
        if (valueType(v1) == 'b') {
            res = valueToBoolean(v1) && valueToBoolean(v2);
        }
        return res ? "TRUE" : "FALSE";
    }
    private static String calculateOr(Airport airport, String v2, String v1) {
        boolean res = true;
        if (valueType(v1) == 'b') {
            res = valueToBoolean(v1) || valueToBoolean(v2);
        }
        return res ? "TRUE" : "FALSE";
    }

    public boolean check(Airport airport) {
        if (postfixFilter.size() == 0) {
            return true;
        }
        Stack<String> result = new Stack<>();
        for (String item : postfixFilter) {
            switch (item) {
                case "=":
                    result.push(calculateEqual(airport, result.pop(), result.pop()));
                    break;
                case "!":
                    result.push(calculateNotEqual(airport, result.pop(), result.pop()));
                    break;
                case ">":
                    result.push(calculateMore(airport, result.pop(), result.pop()));
                    break;
                case "<":
                    result.push(calculateLess(airport, result.pop(), result.pop()));
                    break;
                case "&":
                    result.push(calculateAnd(airport, result.pop(), result.pop()));
                    break;
                case "|":
                    result.push(calculateOr(airport, result.pop(), result.pop()));
                    break;
                default:
                    result.push(item);
                    break;
            }
        }
        return valueToBoolean(result.peek());
    }

    AirportFilter(String filter) {
        postfixFilter = toPostfixFilter(parse(filter));
    }
}

